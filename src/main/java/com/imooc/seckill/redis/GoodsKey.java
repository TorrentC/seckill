package com.imooc.seckill.redis;

public class GoodsKey extends BasePrefix {

    private GoodsKey(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    private GoodsKey(String prefix) {
        super(prefix);
    }

    //设置cookie和redis中字段token的过期时间
    public static final int GOODS_EXPIRE = 3600*24;
    public static GoodsKey GOODSLIST = new GoodsKey(GOODS_EXPIRE, "goodsList");


}
