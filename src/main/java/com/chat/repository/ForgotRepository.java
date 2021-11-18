package com.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.entity.Forgot;

public interface ForgotRepository extends JpaRepository<Forgot, Integer> {

	public boolean existsByEmailAndOtp(String email, String otp);
	
	public List<Forgot> findAllByEmail(String email);
	
}
