package com.example.mytest.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/1317:26
 */
@Data
public class UserAccountTransRequest implements Serializable {

    @NotNull(message = "收款方不能为空")
    private Integer id;
    @NotNull(message = "转出金额不能为空")
    @Max(50000)
    private Long money;
    @Length(min = 0, max = 100)
    private String remark;
    @NotBlank(message = "支付密码不能为空")
    private String payPasswordToken;
}
