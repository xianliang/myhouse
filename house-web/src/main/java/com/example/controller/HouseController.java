package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.constants.CommonConstants;
import com.example.common.model.Comment;
import com.example.common.model.House;
import com.example.common.model.HouseUser;
import com.example.common.page.PageData;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author liangxianliang
 * @create 2019-12-05 19:21
 */
@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private HouseUserService houseUserService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AgencyService agencyService;

    @RequestMapping("/house/list")
    public  String  houseList(Integer pageSize, Integer pageNum, House house, ModelMap modelMap){
        IPage<House> houseIPage = houseService.queryHouse(house,pageSize,pageNum);

        PageData<House> ps = PageData.buildPage(houseIPage.getRecords(),
                houseIPage.getTotal(),(int)houseIPage.getSize(),(int)houseIPage.getCurrent());
        List<House> hotHouses = recommendService.getHotHouse(3);
        modelMap.put("recomHouses",hotHouses);
        modelMap.put("ps",ps);
        modelMap.put("vo",house);
        return "house/listing";
    }
    @RequestMapping("/house/detail")
    public  String houseDetail(Long id,ModelMap modelMap){
        House house = houseService.queryOneHouse(id);
        HouseUser houseUser = houseUserService.getHouseUser(id);
        recommendService.increase(id);
        List<Comment> comments = commentService.getHouseComments(id);
        if(houseUser.getUserId() !=null && !houseUser.getUserId().equals(0)){
            modelMap.put("agent",agencyService.getAgentDetail(id));
        }
        List<House> rcHouses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", rcHouses);
        modelMap.put("house",house);
        modelMap.put("commentList",comments);
        return "/house/detail";
    }


}
