package com.spring.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Yu Zhengmin
 */
@MapperScan("com.spring.boot.mapper")
@EnableCaching
@SpringBootApplication
public class Sc2GWASApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sc2GWASApplication.class, args);
    }

}
