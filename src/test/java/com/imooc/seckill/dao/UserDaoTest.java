package com.imooc.seckill.dao;

import com.imooc.seckill.BaseTest;
import com.imooc.seckill.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDaoTest extends BaseTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testFindAll() {
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void testFindById() {
        User user = userDao.findUserById("15395201029");
        System.out.println(user);
    }
}
