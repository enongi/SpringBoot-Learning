package com.chapter112.service;

import com.chapter112.dao.UserDao;
import com.chapter112.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

/*    public void insertService(){
        userDao.addUser("test01","123456");
    }
    public User selectService(String username){
        return userDao.findUserByName(username);
    }*/

    public void insertService(String username, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        userDao.insertUser(map);
    }

    public User selectService(String username) {
        return userDao.selectUserByName(username);
    }

    public void updateService(String username, String password, int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("id", id);
        userDao.updateUser(map);
    }

    public void deleteService(String username) {
        userDao.deleteUser(username);
    }
}
