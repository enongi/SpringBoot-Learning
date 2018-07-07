package com.chapter112.dao;

import com.chapter112.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;

@Mapper
public interface UserDao {
    int insertUser(HashMap<String, Object> map);

    User selectUserByName(String username);

    int updateUser(HashMap<String, Object> map);

    int deleteUser(String username);
}
