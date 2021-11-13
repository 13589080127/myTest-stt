package com.example.mytest.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/1315:49
 */
@Data
public class UserSetPayPassRequest implements Serializable {
    /**
     * 支付密码
     */
    @NotBlank(message = "支付密码不能为空")
    @Length(min = 6, max = 6, message = "支付密码长度错误")
    private String payPassword;
}
