package com.imooc.seckill.controller;

import com.imooc.seckill.domain.User;
import com.imooc.seckill.result.CodeMsg;
import com.imooc.seckill.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class HelloController {


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

    @RequestMapping("/user")
    @ResponseBody
    public Result user(User user) {
        return Result.success(user);
    }
}
