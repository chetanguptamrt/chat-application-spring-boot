package com.chat.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	private String userName;
	
	private String email;
	
	private String profile;
	
	private String password;

	@Column(name = "joining_date")
	private Date joiningDate;
	
	private boolean active;
	
	private String role;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userSend", fetch = FetchType.LAZY)
	private List<Message> message;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userReceived", fetch = FetchType.LAZY)
	private List<Message> messageReceived;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}
	
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Message> getMessage() {
		return message;
	}

	public void setMessageSend(List<Message> messageSend) {
		this.message = messageSend;
	}

	public List<Message> getMessageReceived() {
		return messageReceived;
	}

	public void setMessageReceived(List<Message> messageReceived) {
		this.messageReceived = messageReceived;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", email=" + email + ", profile=" + profile + ", password=" + password + ", joiningDate="
				+ joiningDate + ", active=" + active + ", role=" + role + "]";
	}
	
}
