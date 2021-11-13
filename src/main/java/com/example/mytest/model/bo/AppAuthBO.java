package com.example.mytest.model.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/922:42
 */
@Data
public class AppAuthBO  implements Serializable {
    /**
     * 应用标识
     */
    private String appId;
    /**
     * 时间戳
     */
    private long timestamp;
}
