package com.chapter111;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.chapter111")
public class Chapter111Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter111Application.class, args);
    }
}
