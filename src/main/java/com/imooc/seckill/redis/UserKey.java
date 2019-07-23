package com.imooc.seckill.redis;

public class UserKey extends BasePrefix {

    private UserKey(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    private static UserKey getByName = new UserKey("name");

    public static void main(String[] args) {
        String prefix = getById.getPrefix();
        System.out.println(getById.expireSeconds());
    }
}
