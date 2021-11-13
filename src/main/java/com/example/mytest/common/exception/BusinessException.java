package com.example.mytest.common.exception;

import lombok.Data;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/100:01
 */
@Data
public class BusinessException extends RuntimeException {

    private int errorCode;

    private String errorMsg;

    public BusinessException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public static BusinessException valueOf(int code, String msg) {
        return new BusinessException(code, msg);
    }
}
