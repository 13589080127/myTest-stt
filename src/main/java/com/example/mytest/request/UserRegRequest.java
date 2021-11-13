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
 * @date 2021/11/923:07
 */
@Data
public class UserRegRequest implements Serializable {
    @NotBlank(message = "手机号码不能为空")
    @Length(max = 11, min = 11)
    @Pattern(regexp = Constants.MOBILE_REG, message = "手机号码格式春错误")
    private String mobile;
    @NotBlank(message = "登录密码不能为空")
    @Length(max = 20, min = 8, message = "登录密码长度错误")
    private String password;
    @NotBlank(message = "登录密码不能为空")
    @Length(min = 2, max = 5, message = "姓名长度错误")
    @Pattern(regexp = Constants.NAME_REG, message = "姓名格式错误")
    private String name;
}
