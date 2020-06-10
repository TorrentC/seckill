package com.imooc.seckill.service;

import com.imooc.seckill.dao.UserDao;
import com.imooc.seckill.domain.User;
import com.imooc.seckill.exception.GlobalException;
import com.imooc.seckill.redis.RedisService;
import com.imooc.seckill.redis.UserKey;
import com.imooc.seckill.result.CodeMsg;
import com.imooc.seckill.result.Result;
import com.imooc.seckill.util.MD5Util;
import com.imooc.seckill.util.UUIDUtil;
import com.imooc.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisService redisService;

    public boolean login(HttpServletResponse response, LoginVo loginVo) {

        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        User user = userDao.findUserById(loginVo.getMobile());
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        String password = MD5Util.inputToForm(loginVo.getPassword());
        if (!password.equals(user.getPassword())) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        String token = UUIDUtil.uuid();
        addCookie(response, token, user);

        return true;
    }

    /**
     * 给客户端添加cookie 向redis中写入user信息
     * @param response
     * @param token 键值
     * @param user redis的value
     */
    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setPath("/");
        cookie.setMaxAge(UserKey.token.expireSeconds());
        response.addCookie(cookie);

    }

    public User getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        User user = redisService.get(UserKey.token, token, User.class);

        if (user != null) {
            addCookie(response, token, user);
        }

        return user;
    }

    @Transactional
    public Result tx() {
        try {
            User user1 = new User();
            user1.setId("6");
            user1.setNickname("mefjsl");
            user1.setPassword("fjls");
            user1.setSalt("fjlsf");

            int save = userDao.save(user1);

            user1.setId("2");
            user1.setNickname("mefjsl");
            user1.setPassword("fjls");
            user1.setSalt("fjlsf");

            userDao.save(user1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Result.success("");

    }
}
