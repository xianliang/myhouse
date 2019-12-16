package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.model.HouseUser;
import com.example.mapper.HouseMapper;
import com.example.mapper.HouseUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liangxianliang
 * @create 2019-12-10 17:37
 */
@Service
public class HouseUserService {
    @Autowired
    HouseUserMapper houseUserMapper;

    public HouseUser getHouseUser(Long id){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        return houseUserMapper.selectOne(queryWrapper);
    }

}
