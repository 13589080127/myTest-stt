package com.example.mytest.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/1415:43
 */
@Data
public class UserListRequest implements Serializable {

    //用户id查询
    private Integer userId;
    //手机号码查询
    private String mobile;
    //名称查询
    private String name;
}
