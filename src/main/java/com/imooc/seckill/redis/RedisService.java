package com.imooc.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
                jedis.setex(key , i, beanToString(value));
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
}
