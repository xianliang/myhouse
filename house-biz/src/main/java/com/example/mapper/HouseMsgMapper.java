package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.model.HouseMsg;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.stereotype.Repository;

/**
 * @author liangxianliang
 * @create 2019-12-10 18:17
 */
@Repository
public interface HouseMsgMapper extends BaseMapper<HouseMsg> {
}
