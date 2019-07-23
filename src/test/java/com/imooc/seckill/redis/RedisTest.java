package com.imooc.seckill.redis;

import com.imooc.seckill.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTest extends BaseTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void testConnection() {


        boolean a = redisService.set(UserKey.getById,"123", 123);
        System.out.println(redisService.decr(UserKey.getById, "123"));

    }

    private static class User {
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
