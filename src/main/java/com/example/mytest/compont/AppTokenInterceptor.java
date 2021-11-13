package com.example.mytest.compont;

import com.example.mytest.common.Constants;
import com.example.mytest.common.enums.ErrorCodeEnum;
import com.example.mytest.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/1315:23
 */
@Component
@Slf4j
public class AppTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(Constants.TOKEN);
        log.info("X-APP-TOKEN ：{} ", token);
        if (StringUtils.isBlank(token)) {
            throw BusinessException.valueOf(-1, "请求头token不能为空");
        }
        Long expireTime = stringRedisTemplate.getExpire(Constants.USER_TOKEN_KEY + token, TimeUnit.MINUTES);
        if (expireTime == -2) {
            log.info("token :{} 已经过期 ，返回请重新登录", token);
            throw BusinessException.valueOf(ErrorCodeEnum.LOGIN_TIME_OUT);
        }
        if (expireTime <= 10) {
            stringRedisTemplate.expire(Constants.USER_TOKEN_KEY+token,2,TimeUnit.DAYS);
        }
        return true;
    }
}
