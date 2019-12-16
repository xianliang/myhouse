package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.model.House;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author liangxianliang
 * @create 2019-12-03 17:11
 */
@Service
public class RecommendService {
    private  final  String HOT_HOUSE_KEY = "hot_house";

    private static final Logger logger = LoggerFactory.getLogger(RecommendService.class);
    @Autowired
    HouseService houseService;

    public IPage<House> getLastest() {
        House house = new House();
        house.setSort("create_time");
        house.setState(1);
        return houseService.queryAndSetImg(house,8,1);
    }

    public void increase(Long id){
        try {
            Jedis jedis = new Jedis("127.0.0.1");
            jedis.zincrby(HOT_HOUSE_KEY,1.0D,id + "");
            jedis.zremrangeByRank(HOT_HOUSE_KEY,0,-11);
            jedis.close();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
    public List<Long> getHot(){
        try {
            Jedis jedis = new Jedis("127.0.0.1");
            Set<String> idSet = jedis.zrevrange(HOT_HOUSE_KEY,0,-1);
            jedis.close();
            List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());
            return ids;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return Lists.newArrayList();
        }

    }

    public List<House> getHotHouse(Integer size){
        House query = new House();
        List<Long> list = getHot();
        list = list.subList(0,Math.min(list.size(),size));
        if(list.isEmpty()){
            return Lists.newArrayList();
        }
        query.setIds(list);
        final List<Long> order = list;
        IPage<House> houses = houseService.queryAndSetImg(query,size,1);
        Ordering<House> house = Ordering.natural().onResultOf(hs ->{
            return  order.indexOf(hs.getId());
        });
        return house.sortedCopy(houses.getRecords());
    }



}
