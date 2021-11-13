package com.example.mytest.compont;

import com.alibaba.fastjson.JSON;
import com.example.mytest.common.Constants;
import com.example.mytest.common.exception.BusinessException;
import com.example.mytest.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        return true;
    }

    private void write(HttpServletResponse response, Response<Void> returnRes) {
        try (PrintWriter writer = response.getWriter()) {
            writer.print(JSON.toJSONString(returnRes));
        } catch (IOException e) {
            log.error("写数据错误：", e);
        }
    }
}
