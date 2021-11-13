package com.example.mytest.common;

/**
 * @author Fanjiyu
 * @Title: 常量
 * @Description:
 * @date 2021/11/923:02
 */
public interface Constants {

    String APP_AUTH="X-APP-AUTH";

    String TOKEN = "X-APP-TOKEN";


    /**
     * 手机号码的正则表达式
     */
    String MOBILE_REG = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$";
    /**
     * 姓名的正则表达式
     */
    String NAME_REG = "^[\\u4e00-\\u9fa5]{2,6}$";


    String USER_TOKEN_KEY="user:token:";

    String PAY_PASS_WORD_TOKEN_KEY = "pay:password:token:";
    /**
     * 自增长主键
     */
    String ORDER_ID_KEY="orderId";
}
