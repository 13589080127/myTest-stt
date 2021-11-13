package com.example.mytest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.example.mytest.mapper")
public class MyTestSttApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyTestSttApplication.class, args);
    }


}
