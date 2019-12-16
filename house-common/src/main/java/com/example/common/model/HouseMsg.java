package com.example.common.model;

import lombok.Data;

import java.util.Date;

/**
 * @author liangxianliang
 * @create 2019-12-10 18:14
 */
@Data
public class HouseMsg {
    private Long id;
    private String msg;
    private Date createTime;
    private Long agentId;
    private Long houseId;
    private String userName;
}
