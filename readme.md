## 秒杀商城实战

```
create table user
(
	id bigint not null,
	nickname varchar(255) not null,
	password varchar(32) not null,
	salt varchar(10) not null,
	head varchar(128) null,
	register_date datetime null,
	last_login_date datetime null,
	login_count int null,
	constraint user_pk
		primary key (id)
);


```

1. 登录功能实现

对用户密码进行两次md5加密

前端加密一次 后端随机加盐再加密一次
```
    String password = MD5Util.formToDB(loginVo.getPassword(), user.getSalt());
        if (!password.equals(user.getPassword())) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
```

2 使用相关库对提交数据进行校验

```
@Valid LoginVo loginVo

@NotNull
    private String mobile;

    @NotNull
    private String password;
```

3 处理全局异常

本服务器所有异常交由该类处理
```
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {

        if (e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }

        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;
            CodeMsg codeMsg = ex.getCodeMsg();
            return Result.error(codeMsg);
        }



        return Result.error(CodeMsg.SERVER_ERROR);
    }

}

```

4 实现分布式session

cookie token:uuid

redis uuid:user
```
private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setPath("/");
        cookie.setMaxAge(UserKey.token.expireSeconds());
        response.addCookie(cookie);

    }
```

4 对User参数的封装(config包)

```
com.imooc.seckill.config.UserArgumentResolver
```