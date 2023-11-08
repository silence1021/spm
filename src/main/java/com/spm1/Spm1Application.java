package com.spm1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.spm1.mapper")
@EnableTransactionManagement
public class Spm1Application {

    public static void main(String[] args) {
        SpringApplication.run(Spm1Application.class, args);
    }

}
