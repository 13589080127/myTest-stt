package com.example.mytest.compont;

import com.alibaba.fastjson.JSON;
import com.example.mytest.common.Constants;
import com.example.mytest.common.exception.BusinessException;
import com.example.mytest.model.bo.AppAuthBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/1122:06
 */
@Component
@Slf4j
public class AppInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String appAuth = request.getHeader(Constants.APP_AUTH);
        if (StringUtils.isBlank(appAuth)) {
            throw new BusinessException(-1, String.format("请求头: %s不能为空", Constants.APP_AUTH));
        }
        AppAuthBO appAuthBO = JSON.parseObject(appAuth, AppAuthBO.class);
        if (!appAuthBO.getAppId().equals("10086")) {
            log.info("请求应用标识错误：{}", appAuthBO.getAppId());
            throw BusinessException.valueOf(-1, "请求应用标识错误");
        }
        if ((Math.abs(appAuthBO.getTimestamp() - (System.currentTimeMillis()/1000)) / 60 )> 20) {
            log.info("请求服务时间错误:{}",appAuthBO.getTimestamp());
            throw BusinessException.valueOf(-1, "请求服务时间错误");
        }
        return true;
    }
}
