package com.yosep.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yosep.user.component.UserComponent;
import com.yosep.user.entity.User;

@RestController
@CrossOrigin
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	UserComponent userComponent;
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	int register(@RequestBody User user) {
//		System.out.println(user.getName());
		int result = userComponent.createUser(user);
//		int result = 200;
		
		if(result > 0) {
			return 200;
		}else {
			return -1;
		}
	}
	
	@RequestMapping(value="/loginCheck", method=RequestMethod.POST)
	int loginCheck(@RequestBody User user) {
		return userComponent.loginCheck(user) == 200 ? 200 : -1;
	}
	
	@RequestMapping(value="/checkedupid",method=RequestMethod.GET)
	User checkDupId(@RequestParam("userId") String userId) {
		User result = userComponent.checkDupId(userId);
//		boolean result = true;
		
		return result;
	}
}
