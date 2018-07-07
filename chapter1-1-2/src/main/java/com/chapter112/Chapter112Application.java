package com.chapter112;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.chapter112")
public class Chapter112Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter112Application.class, args);
    }
}
