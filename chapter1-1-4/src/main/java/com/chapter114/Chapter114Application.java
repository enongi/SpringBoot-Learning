package com.chapter114;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableWebSecurity //启用web安全
public class Chapter114Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter114Application.class, args);
    }

    @RequestMapping("/security")
    public String security(){
        return "Hello Security !!!";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "不验证哦";
    }
}
