package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.model.Comment;
import com.example.common.model.User;
import com.example.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liangxianliang
 * @create 2019-12-10 18:20
 */
@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserService userService;
    public List<Comment> getHouseComments(Long houseId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("house_id",houseId);
        queryWrapper.eq("type",1);
        queryWrapper.orderByDesc("create_time");
        List<Comment> commentList =commentMapper.selectList(queryWrapper);
        commentList.forEach(comment -> {
            User user = userService.getUserById(comment.getUserId());
            comment.setAvatar(user.getAvatar());
            comment.setUserName(user.getName());
        });
        return commentList;
    }
}
