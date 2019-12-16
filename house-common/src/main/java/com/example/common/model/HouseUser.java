package com.example.common.model;

import lombok.Data;

import java.util.Date;

/**
 * @author liangxianliang
 * @create 2019-12-10 17:20
 */
@Data
public class HouseUser {
    private Long id;
    private Long houseId;
    private Long userId;
    private Date createTime;
    private Integer type;
}
