package com.imooc.seckill.service;

import com.imooc.seckill.dao.GoodsDao;
import com.imooc.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getCoodesVoById(Long goodsId) {
        return goodsDao.listGoodsVoById(goodsId);
    }

    public void reduceStock(GoodsVo goodsVo) {
        goodsDao.reduceStock(goodsVo);
    }
}
