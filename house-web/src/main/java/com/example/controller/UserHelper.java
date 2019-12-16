package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.common.model.User;
import com.example.common.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

/**
 * @author liangxianliang
 * @create 2019-12-04 14:15
 */
public class UserHelper {
    public static ResultMsg validate (User accout){
        if (StringUtils.isBlank(accout.getEmail())){
            return ResultMsg.errorMsg("Email 有误");
        }
        if(StringUtils.isBlank(accout.getPasswd())){
            return ResultMsg.errorMsg("密码不能为空");
        }
        if(StringUtils.isBlank(accout.getConfirmPasswd())
           || !accout.getPasswd().equals(accout.getConfirmPasswd())){
            return ResultMsg.errorMsg("两次密码不一致");
        }
        if(accout.getPasswd().length() < 6){
            return ResultMsg.errorMsg("密码不能小于6位");
        }
        return ResultMsg.successMsg("");

    }
}
