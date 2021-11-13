package com.example.mytest.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/1317:18
 */
@Data
public class UserCheckPayPasswordResponse  implements Serializable {
    /**
     * 支付密码token
     */
    private String payPasswordToken;
}
