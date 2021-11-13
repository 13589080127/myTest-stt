package com.example.mytest.controller;

import com.alibaba.fastjson.JSON;
import com.example.mytest.model.domain.User;
import com.example.mytest.request.UserLoginRequest;
import com.example.mytest.request.UserRegRequest;
import com.example.mytest.response.Response;
import com.example.mytest.response.UserLoginResponse;
import com.example.mytest.response.UserRegResponse;
import com.example.mytest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public Response<UserRegResponse> userReg(@RequestBody UserRegRequest userRegRequest) {
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
        Response<UserLoginResponse> response = userService.userLogin(userRegRequest);
        return response;
    }

}
