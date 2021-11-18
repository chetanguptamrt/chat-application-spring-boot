package com.chat.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chat.entity.Message;
import com.chat.entity.User;
import com.chat.helper.SearchUser;
import com.chat.service.ChatService;
import com.chat.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatService chatService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Principal principal, Model model) {
		if(principal == null) {
			model.addAttribute("title", "Login | Messages Application");
			return "public/signin";
		}
		User user = this.userService.getUserByEmail(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("today",new Date());
		model.addAttribute("title", "Home | Messages Application");
		model.addAttribute("chats", this.chatService.inboxList(user));
		return "user/index";
	}

	@RequestMapping(value = "/profile/{username}", method = RequestMethod.GET)
	public String profile(@PathVariable("username") String username,Principal principal, Model model) {
		try {
			User userByUserName = this.userService.getUserByUserName(username);
			model.addAttribute("profileUser", userByUserName);
			model.addAttribute("title", userByUserName.getFirstName()+" "+userByUserName.getLastName()+" | Messages Application");
			User user = this.userService.getUserByEmail(principal.getName());
			model.addAttribute("user", user);
			return "user/profile";	
		} catch (Exception e) {
			return "error/404";
		}
	}
	
	@RequestMapping(value = "/u/settings", method = RequestMethod.GET)
	public String settings(Principal principal, Model model) {
		User user = this.userService.getUserByEmail(principal.getName());
		model.addAttribute("user", user);
		return "user/settings";
	}
	
	@RequestMapping(value = "/chat/{username}", method = RequestMethod.GET)
	public String chat(@PathVariable("username") String username, Model model, Principal principal) {
		try {
			User userByUserName = this.userService.getUserByUserName(username);
			model.addAttribute("profileUser", userByUserName);
			model.addAttribute("title", userByUserName.getFirstName()+" "+userByUserName.getLastName()+" | Messages Application");
			User user = this.userService.getUserByEmail(principal.getName());
			model.addAttribute("user", user);
			List<Message> last20Messages = this.chatService.last20Messages(user, userByUserName);
			model.addAttribute("chats", last20Messages);
			if(last20Messages.size()==20) {
				model.addAttribute("firstChat", last20Messages.get(0).getId());	
			}
			return "user/chat";	
		} catch (Exception e) {
			return "error/404";
		}
	}
	
	@RequestMapping(value = "/u/search-user", method = RequestMethod.GET)
	public ResponseEntity<List<SearchUser>> searchUser(@RequestParam("s") String s){
		return ResponseEntity.ok(this.userService.searchUserByName(s));
	}
	
}
