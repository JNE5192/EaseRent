package com.example.rent.management.system.easerent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rent.management.system.easerent.dto.Response;
import com.example.rent.management.system.easerent.entity.OwnerAuthentication;
import com.example.rent.management.system.easerent.service.OwnerAuthService;

@RestController
@RequestMapping("/api/auth")
public class OwnerAuthController {
	
	@Autowired
	OwnerAuthService ownerAuthService;
	
	@PostMapping("/signup")
	public Response register(@RequestBody OwnerAuthentication ownerAuthentication) {
		Response response = new Response();
		try {
			OwnerAuthentication registeredOwner = ownerAuthService.registerOwner(ownerAuthentication);
			
			if(registeredOwner != null) {
				response.setStatus("true");
				response.setMessage("Owner " + ownerAuthentication.getEmail() + " has been sucessfully registered with owner id  " + registeredOwner.getOwnerId());
			}
			else {
				response.setStatus("false");
				response.setMessage("Failed to register " + ownerAuthentication.getEmail());
			}
		} catch(RuntimeException e) {
			e.getMessage();
		}
		return response;
	}
	
	@PostMapping("/login")
	public Response login(@RequestBody OwnerAuthentication ownerAuthentication) {
		Response response = new Response();
		try {
			
			String loginStatus = ownerAuthService.loginOwner(ownerAuthentication);
			
			if(loginStatus.equalsIgnoreCase("Login successful")) {
				response.setStatus("true");
				response.setMessage(loginStatus);
			}
			
			else {
				response.setStatus("false");
				response.setMessage(loginStatus);
			}
			
		} catch(RuntimeException e) {
			e.getMessage();
		}
		
										
		return response;
	}

}
