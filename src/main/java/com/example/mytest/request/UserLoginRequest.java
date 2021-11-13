package com.example.mytest.request;

import com.example.mytest.common.Constants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/100:11
 */
@Data
public class UserLoginRequest implements Serializable {
    @NotBlank(message = "登录手机号码不能为空")
    @Pattern(regexp = Constants.MOBILE_REG, message = "登录手机号码格式错误")
    private String mobile;
    @NotBlank(message = "登录密码不能为空")
    @Length(max = 20, min = 8, message = "登录密码长度错误")
    private String password;
}
