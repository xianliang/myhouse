package com.example.common.model;

/**
 * @author liangxianliang
 * @create 2019-11-15 18:12
 */


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String email;

    private String phone;

    private String name;

    private String passwd;

    @TableField(exist = false)
    private String confirmPasswd;

    private Integer type;//普通用户1，经纪人2

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date   createTime = new Date();

    private Integer enable;

    private String  avatar;

    private Long   agencyId;

    private String aboutme;

    @TableField(exist = false)
    private MultipartFile avatarFile;

    @TableField(exist = false)
    private String newPassword;

    @TableField(exist = false)
    private String key;

    @TableField(exist = false)
    private String agencyName;






}

