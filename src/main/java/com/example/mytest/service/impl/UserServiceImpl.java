package com.example.mytest.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.mytest.common.exception.BusinessException;
import com.example.mytest.mapper.UserMapper;
import com.example.mytest.model.domain.User;
import com.example.mytest.request.UserLoginRequest;
import com.example.mytest.request.UserRegRequest;
import com.example.mytest.response.Response;
import com.example.mytest.response.UserLoginResponse;
import com.example.mytest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/923:57
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    Map<String, User> tokenMap = new HashMap<>();


    @Override
    public User addUser(UserRegRequest userRegRequest) {
        checkUserExist(userRegRequest);
        User users = new User();
        users.setName(userRegRequest.getName());
        users.setMobile(userRegRequest.getMobile());
        users.setPassword(org.apache.commons.codec.digest.DigestUtils.sha256Hex(JSON.toJSONString(userRegRequest.getPassword())));
        users.setCreateTime(new Date());
        users.setUpdateTime(new Date());
        users.setStatus("1");
        insert(users);
        return users;
    }

    @Override
    public Response<UserLoginResponse> userLogin(UserLoginRequest userLoginRequest) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getMobile, userLoginRequest.getMobile()));
        if (user == null) {
            return Response.fail(-1, "用户名或密码错误");
        }
        if (!user.getPassword().equals(org.apache.commons.codec.digest.DigestUtils.sha256Hex(JSON.toJSONString(userLoginRequest.getPassword())))) {
            return Response.fail(-1, "用户名或密码错误");
        }
        String token = UUID.randomUUID().toString();
        updateToken(token, user);
        saveToken(token, user);
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setToken(token);
        return Response.success(userLoginResponse);
    }

    private void updateToken(String token, User users) {
        users.setLastLoginToken(token);
        LambdaUpdateWrapper<User> userUpdateWrapper = new LambdaUpdateWrapper<User>()
                .set(User::getLastLoginToken, token)
                .set(User::getLastLoginTime, new Date())
                .set(User::getUpdateTime, new Date())
                .eq(User::getId, users.getId());
        userMapper.update(users, userUpdateWrapper);
    }

    private void saveToken(String token, User user) {
        tokenMap.put(token, user);

    }


    private void insert(User users) {
        userMapper.insert(users);
    }

    private boolean checkUserExist(UserRegRequest userRegRequest) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getMobile, userRegRequest.getMobile()));
        if (user != null) {
            throw BusinessException.valueOf(-1, "用户手机号码已经注册");
        }
        return true;
    }
}
