package com.imooc.seckill.controller;

import com.imooc.seckill.dao.UserDao;
import com.imooc.seckill.domain.User;
import com.imooc.seckill.redis.RedisService;
import com.imooc.seckill.redis.UserKey;
import com.imooc.seckill.result.CodeMsg;
import com.imooc.seckill.result.Result;
import com.imooc.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class HelloController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;


    @RequestMapping("/")
    public String hello(Model model) {

        model.addAttribute("name", "merry");

        Result<List<Integer>> success = Result.success(Arrays.asList(1, 2, 3, 4));

        return "hello";
    }

    @RequestMapping("/err")
    @ResponseBody
    public Result error() {
        Result<Object> error = Result.error(CodeMsg.SERVER_ERROR);
        return error;
    }

    @RequestMapping("/test")
    @ResponseBody
    public List<Integer> test() {
        return Arrays.asList(1, 2, 3, 4);
    }

    @RequestMapping("/get/{key}")
    @ResponseBody
    public Result user(@PathVariable("key") String key) {
        Long abc = redisService.get(UserKey.getById, key, Long.class);
        System.out.println(abc);
        return Result.success(abc);
    }

    @RequestMapping("/set/{key}")
    @ResponseBody
    public Result set(@PathVariable("key") String key, @RequestParam("value") Long value) {
        boolean set = redisService.set(UserKey.getById, key, value);
        System.out.println(set);
        return Result.success(set);
    }
}
