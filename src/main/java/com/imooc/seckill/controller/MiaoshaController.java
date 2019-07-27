package com.imooc.seckill.controller;

import com.imooc.seckill.domain.OrderInfo;
import com.imooc.seckill.domain.User;
import com.imooc.seckill.exception.GlobalException;
import com.imooc.seckill.result.CodeMsg;
import com.imooc.seckill.service.GoodsService;
import com.imooc.seckill.service.MiaoshaService;
import com.imooc.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String goodsList(Model model, User user, @RequestParam("goodsId") Long goodsId) {
        model.addAttribute("user", user);

        if (user == null) {
            return "login";
        }

        GoodsVo goodsVo = goodsService.getCoodesVoById(goodsId);
        //判断库存是否大于零
        if (goodsVo.getStockCount() <= 0) {
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
        }

        //判断是否重复秒杀


        //减库存 下订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goodsVo);
        return "order_detail";
    }

}
