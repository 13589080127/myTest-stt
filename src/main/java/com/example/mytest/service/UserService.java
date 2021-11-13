package com.example.mytest.service;

import com.example.mytest.model.domain.User;
import com.example.mytest.request.UserLoginRequest;
import com.example.mytest.request.UserRegRequest;
import com.example.mytest.response.Response;
import com.example.mytest.response.UserLoginResponse;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/923:03
 */
public interface UserService {
    /**
     * 用户注册
     * @param userRegRequest
     * @return
     */
    User addUser(UserRegRequest userRegRequest);

    /**
     * 用户登录
     * @param userLoginRequest
     * @return
     */
    Response<UserLoginResponse> userLogin(UserLoginRequest userLoginRequest);

}
