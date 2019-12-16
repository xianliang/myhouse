package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.model.House;
import com.example.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liangxianliang
 * @create 2019-12-03 16:00
 */
@Controller
public class HomepageController {

    @Autowired
    RecommendService recommendService;

    @RequestMapping("index")
    public String index(ModelMap modelMap){
        IPage<House> houses = recommendService.getLastest();
        modelMap.put("recomHouses",houses.getRecords());
        return "/homepage/index";
    }
}
