package com.yosep.website.client;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yosep.website.entity.User;

@CrossOrigin
@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");

		return mav;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("register");

		return mav;
	}

	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	public ModelAndView loginCheck(@ModelAttribute User user, HttpSession session) {
		// 세션 변수 등록
		User getUser = restTemplate.getForObject("http://user-apigateway/api/user/checkedupid?userId=" + user.getUserId(),
				User.class);
		session.setAttribute("userId", getUser.getUserId());
		session.setAttribute("userName", getUser.getName());

		int status = restTemplate.postForObject("http://user-apigateway/api/user/loginCheck", user, int.class);
//		
		ModelAndView mav = new ModelAndView();

		if (status == 200) {
			mav.setViewName("redirect:/main");
			mav.addObject("login", "success");
		} else {
			mav.setViewName("redirect:/login");
			mav.addObject("login", "fail");
		}

		return mav;
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String showMainPage() {
		return "main";
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public String userRegister(@ModelAttribute User user) {
		int status = restTemplate.postForObject("http://user-apigateway/api/user/register", user, int.class);
//		int status = 200;
		if (status == 200)
			return "redirect:/login";
		else
			return "redirect:/register";
	}

	@RequestMapping(value = "/checkdupid", method = RequestMethod.GET)
	public ResponseEntity<String> checkDupId(@RequestParam("userId") String userId) {
//		boolean isDup = restTemplate.getForObject("http://localhost:8065/api/user/checkedupid?userId="+userId, Boolean.class);
//		System.out.println(userId);
		User user = restTemplate.getForObject("http://user-apigateway/api/user/checkedupid?userId=" + userId,
				User.class);
		// boolean isDup = true;

		if (user != null) {
			return new ResponseEntity<String>("true", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("false", HttpStatus.OK);
		}
	}
}
