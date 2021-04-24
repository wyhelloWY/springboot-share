package com.share.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.share.springboot.mapper")
public class SpringbootShareApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootShareApplication.class, args);
    }

}
