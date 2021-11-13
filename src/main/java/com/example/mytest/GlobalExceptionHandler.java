package com.example.mytest;

import com.example.mytest.common.exception.BusinessException;
import com.example.mytest.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    //region Exception

    /**
     * 其他异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Response exceptionHandler(Exception e) {
        log.error("系统发生异常", e);
        return Response.fail(-1, e.getMessage());
    }
    //endregion


    //region Exception

    /**
     * 其他异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Response businessExceptionHandler(BusinessException e) {
        log.error("错误：{}", e.getErrorMsg());
        return Response.fail(e.getErrorCode(), e.getErrorMsg());
    }
    //endregion
}
