package com.example.mytest.common.enums;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/1315:27
 */
public enum ErrorCodeEnum {
    LOGIN_TIME_OUT(405,"登录超时"),
    PAY_PASS_ERROR(500,"支付密码错误"),
    PAY_PASS_TOKEN_ERROR(501,"支付密码过期或错误"),
    INSUFFICIENT_BALANCE(502,"余额不足"),
    IN_ACCOUNT_ERROR(503,"收款方信息错误"),
    ;
    int code;
    String message;
    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
