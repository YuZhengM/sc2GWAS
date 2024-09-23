package com.spring.boot.config.bean;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.spring.boot.Sc2GWASApplication;
import com.spring.boot.util.factory.LogFactory;
import com.spring.boot.util.factory.log.Log;
import com.spring.boot.util.util.NumberUtil;
import com.spring.boot.util.util.StringUtil;
import com.spring.boot.util.util.config.ReadConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 执行 Linux 命令并返回结果 (可以远程操作)
 *
 * @author YKenan
 */
@Data
@ConfigurationProperties(prefix = "com.linux")
public class ExecLinux {

    private static final Log log = LogFactory.getLog(ExecLinux.class);

    /**
     * IP 地址
     */
    private String host;
    /**
     * 端口号
     */
    private int port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 初始化
     */
    public ExecLinux() {
        ReadConfig readConfig = new ReadConfig(Sc2GWASApplication.class, "application.properties");
        log.debug("init linux config...");
        if (StringUtil.isEmpty(host)) {
            host = readConfig.getConfiguration("com.linux.ip");
        }
        if (NumberUtil.isEqualZero(port)) {
            port = Integer.parseInt(readConfig.getConfiguration("com.linux.port"));
        }
        if (StringUtil.isEmpty(username)) {
            username = readConfig.getConfiguration("com.linux.username");
        }
        if (StringUtil.isEmpty(password)) {
            password = readConfig.getConfiguration("com.linux.password");
        }
    }

    /**
     * 执行命令
     *
     * @param string 执行的命令
     * @return List<String>
     * @throws IOException IOException
     */
    public List<String> execCommand(String string) throws IOException {
        // 创建连接
        Connection connection = new Connection(host, port);
        class Authenticate {
            private Boolean login() {
                boolean flg = false;
                try {
                    Connection connection = new Connection(host);
                    // 连接
                    connection.connect();
                    // 判断身份是否已经认证
                    if (!connection.isAuthenticationComplete()) {
                        // 加锁，防止多线程调用时线程间判断不一致，导致出现重复认证
                        synchronized (this) {
                            // 进行身份认证
                            if (!connection.isAuthenticationComplete()) {
                                flg = connection.authenticateWithPassword(username, password);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return flg;
            }
        }

        // 创建返回的容器
        List<String> result = new ArrayList<>();
        // 连接
        connection.connect();
        // 认证
        boolean authenticateWithPassword = connection.authenticateWithPassword(username, password);
        Authenticate authenticate = new Authenticate();
        if (!authenticateWithPassword || !authenticate.login()) {
            throw new IOException("Authentication failed.");
        } else {
            Session session = connection.openSession();
            session.execCommand(string, "utf-8");
            StreamGobbler stdout = new StreamGobbler(session.getStdout());
            StreamGobbler stderr = new StreamGobbler(session.getStderr());
            BufferedReader stdoutBuffer = new BufferedReader(new InputStreamReader(stdout));
            BufferedReader solderersBuffers = new BufferedReader(new InputStreamReader(stderr));
            log.debug("exec Linux >>>> {}", string);
            for (String line = stdoutBuffer.readLine(); line != null; line = stdoutBuffer.readLine()) {
                result.add(line + "\n");
            }
            for (String line = solderersBuffers.readLine(); line != null; line = solderersBuffers.readLine()) {
                log.error("line Linux >>>> {}", line);
                result.add(line);
            }
            stdoutBuffer.close();
            solderersBuffers.close();
            session.close();
        }
        connection.close();
        return result;
    }

}

