package com.imooc.seckill.dao;

import com.imooc.seckill.BaseTest;
import com.imooc.seckill.vo.GoodsVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GoodsDaoTest extends BaseTest {

    @Autowired
    private GoodsDao goodsDao;

    @Test
    public void testListGoodsVo() {
        List<GoodsVo> goodsVoList = goodsDao.listGoodsVo();
        for (GoodsVo goodsVo : goodsVoList) {
            System.out.println(goodsVo);
        }

        GoodsVo goodsVo = goodsDao.listGoodsVoById(1l);
        System.out.println(goodsVo);
    }

    @Test
    public void testReduceStock() {
        GoodsVo good = new GoodsVo();
        good.setId(1l);
        goodsDao.reduceStock(good);
    }
}
