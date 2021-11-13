package com.example.mytest.controller;

import com.alibaba.fastjson.JSON;
import com.example.mytest.common.Constants;
import com.example.mytest.model.domain.User;
import com.example.mytest.request.*;
import com.example.mytest.response.*;
import com.example.mytest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/923:03
 */
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("user/reg")
    public Response<UserRegResponse> userReg(@Valid @RequestBody UserRegRequest userRegRequest) {
        log.info("用户注册入参 ：{}", JSON.toJSONString(userRegRequest));
        User users = userService.addUser(userRegRequest);
        UserRegResponse userRegResponse = new UserRegResponse();
        userRegResponse.setUserId(users.getId());
        log.info("用户注册出参：{}", JSON.toJSONString(userRegResponse));
        return Response.success(userRegResponse);
    }

    @PostMapping("user/login")
    public Response<UserLoginResponse> userLogin(@RequestBody UserLoginRequest userRegRequest) {
        log.info("用户登录入参 ：{}", JSON.toJSONString(userRegRequest));
        return userService.userLogin(userRegRequest);
    }

    @PostMapping("user/paypassword/set")
    public Response<Void> setPayPassword(HttpServletRequest request, @Valid @RequestBody UserSetPayPassRequest userSetPayPassRequest) {
        User user = userService.getUserByToken(request.getHeader(Constants.TOKEN));
        userService.setPayPassword(userSetPayPassRequest, user);
        return Response.success();
    }


    @PostMapping("user/paypassword/check")
    public Response<UserCheckPayPasswordResponse> checkPayPassword(HttpServletRequest request, @Valid @RequestBody UserCheckPayPasswordRequest userSetPayPassRequest) {
        User user = userService.getUserByToken(request.getHeader(Constants.TOKEN));
        return userService.checkPayPassword(userSetPayPassRequest, user);
    }

    @PostMapping("user/list")
    public Response<?> userList(HttpServletRequest request) {
        return userService.userList();
    }

    @PostMapping("user/account/trans")
    public Response<UserAccountTransResponse> userAccountTrans(HttpServletRequest request, @Valid @RequestBody UserAccountTransRequest userAccountTransRequest) {
        User user = userService.getUserByToken(request.getHeader(Constants.TOKEN));
        return userService.userAccountTrans(userAccountTransRequest, user);
    }
}
