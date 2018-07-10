package com.chapter113.service;

import com.chapter113.dao.UserDao;
import com.chapter113.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public void insertService(String username,String password){
        HashMap<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        userDao.insert(map);
    }
    public User selectService(String username,String password){
        HashMap<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        return userDao.select(map);
    }
    public User findNameService(String username){
        HashMap<String,Object> map = new HashMap<>();
        map.put("username",username);
        return userDao.findName(map);
    }
}
