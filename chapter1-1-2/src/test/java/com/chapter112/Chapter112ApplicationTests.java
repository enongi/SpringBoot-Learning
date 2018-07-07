package com.chapter112;

import com.chapter112.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Chapter112ApplicationTests {
    private Log log = LogFactory.getLog(Chapter112ApplicationTests.class);
    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
    }
    @Test
    public void testUser(){
        userService.insertService("test04","123456");
        log.info(userService.selectService("test04"));
        userService.updateService("test004","123456",5);
        log.info(userService.selectService("test004"));
        userService.deleteService("test004");
        log.info(userService.selectService("test01"));
    }

}
