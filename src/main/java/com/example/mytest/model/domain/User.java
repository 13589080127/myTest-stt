package com.example.mytest.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Primary;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/923:56
 */
@Data
@TableName("user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String password;

    private String mobile;

    private String lastLoginToken;

    private Date lastLoginTime;

    private String payPassword;

    private Date createTime;

    private Date updateTime;

    private String status;

    private long money;
}
