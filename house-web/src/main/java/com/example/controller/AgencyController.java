package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.constants.CommonConstants;
import com.example.common.model.Agency;
import com.example.common.model.House;
import com.example.common.model.User;
import com.example.common.page.PageData;
import com.example.common.result.ResultMsg;
import com.example.service.*;
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
    @Autowired
    HouseService houseService;

    @Autowired
    MailService mailService;

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

    @RequestMapping("agency/create")
    public String agencyCreate(){
        return "";
    }

    @RequestMapping("/agency/agentDetail")
    public String agentDetail(Long id , ModelMap modelMap){
        User user = agencyService.getAgentDetail(id);
        List<House> houses = recommendService.getHotHouse();
        House query = new House();
        query.setUserId(id);
        query.setBookmarked(false);
        IPage<House> houseIPage = houseService.queryHouse(query,10,1);
        if(houseIPage != null){
            modelMap.put("bindHouses",houseIPage.getRecords());
        }
        modelMap.put("recomHouses", houses);
        modelMap.put("agent", user);
        modelMap.put("agencyName", user.getAgencyName());
        return "user/agent/agentDetail";

    }

    @RequestMapping("agency/agentMsg")
    public String agentMsg(Long id,String msg,String name,String email,ModelMap modelMap ){
        User user = agencyService.getAgentDetail(id);
        mailService.sendMail("","email:"+email+"msg:"+msg,user.getEmail());
        return "redirect:agency/agentDetail?id="+id+"&"+ ResultMsg.successMsg("成功").asUrlParams();
    }




}
