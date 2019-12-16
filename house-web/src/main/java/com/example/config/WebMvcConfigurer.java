package com.example.config;

import com.example.aop.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * @author liangxianliang
 * @create 2019-12-03 19:37
 */
@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    public void addInterceptors(InterceptorRegistry registry) {
        String[] exclude = new String[]{"/error","/static/**"};
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**").excludePathPatterns(exclude);
    }
}
