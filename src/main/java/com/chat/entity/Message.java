package com.chat.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	private String text;
	
	private Date date;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name = "usersend", referencedColumnName = "id")
	private User userSend;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name = "userreceived", referencedColumnName = "id")
	private User userReceived;
	
	private Boolean seen;
	
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

	public User getUserSend() {
		return userSend;
	}

	public void setUserSend(User user) {
		this.userSend = user;
	}

	public User getUserReceived() {
		return userReceived;
	}

	public void setUserReceived(User userReceived) {
		this.userReceived = userReceived;
	}

	public Boolean getSeen() {
		return seen;
	}

	public void setSeen(Boolean seen) {
		this.seen = seen;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", text=" + text + ", date=" + date + ", userSend=" + userSend + ", userReceived="
				+ userReceived + ", seen=" + seen + "]";
	}
	
}
