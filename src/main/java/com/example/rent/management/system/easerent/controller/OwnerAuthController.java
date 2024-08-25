package com.example.rent.management.system.easerent.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rent.management.system.easerent.dto.Response;
import com.example.rent.management.system.easerent.entity.OwnerAuthentication;
import com.example.rent.management.system.easerent.service.OwnerAuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class OwnerAuthController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(OwnerAuthController.class);
	@Autowired
	OwnerAuthService ownerAuthService;
	
	
	/**
	 * Register user in table owner_authentication
	 * 
	 * <p> The full service url for this endpoint is : </p>
	 * <p> <code>http://localhost:8080/api/auth/signup<code> </p>
	 * 
	 * @author Juhilee Nazare
	 * @param ownerAuthentication
	 * @return
	 */
	@CrossOrigin(origins = "http://localhost:3000")
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
			response.setStatus("false");
			response.setMessage("Owner with email id " + ownerAuthentication.getEmail() + " already exists");
		}
		return response;
	}
	
	
	/**
	 * Login user by applying checks on username and password
	 * 
	 * <p> The full service url for this endpoint is : </p>
	 * <p> <code>http://localhost:8080/api/auth/login<code> </p>
	 * 
	 * @author Juhilee Nazare
	 * @param ownerAuthentication
	 * @return
	 */
	@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
	@PostMapping("/login")
	public Response login(HttpServletRequest request,HttpSession session, @RequestBody OwnerAuthentication ownerAuthentication) {
		Response response = new Response();
		try {
			
			//logger.info("**************************SESSION ID 1 : " + session.getId());
			
			//session.setAttribute("ownerId", ownerAuthentication.getOwnerId());
			//HttpSession session = request.getSession(true); // true means create a new session if none exists
	        //session.setAttribute("someAttribute", "someValue");
		    //session.setAttribute("ownerId", ownerAuthentication.getOwnerId());
			
			HttpSession existingSession = request.getSession(false);  // Use existing session
		    if (existingSession == null) {
		        existingSession = request.getSession(true);  // Create a new session if none exists
		    }
		    existingSession.setAttribute("ownerId", ownerAuthentication.getOwnerId());
		    logger.info("**************************SESSION ID 1 : " + existingSession.getId());
			
			String loginStatus = ownerAuthService.loginOwner(ownerAuthentication);

			if(loginStatus.equalsIgnoreCase("Login successful")) {
				response.setStatus("true");
				response.setMessage(loginStatus);
				/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
		            System.out.println( "****************************************Authenticated as: " + ((UserDetails)
		            		  authentication.getPrincipal()).getUsername());
		        	System.out.println("****************YES*************************");
		        }
		        ownerAuthService.getCurrentUser();		*/	}

			else {
				response.setStatus("false");
				response.setMessage(loginStatus);
			}

		} catch(RuntimeException e) {
			e.getMessage();
			response.setStatus("false");
			response.setMessage("Owner not found");
		}
		
		return response;
	}

}
