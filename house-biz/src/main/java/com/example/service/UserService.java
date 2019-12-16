package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.model.House;
import com.example.common.model.User;
import com.example.common.utils.HashUtils;
import com.example.mapper.UserMapper;
import com.google.common.collect.Lists;
import net.sf.jsqlparser.statement.execute.Execute;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);




  @Autowired
  private UserMapper userMapper;
  @Autowired
  private FileService fileService;
  @Autowired
  private MailService mailService;

  @Value("${file.prefix}")
  private String imgPrefix;



    /**
     * 1.插入数据库，非激活；密码加盐MD5；保存头像文件到本地 2.生成key,绑定email 3.发送邮件给用户
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(User account){
        account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
       List<String> imgList = fileService.getImgPaths(Lists.newArrayList(account.getAvatarFile()));
        if(!imgList.isEmpty()){
            account.setAvatar(imgList.get(0));
        }
        account.setEnable(0);
        userMapper.insert(account);
        //TODO 发送邮箱
        mailService.registerNotify(account.getEmail());
        return true;
    }

    public User auth(String username, String password){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("email",username);
        queryWrapper.eq("enable",1);
        User user = userMapper.selectOne(queryWrapper);
        if(user != null && HashUtils.encryPassword(password).equals(user.getPasswd())){
            return user;
        }else{
            return null;
        }
    }

    public void updateUser(User updateUser){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("email", updateUser.getEmail());
        userMapper.update(updateUser,updateWrapper);
    }

    public  User getUserByQuery(User user){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("email", user.getEmail());
        User u = userMapper.selectOne(queryWrapper);
        u.setAvatar(imgPrefix+u.getAvatar());
        return u;
    }

    public User getUserById(Long id){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        List<User> userList = userMapper.selectList(queryWrapper);
        if(!userList.isEmpty()){
            return userList.get(0);
        }
        return null;
    }


  public IPage<User> getUsers(User user,Integer pageNum,Integer pageSize) {
      IPage<User> userPage = new Page<User>(pageNum==null?1:pageNum,pageSize==null?10:pageSize);
      QueryWrapper queryWrapper = new QueryWrapper();
      if(null != user.getType()){
          queryWrapper.eq("type",user.getType());
      }
      queryWrapper.eq("enable",1);
      userPage =userMapper.selectPage(userPage,queryWrapper);
      //userPage.setRecords(userMapper.selectUsers(userPage));
      return userPage;
  }





}
