package com.example.rent.management.system.easerent.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.example.rent.management.system.easerent.entity.OwnerAuthentication;
import com.example.rent.management.system.easerent.repository.OwnerAuthRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
    private OwnerAuthRepository ownerAuthRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Fetch the user from the database
		OwnerAuthentication owner = ownerAuthRepository.findByOwnerId(username).get();
                
        // Return a UserDetails object with the user's information
        return org.springframework.security.core.userdetails.User.withUsername(owner.getOwnerId())
                .password(owner.getPassword())
                .authorities("ROLE_USER") // Replace with actual roles
                .build();
	}

}
