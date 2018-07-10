package com.chapter113.dao;

import com.chapter113.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Mapper
@Repository
public interface UserDao {
    int insert(HashMap<String, Object> map);
    User select(HashMap<String, Object> map);
    User findName(HashMap<String, Object> map);
}
