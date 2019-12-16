package com.example.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.common.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liangxianliang
 * @create 2019-12-03 19:25
 */
@Component
public class LogInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        logger.info("*******************MVC业务处理开始**********************");
        try {

            String requestURL = request.getRequestURI();
            logger.info("当前请求的URL：【{}】", requestURL);
            logger.info("执行目标方法: {}", handler);

            Map<String, ?> params = request.getParameterMap();
            if (!params.isEmpty()) {
                logger.info("当前请求参数打印："+request.getParameterMap());
            }
        } catch (Exception e) {
            logger.error("MVC业务处理-拦截器异常：", e);
        }
        logger.info("*******************MVC业务处理结束**********************");
        return  true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        logger.info("请求ip："+request.getRemoteAddr());
        logger.info("请求的方法："+request.getMethod());
        if(modelAndView != null){
            logger.info("modelmap"+ modelAndView.getModelMap());
        }
    }
}
