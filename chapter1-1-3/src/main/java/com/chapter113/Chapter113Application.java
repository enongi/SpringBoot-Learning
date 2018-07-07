package com.chapter113;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.chapter113")
public class Chapter113Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter113Application.class, args);
    }
}
