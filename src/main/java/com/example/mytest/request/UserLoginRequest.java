package com.example.mytest.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/100:11
 */
@Data
public class UserLoginRequest  implements Serializable {

    private String mobile;

    private String password;
}
