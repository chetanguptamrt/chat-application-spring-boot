package com.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.entity.ActivateAccount;

public interface ActivateAccountRepository extends JpaRepository<ActivateAccount, Integer> {
	
	public boolean existsByEmailAndOtp(String email, String otp);
	
	public List<ActivateAccount> findAllByEmail(String email);
	
}
