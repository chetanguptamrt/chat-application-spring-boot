package com.chat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.entity.Message;
import com.chat.entity.User;
import com.chat.helper.ChatHelper;
import com.chat.repository.MessageRepository;

@Service
public class ChatService {

	@Autowired
	private MessageRepository messageRepository;

	public List<Message> inboxList(User user){
		List<Message> list = new ArrayList<Message>();
		//get all inbox users
		List<Integer> sendMessage = this.messageRepository.loadSendMessage(user.getId());
		List<Integer> receivedMessage = this.messageRepository.loadReceivedMessage(user.getId());
		Set<Integer> setList = new HashSet<Integer>();
		setList.addAll(sendMessage);
		setList.addAll(receivedMessage);
		//get all users message
		setList.forEach(u->{
			list.add(this.messageRepository.getLastMessageByUser(user.getId(), u));
		});
		int temp = 0;
		for(int i = 0; i<list.size(); i++) {
			temp = i;
			for(int j = i+1; j<list.size(); j++) {
				if(list.get(temp).getId() < list.get(j).getId()) {
					temp = j;
				}
			}
			Message tempMsg = list.get(temp);
			list.set(temp, list.get(i));
			list.set(i, tempMsg);
		}
		return list;
	}
	
	
	public String sendMessage(String text, User user, User profileUser) {
		Message message = new Message();
		message.setText(text);
		message.setDate(new Date());
		message.setSeen(false);
		message.setUserSend(user);
		message.setUserReceived(profileUser);
		this.messageRepository.save(message);
		return "done";
	}
	
	public List<Message> last20Messages(User user, User profileUser) {
		List<Message> last20Message = new ArrayList<Message>();
		this.messageRepository.seenMessage(user.getId(), profileUser.getId());
		List<Message> list = this.messageRepository.getLast20Message(user.getId(), profileUser.getId());
		for(int i = list.size()-1; i>=0; i--) {
			last20Message.add(list.get(i));
		}
		return last20Message;
	}

	public List<Message> getNewMessages(User user, User profileUser) {
		List<Message> newMessage = this.messageRepository.getNewMessage(user.getId(), profileUser.getId());
		newMessage.forEach(msg->{
			this.messageRepository.seenNewMessage(msg.getId());
		});
		return newMessage;
	}

	public String checkSeenOrNot(User user, User profileUser) {
		Message message = this.messageRepository.checkSeenUserMessage(user.getId(), profileUser.getId());
		return message.getSeen()?"seen":"no";
	}

	public List<ChatHelper> loadMoreMessages(User user, User profileUser, int firstChat) {
		List<ChatHelper> last20Message = new ArrayList<ChatHelper>();
		List<Message> list = this.messageRepository.getLoadMoreMessage(user.getId(), profileUser.getId(), firstChat);
		for(int i = list.size()-1; i>=0; i--) {
			ChatHelper chatHelper = new ChatHelper(list.get(i).getId(), list.get(i).getText(), list.get(i).getDate(), list.get(i).getUserSend().getId());
			last20Message.add(chatHelper);
		}
		return last20Message;
	}
	
}
