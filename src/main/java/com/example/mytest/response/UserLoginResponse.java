package com.example.mytest.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/100:12
 */
@Data
public class UserLoginResponse implements Serializable {

    private String token;
}
