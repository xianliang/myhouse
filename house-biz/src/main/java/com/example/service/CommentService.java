package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.constants.CommonConstants;
import com.example.common.model.Comment;
import com.example.common.model.User;
import com.example.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.metadata.CompositeDataSourcePoolMetadataProvider;
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

    public void addHouseComment(Long houseId, String content, Long userId) {
        addComment(houseId,null,content,userId,CommonConstants.COMMENT_HOUSE_TYPE);
    }

    public void addBlogCommet(int blogId,String content,Long userId){
        addComment(null,blogId,content,userId, CommonConstants.COMMENT_BLOG_TYPE);
    }

    private void addComment(Long houseId,Integer blogId,String content,Long userId,int type){
        Comment comment = new Comment();
        if(type == 1){
            comment.setHouseId(houseId);
        }else{
            comment.setBlogId(blogId);
        }
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setType(type);
        //BeanH
        commentMapper.insert(comment);
    }
}
