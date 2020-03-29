package com.yosep.user.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;

import com.yosep.user.entity.User;
import com.yosep.user.repository.UserDAO;

//@EnableFeignClients
@RefreshScope
@Component
public class UserComponent {
	@Autowired
	UserDAO userDAO;
	
	public UserComponent() {}
	
	public int createUser(User user) {
		int result = userDAO.createUser(user);
		
		return result;
	}
	
	public User checkDupId(String userId) {
		User user = userDAO.sameIdCheck(userId);
		
		return user;
	}
	
	public int loginCheck(User user) {
		boolean result = userDAO.loginCheck(user);
		
		return (result == true) ? 200 : -1;
	}
}
