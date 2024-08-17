package com.example.rent.management.system.easerent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.rent.management.system.easerent.repository.OwnerAuthRepository;

@Service
public class OwnerAuthService {
	
	@Autowired
	OwnerAuthRepository ownerAuthRepository;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

}
