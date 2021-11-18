package com.chat.helper;

import java.util.Date;

public class ChatHelper {
 
	private int id;
	
	private String text;
	
	private Date date;
	
	private int userId;

	public ChatHelper(int id, String text, Date date, int userId) {
		super();
		this.id = id;
		this.text = text;
		this.date = date;
		this.userId = userId;
	}
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
