package com.example.mytest.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.mytest.common.Constants;
import com.example.mytest.common.enums.ErrorCodeEnum;
import com.example.mytest.common.exception.BusinessException;
import com.example.mytest.mapper.UserMapper;
import com.example.mytest.model.domain.User;
import com.example.mytest.request.*;
import com.example.mytest.response.Response;
import com.example.mytest.response.UserAccountTransResponse;
import com.example.mytest.response.UserCheckPayPasswordResponse;
import com.example.mytest.response.UserLoginResponse;
import com.example.mytest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/923:57
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


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
        saveToken(token, user, user.getLastLoginToken());
        updateToken(token, user);
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setToken(token);
        return Response.success(userLoginResponse);
    }

    @Override
    public void setPayPassword(UserSetPayPassRequest userSetPayPassRequest, User user) {
        if (StringUtils.isNotBlank(user.getPayPassword())) {
            log.info("用户已设置支付密码 不能重复设置");
            throw BusinessException.valueOf(-1, "用户已经设置支付密码不能重复设置");
        }
        user.setPayPassword(DigestUtils.sha256Hex(userSetPayPassRequest.getPayPassword()));
        LambdaUpdateWrapper<User> userUpdateWrapper = new LambdaUpdateWrapper<User>()
                .set(User::getPayPassword, user.getPayPassword())
                .set(User::getLastLoginTime, new Date())
                .set(User::getUpdateTime, new Date())
                .eq(User::getId, user.getId());
        userMapper.update(user, userUpdateWrapper);
        stringRedisTemplate.opsForValue().set(Constants.USER_TOKEN_KEY + user.getLastLoginToken(), JSON.toJSONString(user), 2, TimeUnit.DAYS);

    }

    @Override
    public Response<UserCheckPayPasswordResponse> checkPayPassword(UserCheckPayPasswordRequest userCheckPayPasswordRequest, User user) {
        String password = userCheckPayPasswordRequest.getPayPassword();
        String userPassword = user.getPassword();
        if (!DigestUtils.sha256Hex(password).equals(userPassword)) {
            log.info("支付密码错误：{} ", userCheckPayPasswordRequest.getPayPassword());
            throw BusinessException.valueOf(ErrorCodeEnum.PAY_PASS_ERROR);
        }
        UserCheckPayPasswordResponse userCheckPayPasswordResponse = new UserCheckPayPasswordResponse();
        userCheckPayPasswordResponse.setPayPasswordToken(UUID.randomUUID().toString());
        stringRedisTemplate.opsForValue().set(Constants.PAY_PASS_WORD_TOKEN_KEY + userCheckPayPasswordResponse.getPayPasswordToken(), user.getId() + "", 1, TimeUnit.MINUTES);
        return Response.success(userCheckPayPasswordResponse);
    }

    @Override
    public User getUserByToken(String token) {
        String userJson = stringRedisTemplate.opsForValue().get(Constants.USER_TOKEN_KEY + token);
        return JSON.parseObject(userJson, User.class);
    }

    @Override
    public Response<List<User>> userList() {
        List<User> list = userMapper.selectList(new QueryWrapper<>());
        return Response.success(list);
    }

    @Override
    @Transactional
    public Response<UserAccountTransResponse> userAccountTrans(UserAccountTransRequest userAccountTransRequest, User user) {
        String payPasswordToken = userAccountTransRequest.getPayPasswordToken();
        String redisValue = stringRedisTemplate.opsForValue().get(Constants.PAY_PASS_WORD_TOKEN_KEY + payPasswordToken);
        if (StringUtils.isNotBlank(redisValue) && !redisValue.equals(user.getId() + "")) {
            log.info("支付密码凭证错误或已过期 redis:{} in :{}", redisValue, user.getId());
            throw BusinessException.valueOf(ErrorCodeEnum.PAY_PASS_TOKEN_ERROR);
        }
        User targetUser = userMapper.selectById(userAccountTransRequest.getId());
        if (targetUser == null || targetUser.getStatus().equals("1")) {
            log.info("用户不存在或用户状态异常 targetUser:{}", JSON.toJSONString(targetUser));
            throw BusinessException.valueOf(ErrorCodeEnum.IN_ACCOUNT_ERROR);
        }
        user = userMapper.selectById(user.getId());
        if (user.getMoney() < userAccountTransRequest.getMoney()) {
            log.info("用户余额不足 不允许转账 账户余额：{}", user.getMoney());
            throw BusinessException.valueOf(ErrorCodeEnum.INSUFFICIENT_BALANCE);
        }
        user.setMoney(user.getMoney() - userAccountTransRequest.getMoney());
        targetUser.setMoney(targetUser.getMoney() + userAccountTransRequest.getMoney());
        userMapper.update(user, new LambdaUpdateWrapper<User>().set(User::getMoney, user.getMoney()).set(User::getUpdateTime, new Date()).eq(User::getId, user.getId()));
        userMapper.update(targetUser, new LambdaUpdateWrapper<User>().set(User::getMoney, targetUser.getMoney()).set(User::getUpdateTime, new Date()).eq(User::getId, targetUser.getId()));
        UserAccountTransResponse accountTransResponse = new UserAccountTransResponse();
        accountTransResponse.setOrderId(generateOrderId());
        return Response.success(accountTransResponse);
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH:mm:ss sss");

    private String generateOrderId() {
        String flag = "001";
        Long value = stringRedisTemplate.opsForValue().increment(Constants.ORDER_ID_KEY);
        //时间戳 机器标识+ 8位订单号
        return simpleDateFormat.format(new Date()) + flag + StringUtils.leftPad(value + "", 8, '0');
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

    private void saveToken(String token, User user, String oldToken) {
        stringRedisTemplate.delete(Constants.USER_TOKEN_KEY + oldToken);
        stringRedisTemplate.opsForValue().set(Constants.USER_TOKEN_KEY + token, JSON.toJSONString(user), 2, TimeUnit.DAYS);
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
