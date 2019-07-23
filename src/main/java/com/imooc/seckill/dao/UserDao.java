package com.imooc.seckill.dao;

import com.imooc.seckill.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("select * from user")
    List<User> findAll();

    @Select("select * from user where id = #{id}")
    User findUserById(@Param("id")int id);

    @Insert("insert into user(id, username) values(#{id}, #{username})")
    int addUser(User user);
}
