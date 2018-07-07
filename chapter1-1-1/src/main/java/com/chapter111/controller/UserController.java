package com.chapter111.controller;

import com.chapter111.entity.User;
import com.chapter111.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {
/*    @Autowired
    private UserService userService;
    @RequestMapping("/insert")
    public User testInsert(){
        userService.insertService();
        return userService.selectService("test01");
    }
    @RequestMapping("/select")
    public User testSelect(){
        return userService.selectService("test01");
    }*/
    private Log log = LogFactory.getLog(UserController.class);
    @Autowired
    private UserService userService;

    @RequestMapping("/insert")
    public void insetUser(@RequestBody User user){
        userService.insertService(user.getUsername(),user.getPassword());
    }
    @RequestMapping("/select")
    public User selectUser(String username){
        User user = userService.selectService(username);
        log.info(user);
        return user;
    }
    @RequestMapping("/update")
    public  void updateUser(@RequestBody User user){
        userService.updateService(user.getUsername(),user.getPassword(),user.getId().intValue());
    }
    @RequestMapping("/delete")
    public void deleteUser(String username){
        userService.deleteService(username);
    }

}
