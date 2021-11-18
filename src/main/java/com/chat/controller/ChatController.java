package com.chat.controller;

import java.security.Principal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chat.entity.Message;
import com.chat.entity.User;
import com.chat.helper.ChatHelper;
import com.chat.service.ChatService;
import com.chat.service.UserService;

@Controller
public class ChatController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatService chatService;

	@ResponseBody
	@RequestMapping(value = "/u/send", method = RequestMethod.GET)
	public String sendMessage(@RequestParam("text") String text,
							@RequestParam("username") String username,
							Principal principal) {
		User user = this.userService.getUserByEmail(principal.getName());
		User profileUser = this.userService.getUserByUserName(username);
		String done = this.chatService.sendMessage(text, user, profileUser);
		return done;
	}

	@ResponseBody
	@RequestMapping(value = "/u/getmorechat", method = RequestMethod.GET)
	public ResponseEntity<List<Message>> getMessage(
						@RequestParam("username") String username,
						Principal principal) {
		User user = this.userService.getUserByEmail(principal.getName());
		User profileUser = this.userService.getUserByUserName(username);
		return ResponseEntity.ok(this.chatService.getNewMessages(user, profileUser));
	}
	
	@ResponseBody
	@RequestMapping(value = "/u/loadmorechat", method = RequestMethod.GET)
	public ResponseEntity<List<ChatHelper>> loadMoreChat(
						@RequestParam("username") String username,
						@RequestParam("firstChat") int firstChat,
						Principal principal) {
		User user = this.userService.getUserByEmail(principal.getName());
		User profileUser = this.userService.getUserByUserName(username);
		return ResponseEntity.ok(this.chatService.loadMoreMessages(user, profileUser,firstChat));
	}
	
	@ResponseBody
	@RequestMapping(value = "/u/checkchatseen", method = RequestMethod.GET)
	public String checkSeen(@RequestParam("username") String username,
			Principal principal) {
		User user = this.userService.getUserByEmail(principal.getName());
		User profileUser = this.userService.getUserByUserName(username);
		return this.chatService.checkSeenOrNot(user, profileUser);
	}
	
}
