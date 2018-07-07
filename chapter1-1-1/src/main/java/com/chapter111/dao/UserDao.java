package com.chapter111.dao;

import com.chapter111.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    @Insert("INSERT INTO tb_user(username,password) VALUES(#{username},#{password})")
    int addUser(@Param("username") String username, @Param("password") String password);

    @Select("SELECT * FROM tb_user WHERE username = #{username}")
    User findUserByName(@Param("username") String username);

    @Update("UPDATE tb_user SET username = #{username},password = #{password} WHERE id = #{id}")
    int updateUser(@Param("username") String username, @Param("password") String password, @Param("id") int id);

    @Delete("DELETE FROM tb_user WHERE username = #{username}")
    int deleteUser(@Param("username") String username);
}
