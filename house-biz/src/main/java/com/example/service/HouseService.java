package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.model.Community;
import com.example.common.model.House;
import com.example.mapper.CommunityMapper;
import com.example.mapper.HouseMapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liangxianliang
 * @create 2019-12-03 16:45
 */
@Service
public class HouseService {

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    CommunityMapper communityMapper;
    @Value("${file.prefix}")
    private String imgPrefix;
    public IPage<House> queryAndSetImg(House house,Integer pageSize,Integer pageNum){
        house.setState(1);
       IPage<House> houseIPage = new Page<>(pageNum,pageSize);
       QueryWrapper<House> queryWrapper = new QueryWrapper<>();
       if(null != house.getIds()){
           queryWrapper.in("id",house.getIds());
       }
       if(StringUtils.isNotBlank(house.getSort())){
           queryWrapper.orderByDesc(house.getSort());
       }
       if(null != house.getCommunityId()){
           queryWrapper.eq("community_id",house.getCommunityId());
       }
       if(null != house.getId()){
           queryWrapper.eq("id",house.getId());
       }
       if(null != house.getType()){
           queryWrapper.eq("type",house.getType());
       }
       queryWrapper.eq("state",house.getState());
       houseIPage = houseMapper.selectPage(houseIPage,queryWrapper);
       List<House> houseList = houseIPage.getRecords();
       houseList.forEach(h ->{
           h.setFirstImg(imgPrefix + h.getFirstImg());
           h.setImageList(h.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
           h.setFloorPlanList(h.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
       });
       houseIPage.setRecords(houseList);
    return houseIPage;
    }

    public IPage<House> queryHouse(House query,Integer pageSize,Integer pageNum){
        if(StringUtils.isNotBlank(query.getName())){
            QueryWrapper queryWrapper = new QueryWrapper();
            if(StringUtils.isNotBlank(query.getName())){
                queryWrapper.eq("name",query.getName());
            }
            List<Community> communityList = communityMapper.selectList(queryWrapper);
            if(!communityList.isEmpty()){
                query.setCommunityId(communityList.get(0).getId());
            }
        }
        IPage<House> houses = queryAndSetImg(query,pageSize==null?5:pageSize,pageNum==null ?0:pageNum);
        return houses;
    }

    public House queryOneHouse(Long id){
        House house = new House();
        house.setId(id);
        IPage<House> houseIPage = queryAndSetImg(house,5,1);
        List<House> houseList = houseIPage.getRecords();
        if(!houseList.isEmpty()){
            return houseList.get(0);
        }
        return null;
    }


}
