package com.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chat.entity.User;
import com.chat.service.UserService;

@Controller
public class MainController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("title", "Login | Messages Application");
		return "public/signin";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("title", "Registration | Messages Application");
		return "public/signup";
	}
	
	@ResponseBody
	@RequestMapping(value = "/registrationOTP", method = RequestMethod.POST)
	public String registrationOTP(@RequestParam("oEmail") String email) {
		if(email.trim().equals("")) {
			return "fillDetail";
		}
		return this.userService.userSendRegistrationOtp(email.trim());
	}

	@ResponseBody
	@RequestMapping(value = "/registrationuser", method = RequestMethod.POST)
	public String registrationuser(@ModelAttribute User user, @RequestParam("otp") String otp) {
		if(otp.trim().equals("") || user.getFirstName().trim().equals("") || user.getLastName().trim().equals("") ||
					user.getEmail().trim().equals("") || user.getPassword().trim().equals("") ) {
			return "fillDetail";
		}
		return this.userService.userRegistration(user,otp);
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public String forgot(Model model) {
		model.addAttribute("title", "Forgot | Messages Application");
		return "public/forgot";
	}

	@ResponseBody
	@RequestMapping(value = "/sendOtpForgot", method = RequestMethod.POST)
	public String sendOtpForgot(@RequestParam("oEmail") String email) {
		if(email.trim().equals("")) {
			return "fillDetail";
		}
		return this.userService.sendOtpForForgot(email.trim());
	}
	
	@ResponseBody
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public String forgotPassword(@RequestParam("email") String email, 
				@RequestParam("otp") String otp, 
				@RequestParam("password") String password) {
		if(email.trim().equals("") || otp.trim().equals("") || password.trim().equals("")) {
			return "fillDetail";
		}
		return this.userService.forgotPassword(email.trim(), otp.trim(), password.trim());
	}

	
}
