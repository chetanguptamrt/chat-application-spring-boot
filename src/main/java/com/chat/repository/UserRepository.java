package com.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

import com.chat.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User getUserByEmail(String email);

	public boolean existsByEmail(String email);
	
	public User getUserByUserName(String userName);
	
	public Streamable<User> findTop10ByUserNameContaining(String userName);
	
}
