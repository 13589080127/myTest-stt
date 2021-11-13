package com.example.mytest.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/923:07
 */
@Data
public class UserRegResponse implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;
}
