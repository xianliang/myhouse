package com.example.controller;

import com.example.common.model.User;
import com.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author liangxianliang
 * @create 2019-12-23 19:23
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "comment/leaveComment",method = {RequestMethod.POST,RequestMethod.GET})
    public String leaveComment(String content, Long houseId, ModelMap modelMap){
        //User  user =
        Long  userId = 0l;
        commentService.addHouseComment(houseId,content,userId);
        return "redirect:/house/detail?id=" + houseId;
    }

    public String leaveBlogComment(String content, Integer blogId, ModelMap modelMap, RedirectAttributes redirectAttributes){
        //User user = UserContext.getUser;
        Long userId = 0l;
        commentService.addBlogCommet(blogId,content,userId);
        return "redirect:/blog/detail?id=" + blogId;
    }
}
