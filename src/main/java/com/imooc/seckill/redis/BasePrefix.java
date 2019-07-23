package com.imooc.seckill.redis;

public abstract class BasePrefix implements KeyPrefix{
    private Integer expireSeconds;
    private String prefix;

    public BasePrefix(Integer expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {

        //表示永不过期
        this(0, prefix);
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        return getClass().getSimpleName() + ":" + prefix + ":";
    }
}
