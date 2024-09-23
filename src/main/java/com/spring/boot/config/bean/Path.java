package com.spring.boot.config.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YKenan
 */
@Component
@ConfigurationProperties(prefix = "com.path")
@ToString
@Data
public class Path {

    /**
     * 项目的工作路径
     */
    private String workPath;

    /**
     * 项目 bedtools 的命令路径
     */
    private String bedtools;

    /**
     * 前端的工作路径
     */
    private String pagePath;

}
