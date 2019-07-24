package com.imooc.seckill.controller;

import com.imooc.seckill.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @RequestMapping("/to_list")
    public String goodsList(Model model, User user) {
        model.addAttribute("user", user);
        return "goods_list";
    }
}
