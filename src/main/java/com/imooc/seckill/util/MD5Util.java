package com.imooc.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    private static final String salt = "1a2b3c4d";

    public static String md5(String s) {
        return DigestUtils.md5Hex(s);
    }

    public static String inputToForm(String password) {
        return md5(password);
    }

    public static String formToDB(String md5Pass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + md5Pass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputToDB(String password, String salt) {
        return formToDB(inputToForm(password), salt);
    }

    public static void main(String[] args) {
        String admin = inputToForm("admin");
        System.out.println(admin);
    }
}
