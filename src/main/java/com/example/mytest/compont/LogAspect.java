package com.example.mytest.compont;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/1319:07
 */
@Aspect
@Component
@Slf4j
public class LogAspect {


    @Around(value = "execution(public * com.example.mytest.controller.*.*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        log.info("返回：{}", JSON.toJSONString(result));
        return result;
    }
}
