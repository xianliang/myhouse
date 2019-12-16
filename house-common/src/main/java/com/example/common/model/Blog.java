package com.example.common.model;

import lombok.Data;

import javax.swing.text.AbstractDocument;
import java.util.Date;

/**
 * @author liangxianliang
 * @create 2019-12-10 18:12
 */
@Data
public class Blog {
    private Long id;
    private String tags;
    private String content;
    private Date createTime;
    private String title;
    private String cat;
}
