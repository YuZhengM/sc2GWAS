package com.spring.boot.config;

import com.spring.boot.config.bean.ExecLinux;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YKenan
 */
@Configuration
@EnableConfigurationProperties(ExecLinux.class)
public class CoreProperties {

    @Bean
    public ExecLinux execLinux() {
        return new ExecLinux();
    }

}