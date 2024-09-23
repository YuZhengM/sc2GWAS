package com.spring.boot.config;

import com.spring.boot.Sc2GWASApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Yu Zhengmin
 */
public class WebServletInitializerConfig extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // Demo1Application 这个类是 SpringApplication.run 启动类
        return application.sources(Sc2GWASApplication.class);
    }

}

