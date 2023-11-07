package com.spm1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.spm1.mapper")
public class Spm1Application {

    public static void main(String[] args) {
        SpringApplication.run(Spm1Application.class, args);
    }

}
