package com.example.mytest.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2021/11/229:41
 */
@Data
@AllArgsConstructor
public class Order implements Serializable {

    private Integer orderId;
    private int num;
    private int amount;
    private double aDouble;
}
