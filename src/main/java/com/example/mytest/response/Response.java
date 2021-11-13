package com.example.mytest.response;

import java.io.Serializable;

/**
 * @author Kangxu
 * @since 2020/7/15
 */
public class Response<T> implements Serializable {

    private static final String DEFAULT_SUCCESS_MESSAGE = "Success";

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 状态码
     */
    private int code = 200;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回实体
     */
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    private Response<T> setSuccess(boolean success) {
        this.success = success;
        if (this.success) {
            this.code = 200;
        }
        return this;
    }

    private Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    private Response<T> setCode(int code) {
        this.code = code;
        return this;
    }

    private Response<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public static <T> Response<T> success() {
        return new Response<T>().setSuccess(true).setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Response<T> success(T data) {
        return new Response<T>().setSuccess(true).setData(data).setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Response<T> success(T data, String message) {
        return new Response<T>().setSuccess(true).setData(data).setMessage(message);
    }

//    public static <T> Response<T> fail(UserErrorCode errorCode) {
//        return new Response<T>().setSuccess(false).setCode(errorCode.getCode()).setMessage(errorCode.getMsg());
//    }
//
//    public static <T> Response<T> fail(UserErrorCode errorCode, String message) {
//        return new Response<T>().setSuccess(false).setCode(errorCode.getCode()).setMessage(message);
//    }

    public static <T> Response<T> fail(int errorCode, String message) {
        return new Response<T>().setSuccess(false).setCode(errorCode).setMessage(message);
    }

    public static <T> Response<T> fail(int errorCode, String message, T data) {
        return new Response<T>().setSuccess(false).setCode(errorCode).setMessage(message).setData(data);
    }

//    public static <T> Response<T> fail(Response response) {
//        return fail(response, UserErrorCode.SystemError);
//    }
//
//    public static <T> Response<T> fail(Response response, UserErrorCode defaultErrorCode) {
//        Response<T> rsp = new Response<T>().setSuccess(false).setCode(defaultErrorCode.getCode()).setMessage(defaultErrorCode.getMsg());
//        if (Objects.nonNull(response)) {
//            rsp.setCode(response.getCode()).setMessage(response.getMessage());
//        }
//        return rsp;
//    }
}
