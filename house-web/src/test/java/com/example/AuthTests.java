package com.example;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.common.model.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= WebEnvironment.DEFINED_PORT)
public class AuthTests {

	@Autowired
	private UserMapper userService;

	@Test
	public void testAuth() {
		User user = new User();
		user.setName("asss");
		user.setPhone("15173435478");
		user.setEmail("lxl@meirichexian.com");
		user.setId(33L);
		UpdateWrapper updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("email",user.getEmail());
		userService.update(user,updateWrapper);
		userService.updateById(user);
	}

}