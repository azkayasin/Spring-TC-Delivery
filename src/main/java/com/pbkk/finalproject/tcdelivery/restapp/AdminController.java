package com.pbkk.finalproject.tcdelivery.restapp;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pbkk.finalproject.tcdelivery.aop.AdminTokenRequired;
import com.pbkk.finalproject.tcdelivery.dao.TokenDAO;
import com.pbkk.finalproject.tcdelivery.dao.UserDAO;
import com.pbkk.finalproject.tcdelivery.model.User;
import com.pbkk.finalproject.tcdelivery.service.SecurityService;
import com.pbkk.finalproject.tcdelivery.service.UserService;
import com.pbkk.finalproject.tcdelivery.util.Util;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	TokenDAO tokenDAO;
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	UserService userService;
	
	
	@ResponseBody
	@AdminTokenRequired
	@RequestMapping(value = "/register/admin", method = RequestMethod.POST)
	public Map<String, Object> registerAdmin(
				@RequestParam(value = "username") String username,
				@RequestParam(value = "name") String name,
				@RequestParam(value = "email") String email,
				@RequestParam(value = "password") String password,
				@RequestParam(value = "phone_number") String phoneNumber
			) 
	{
		userService.checkAvailable(username,email);
		userService.validate(email);
		
		User usr=new User();
		usr.setName(name);
		usr.setUserName(username);
		usr.setPassword(password);
		usr.setEmail(email);
		usr.setPhoneNumber(phoneNumber);
		usr.setStatus(1);
		usr.setRole(3);
		usr.setCreatedAt(new Date());
		userDAO.save(usr);
		return Util.getSuccessResult();
	}

}