package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.constants.CommonConstants;
import com.example.common.model.Agency;
import com.example.common.model.House;
import com.example.common.model.User;
import com.example.common.page.PageData;
import com.example.service.AgencyService;
import com.example.service.RecommendService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author liangxianliang
 * @create 2019-12-11 18:13
 */
@Controller
public class AgencyController {
    @Autowired
    AgencyService agencyService;
    @Autowired
    UserService userService;
    @Autowired
    RecommendService recommendService;

    @RequestMapping("/agency/agentList")
    public String agentList(Integer pageSize,Integer pageNum,ModelMap modelMap){
        User query = new User();
        query.setType(2);
        IPage<User> agentUserPage = userService.getUsers(query,pageNum,pageSize);
        PageData<User> ps = PageData.buildPage(agentUserPage.getRecords(),agentUserPage.getTotal(),(int)agentUserPage.getSize(),(int)agentUserPage.getCurrent());
        List<House> houses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses",houses);
        modelMap.put("ps",ps);
        return "/user/agent/agentList";
    }

    @RequestMapping("/agency/list")
    public String agencyList(ModelMap modelMap){
        List<Agency> agencies = agencyService.getAllAgency();
        List<House> houses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses",houses);
        modelMap.put("agencyList",agencies);
        return "/user/agency/agencyList";
    }
}
