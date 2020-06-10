package com.imooc.seckill.dao;

import com.imooc.seckill.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("select * from user")
    List<User> findAll();

    @Select("select * from user where id = #{id}")
    User findUserById(@Param("id")String id);

    @Insert("insert into user(id, nickname, password, salt) values(#{id}, #{nickname}, #{password}, #{salt})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")//加入该注解可以保持对象后，查看对象插入id
    int save(User user);

}
