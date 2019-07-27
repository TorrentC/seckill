package com.imooc.seckill.service;

import com.imooc.seckill.domain.OrderInfo;
import com.imooc.seckill.domain.User;
import com.imooc.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Transactional
    public OrderInfo miaosha(User user, GoodsVo goodsVo) {
        goodsService.reduceStock(goodsVo);
        return orderService.createOrder(user, goodsVo);
    }
}
