package com.example.mytest.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/923:56
 */
@Data
@TableName("t_contract")
public class Contract implements Serializable {
    private Integer id;
}
