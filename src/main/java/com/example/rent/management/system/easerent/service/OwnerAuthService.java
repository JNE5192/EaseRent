package com.example.rent.management.system.easerent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.rent.management.system.easerent.entity.OwnerAuthentication;
import com.example.rent.management.system.easerent.repository.OwnerAuthRepository;

@Service
public class OwnerAuthService {
	
	@Autowired
	OwnerAuthRepository ownerAuthRepository;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	public OwnerAuthentication registerOwner(OwnerAuthentication ownerAuthentication) {
		if(ownerAuthRepository.existsByEmail(ownerAuthentication.getEmail()))
			throw new RuntimeException("Email id : " + ownerAuthentication.getEmail() + " is already registered");
		
		//Encode password before saving it to table
		ownerAuthentication.setPassword(passwordEncoder.encode(ownerAuthentication.getPassword()));
			
		return ownerAuthRepository.save(ownerAuthentication);
	}

}
