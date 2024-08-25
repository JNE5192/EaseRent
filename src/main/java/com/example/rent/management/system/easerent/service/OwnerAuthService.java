package com.example.rent.management.system.easerent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
					SecurityContextHolder.getContext().setAuthentication(authentication);;
					return "Login successful";
				}
			}
		} catch (AuthenticationException authenticationException) {
			authenticationException.getMessage();
		}

		return "Invalid owner id or password";
	}

	
	// Method to get the currently logged-in user
	@PreAuthorize("hasRole('ROLE_USER')")
	public String getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
			System.out.println("_______________________________________" + ((UserDetails) authentication.getPrincipal()).getUsername());
			return ((UserDetails) authentication.getPrincipal()).getUsername(); 
		} 
		return ""; 
	}

    
	/*
	 * public String getCurrentUser() throws Exception { Authentication
	 * authentication = SecurityContextHolder.getContext().getAuthentication();
	 * logger.info("**************************authentication ", authentication); if
	 * (authentication != null) { String ownerId = ((UserDetails)
	 * authentication.getPrincipal()).getUsername();
	 * logger.info("**************************Current logged in user is ", ownerId);
	 * return ownerId; } return null; }
	 */
}
