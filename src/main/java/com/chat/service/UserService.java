package com.chat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chat.entity.ActivateAccount;
import com.chat.entity.Forgot;
import com.chat.entity.User;
import com.chat.helper.SearchUser;
import com.chat.repository.ActivateAccountRepository;
import com.chat.repository.ForgotRepository;
import com.chat.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ActivateAccountRepository activateAccountRepository;
	
	@Autowired
	private ForgotRepository forgotRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User getUserByEmail(String email) {
		return this.userRepository.getUserByEmail(email);
	}
	public User getUserByUserName(String username) {
		return this.userRepository.getUserByUserName(username);
	}
	
	public String userSendRegistrationOtp(String email) {
		if(!this.userRepository.existsByEmail(email)){
			this.activateAccountRepository.findAllByEmail(email).forEach(active -> {
				this.activateAccountRepository.delete(active);
			});
			String otp = this.getRandom();
			String body = "Hello,\n\n"
                    + "Your verification code is : "+otp+"\n"
                    + "If you are having any issue with your account, please don't hesitate to contact us.\n\n"
                    + "Thanks!\n"
                    + "MessageS India";
			this.mailService.send(email, "One time password | Registration", body);
			ActivateAccount activateAccount = new ActivateAccount();
			activateAccount.setEmail(email);
			activateAccount.setOtp(otp);
			this.activateAccountRepository.save(activateAccount);
			return "done";
		} else {
			return "userAlready";
		}
	}

	public String userRegistration(User user, String otp) {
		if(!this.userRepository.existsByEmail(user.getEmail().trim())){
			if(this.activateAccountRepository.existsByEmailAndOtp(user.getEmail().trim(), otp)) {
				this.activateAccountRepository.findAllByEmail(user.getEmail().trim()).forEach(active -> {
					this.activateAccountRepository.delete(active);
				});
				user.setActive(true);
				user.setJoiningDate(new Date());
				user.setProfile("default.jpg");
				user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
				user.setRole("ROLE_USER");
				this.userRepository.save(user);
				user.setUserName(user.getFirstName().trim()+user.getId());
				this.userRepository.save(user);
				return "done";
			} else {
				return "notMatched";
			}
		} else {
			return "userAlready";
		}
	}

	public String sendOtpForForgot(String email) {
		if(this.userRepository.existsByEmail(email)){
			this.forgotRepository.findAllByEmail(email).forEach(active -> {
				this.forgotRepository.delete(active);
			});
			String otp = this.getRandom();
			String body = "Hello,\n\n"
                    + "Your verification code is : "+otp+"\n"
                    + "If you are having any issue with your account, please don't hesitate to contact us.\n\n"
                    + "Thanks!\n"
                    + "MessageS India";
			this.mailService.send(email, "One time password | Forgot Password", body);
			Forgot forgot = new Forgot();
			forgot.setEmail(email);
			forgot.setOtp(otp);
			this.forgotRepository.save(forgot);
			return "done";
		} else {
			return "noUser";
		}
	}

	public String forgotPassword(String email, String otp, String password) {
		if(this.userRepository.existsByEmail(email)){
			if(this.forgotRepository.existsByEmailAndOtp(email, otp)) {
				this.forgotRepository.findAllByEmail(email).forEach(active -> {
					this.forgotRepository.delete(active);
				});
				User user = this.userRepository.getUserByEmail(email);
				user.setPassword(this.bCryptPasswordEncoder.encode(password));
				this.userRepository.save(user);
				return "done";	
			} else {
				return "notMatched";
			}
		} else {
			return "noUser";
		}
	}

	private String getRandom() {
		return String.valueOf((int) (Math.random()*(999999-111111+1)+111111));
	}
	public List<SearchUser> searchUserByName(String s) {
		List<SearchUser> list = new ArrayList<SearchUser>();
		Streamable<User> temp = this.userRepository.findTop10ByUserNameContaining(s);
		temp.forEach(t->{
			SearchUser su = new SearchUser();
			su.setFirstName(t.getFirstName());
			su.setLastName(t.getLastName());
			su.setUserName(t.getUserName());
			list.add(su);
		});
		return list;
	}
	
}
