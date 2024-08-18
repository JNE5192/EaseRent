package com.example.rent.management.system.easerent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.rent.management.system.easerent.entity.OwnerAuthentication;
import com.example.rent.management.system.easerent.repository.OwnerAuthRepository;

@Service
public class OwnerAuthService {
	
	private static final Logger logger = LoggerFactory.getLogger(OwnerAuthService.class);
	
	@Autowired
	OwnerAuthRepository ownerAuthRepository;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	
	/**
	 * @author Juhilee Nazare
	 * @param ownerAuthentication
	 * @return OwnerAuthentication registered user
	 */
	public OwnerAuthentication registerOwner(OwnerAuthentication ownerAuthentication) {
		if(ownerAuthRepository.existsByEmail(ownerAuthentication.getEmail()))
			throw new RuntimeException("Email id : " + ownerAuthentication.getEmail() + " is already registered");
		
		//Encode password before saving it to table
		ownerAuthentication.setPassword(passwordEncoder.encode(ownerAuthentication.getPassword()));
			
		return ownerAuthRepository.save(ownerAuthentication);
	}
	
	
	/**
	 * @author Juhilee Nazare
	 * @param loginUser
	 * @return String login status
	 * @throws RuntimeException
	 * @throws AuthenticationException
	 */
	public String loginOwner(OwnerAuthentication loginUser) throws RuntimeException, AuthenticationException {
		try {
			OwnerAuthentication existingUser = (ownerAuthRepository.findByOwnerId(loginUser.getOwnerId())).get();

			if(existingUser == null) {
				throw new RuntimeException("Owner not found");
			}

			if(passwordEncoder.matches(loginUser.getPassword(), existingUser.getPassword())) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginUser.getOwnerId(), loginUser.getPassword())
						);

				if (authentication.isAuthenticated()) {
					return "Login successful";
				}
			}
		} catch (AuthenticationException authenticationException) {
			authenticationException.getMessage();
		}

		return "Login Failed";
	}

}
