package com.example.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author liangxianliang
 * @create 2019-12-20 11:47
 */
@Data
@Accessors(chain = true)
public class Order {
    private String orderId;
    private String orderContent;
    private Date createTime;
}
