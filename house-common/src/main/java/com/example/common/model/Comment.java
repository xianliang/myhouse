package com.example.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author liangxianliang
 * @create 2019-12-10 18:08
 */
@Data
public class Comment {
    private Long id;
    private String content;
    private Long houseId;
    private Date createTime;
    private Long blogId;
    private Integer type;
    private Long userId;

    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String avatar;

}
