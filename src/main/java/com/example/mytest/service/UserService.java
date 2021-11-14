package com.example.mytest.service;

import com.example.mytest.model.domain.User;
import com.example.mytest.request.*;
import com.example.mytest.response.Response;
import com.example.mytest.response.UserAccountTransResponse;
import com.example.mytest.response.UserCheckPayPasswordResponse;
import com.example.mytest.response.UserLoginResponse;

import java.util.List;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/923:03
 */
public interface UserService {
    /**
     * 用户注册
     *
     * @param userRegRequest
     * @return
     */
    User addUser(UserRegRequest userRegRequest);

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @return
     */
    Response<UserLoginResponse> userLogin(UserLoginRequest userLoginRequest);

    /**
     * 用户设置支付密码
     *
     * @param userSetPayPassRequest
     * @param user
     */
    void setPayPassword(UserSetPayPassRequest userSetPayPassRequest, User user);

    /**
     * 校验用户支付密码
     * @param userCheckPayPasswordRequest
     * @param user
     */
    Response<UserCheckPayPasswordResponse> checkPayPassword(UserCheckPayPasswordRequest userCheckPayPasswordRequest, User user);

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    User getUserByToken(String token);

    /**
     * 用户信息列表
     * @return
     * @param userListRequest
     */
    Response<List<User>> userList(UserListRequest userListRequest);

    /**
     * 转账
     * @param userAccountTransRequest
     * @param user
     * @return
     */
    Response<UserAccountTransResponse> userAccountTrans(UserAccountTransRequest userAccountTransRequest, User user);

}
