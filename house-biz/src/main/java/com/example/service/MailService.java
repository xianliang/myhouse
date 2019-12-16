package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.model.User;
import com.example.mapper.UserMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author liangxianliang
 * @create 2019-12-11 14:14
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserMapper userMapper;


    @Value("${spring.mail.username}")
    private   String from ;

    @Value("${domainName}")
    private String domainName;

    private final Cache<String,String> registerCache =
            CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES)
            .removalListener(new RemovalListener<String, String>() {
                @Override
                public void onRemoval (RemovalNotification<String,String> notification){
                    String email = notification.getValue();
                    QueryWrapper queryWrapper = new QueryWrapper();
                    queryWrapper.eq("email",email);
                    User user =userMapper.selectOne(queryWrapper);
                    if(user != null && 0 ==user.getEnable()){
                        userMapper.delete(queryWrapper);//邮件过期，对于未激活的用户进行删除操作
                    }

                }
            }).build();

    @Async
    public void registerNotify(String email){
        try {

            Thread.sleep(5000);
        }catch (Exception e){

        }
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        registerCache.put(randomKey,email);
        String url = "http://"+ domainName +"/accounts/verify?key=" + randomKey;
        sendMail("激活邮件",url,email);
    }

    private void sendMail(String title,String url,String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject(title);
        message.setTo(email);
        message.setText(url);
        mailSender.send(message);
    }

    public boolean enable(String key){
        String email = registerCache.getIfPresent(key);
        if(StringUtils.isBlank(email)){
            return false;
        }
        User updateUser = new User();
        updateUser.setEmail(email);
        updateUser.setEnable(1);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("email",email);
        userMapper.update(updateUser,queryWrapper);
        registerCache.invalidate(key);
        return true;
    }



}
