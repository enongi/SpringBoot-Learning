package com.chapter113;

import com.chapter113.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Chapter113ApplicationTests {
    private Log log = LogFactory.getLog(Chapter113ApplicationTests.class);
    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
    }
    @Test
    public void testUser(){
        userService.insertService("test07","123456");
        //log.info(userService.selectService("test04","123456"));
    }
}
