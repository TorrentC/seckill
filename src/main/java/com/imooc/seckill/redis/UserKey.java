package com.imooc.seckill.redis;

public class UserKey extends BasePrefix {

    private UserKey(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    private UserKey(String prefix) {
        super(prefix);
    }

    //设置cookie和redis中字段token的过期时间
    public static final int TOKEN_EXPIRE = 3600*24 * 2;
    public static UserKey token = new UserKey(TOKEN_EXPIRE, "token");

    public static UserKey getById = new UserKey("id");
    private static UserKey getByName = new UserKey("name");

    public static void main(String[] args) {
        String prefix = getById.getPrefix();
        System.out.println(prefix);
    }
}
