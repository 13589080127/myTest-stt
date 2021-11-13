package com.example.mytest.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/923:07
 */
@Data
public class UserRegRequest implements Serializable {

    private String mobile;

    private String password;

    private String name;
}
