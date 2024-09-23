package com.spring.boot.service.impl;

import com.spring.boot.config.bean.Path;
import com.spring.boot.service.FileService;
import com.spring.boot.util.factory.LogFactory;
import com.spring.boot.util.factory.log.Log;
import com.spring.boot.util.util.FileUtil;
import com.spring.boot.util.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    private static final Log log = LogFactory.getLog(FileServiceImpl.class);

    private Path path;

    public FileServiceImpl() {
    }

    @Autowired
    public FileServiceImpl(Path path) {
        this.path = path;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String workPath = path.getWorkPath();
        String userPath = workPath + "/user/";

        String filename = StringUtil.getUniqueId() + "_" + file.getOriginalFilename();

        log.info("User upload file path: {}, filename: {}", userPath, filename);
        // 形成文件
        FileUtil.formation(userPath + filename, file.getInputStream());

        return filename;
    }
}
