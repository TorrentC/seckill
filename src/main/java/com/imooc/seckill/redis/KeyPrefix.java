package com.imooc.seckill.redis;

public interface KeyPrefix {
    int expireSeconds();

    String getPrefix();
}
