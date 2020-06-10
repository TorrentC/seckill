package com.imooc.seckill.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.imooc.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {

        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String s = jedis.get(realKey);
            T t = stringToBean(s, clazz);

            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            if (key == null || key.length() <= 0) {
                return false;
            }

            String realKey = prefix.getPrefix() + key;
            int i = prefix.expireSeconds();

            if (i <= 0) {
                jedis.set(realKey, beanToString(value));
            } else {
                jedis.setex(realKey , i, beanToString(value));
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    public boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            return jedis.exists(prefix.getPrefix() + key);
        } finally {
            returnToPool(jedis);
        }
    }

    //增加值
    public Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            return jedis.incr(prefix.getPrefix() + key);
        } finally {
            returnToPool(jedis);
        }
    }

    //减少值
    public Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            return jedis.decr(prefix.getPrefix() + key);
        } finally {
            returnToPool(jedis);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    private <T> T stringToBean(String s, Class<T> clazz) {

        if (s == null || s.length() <= 0 || clazz == null) {
            return null;
        }

        if (int.class == clazz || Integer.class == clazz) {
            return (T) Integer.valueOf(s);
        }

        if (Long.class == clazz || long.class == clazz) {
            return (T) Long.valueOf(s);
        }

        if (String.class == clazz) {
            return (T) s;
        }

        return JSON.toJavaObject(JSON.parseObject(s), clazz);
    }

    public <T> String beanToString(T value) {

        if (value == null) {
            return null;
        }

        Class<?> clazz = value.getClass();

        if (int.class == clazz || Integer.class == clazz || Long.class == clazz || long.class == clazz || String.class == clazz) {
            String.valueOf(value);
        }

        return JSON.toJSONString(value);
    }

    public static void main(String[] args) {

        String t = "[{\"endDate\":1598287158000,\"goodsImg\":\"/img/meta10.png\",\"goodsName\":\"HuaWei mate20\",\"goodsPrice\":5000.0,\"goodsStock\":-1,\"id\":1,\"miaoshaPrice\":0.5,\"startDate\":1564023615000,\"stockCount\":-21},{\"endDate\":1598287180000,\"goodsImg\":\"/img/iphonex.png\",\"goodsName\":\"IPhone10\",\"goodsPrice\":8000.0,\"goodsStock\":-1,\"id\":2,\"miaoshaPrice\":1.0,\"startDate\":1595608777000,\"stockCount\":100}]";
        ArrayList<GoodsVo> goodsVos = new ArrayList<GoodsVo>();
        GoodsVo goodsVo = new GoodsVo();
        goodsVo.setId(1L);
        goodsVo.setEndDate(new Date());
        goodsVo.setMiaoshaPrice(1d);

        goodsVos.add(goodsVo);
        String s = JSON.toJSONString(goodsVos);
        System.out.println(s);

        List<GoodsVo> goodsVos1 = JSONArray.parseArray(t, GoodsVo.class);
        System.out.println(goodsVos1);
    }
}
