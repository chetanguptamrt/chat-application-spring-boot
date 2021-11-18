package com.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.chat.entity.User;

import org.springframework.security.core.userdetails.UserDetailsService;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private com.chat.repository.UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// fetching user from database
		User user = this.userRepository.getUserByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException("User doesn't exists");
		}
		UserDetailsImpl customUserDetails = new UserDetailsImpl(user);
		return customUserDetails;
	}

}
