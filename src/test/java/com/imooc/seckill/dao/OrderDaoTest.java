package com.imooc.seckill.dao;

import com.imooc.seckill.BaseTest;
import com.imooc.seckill.domain.MiaoshaOrder;
import com.imooc.seckill.domain.OrderInfo;
import org.junit.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderDaoTest extends BaseTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void testInsert() {
        OrderInfo orderInfo = new OrderInfo();
        long insert = orderDao.insert(orderInfo);
        System.out.println(orderInfo.getId());

        MiaoshaOrder o = new MiaoshaOrder();
        o.setUserId("15395201029");
        o.setGoodsId(1l);
        o.setOrderId(1l);
        orderDao.insertMiaoshaOrder(o);
    }
}
