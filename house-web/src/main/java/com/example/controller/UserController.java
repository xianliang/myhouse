package com.example.controller;

import com.example.common.model.User;
import com.example.common.result.ResultMsg;
import com.example.service.AgencyService;
import com.example.service.MailService;
import com.example.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author liangxianliang
 * @create 2019-11-15 14:48
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private MailService mailService;

    @RequestMapping("/list")
    public Object list(String key) {
        return userService.getUsers(new User(),1,10);
    }

    /**
     * 注册接口
     * @param account
     * @param modelMap
     * @return
     */
    @RequestMapping("/accounts/register")
    public  String accountRegister(User account, ModelMap modelMap){
        if(account == null || account.getName() ==null){
            modelMap.put("agencyList",agencyService.getAllAgency());
            return "/user/accounts/register";
        }
        ResultMsg resultMsg = UserHelper.validate(account);
        if(resultMsg.isSuccess() && userService.addAccount(account)){
            modelMap.put("email",account.getEmail());
            return "/user/accounts/registerSubmit";
        }else {
            return "redirect:/accounts/register?" + resultMsg.asUrlParams();
        }
    }

    @RequestMapping("accounts/verify")
    public String verify(String key){
        boolean result = mailService.enable(key);
        if(result){
            return "redirect:/index?"+ ResultMsg.successMsg("激活成功").asUrlParams();
        } else {
            return "redirect:/accounts/register?"+ ResultMsg.errorMsg("激活失败，请确认链接是否过期");
        }
    }

    /**
     * 登录
     * @param req
     * @return
     */
    @RequestMapping("/accounts/signin")
    public  String signin(HttpServletRequest req){
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String target = req.getParameter("target");
        if(username == null || password == null){
            req.setAttribute("target",target);
            return "/user/accounts/signin";
        }
        User user = userService.auth(username,password);
        if(user == null){
            return "redirect:/accounts/signin?"+target+ "&username=" + username + "&"
                    + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        }else {
            HttpSession session = req.getSession();
            session.setAttribute("loginUser",user);
            return StringUtils.isNoneBlank(target)?"redirect:"+target
                    : "redirect:/index";
        }
    }
    @RequestMapping("/accounts/profile")
    public String profile(HttpServletRequest req,User updateUser,ModelMap modelMap){
        if(updateUser.getEmail() == null){
            return "/user/accounts/profile";
        }
        userService.updateUser(updateUser);
        User user = userService.getUserByQuery(updateUser);
        req.getSession().setAttribute("loginUser",user);
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }
}
