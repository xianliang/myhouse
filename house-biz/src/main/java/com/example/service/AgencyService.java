package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.model.Agency;
import com.example.common.model.User;
import com.example.mapper.AgencyMapper;
import com.example.mapper.UserMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.management.Agent;

import java.util.List;

/**
 * @author liangxianliang
 * @create 2019-12-04 12:01
 */
@Service
public class AgencyService {
    @Autowired
    AgencyMapper agencyMapper;

    @Autowired
    UserMapper userMapper;

    @Value("${file.prefix}")
    private String imgPrefix;

    public List<Agency> getAllAgency(){
        return agencyMapper.selectList(null);
    }

    public User getAgentDetail(Long userID){
        User user = userMapper.selectById(userID);
        setImg(Lists.newArrayList(user));
        if(user != null){
            Agency agency = agencyMapper.selectById(user.getAgencyId());
            if(agency != null){
                user.setAgencyName(agency.getName());
            }
        }
        return user;
    }

    private void setImg(List<User> list){
        list.forEach(i->{
            i.setAvatar(imgPrefix + i.getAvatar());
        });
    }


}

