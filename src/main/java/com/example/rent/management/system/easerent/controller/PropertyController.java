package com.example.rent.management.system.easerent.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rent.management.system.easerent.dto.Response;
import com.example.rent.management.system.easerent.entity.Property;
import com.example.rent.management.system.easerent.repository.PropertyRepository;
import com.example.rent.management.system.easerent.service.PropertyService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/property")
public class PropertyController {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);
	
	@Autowired
	PropertyService propertyService;
	
	@Autowired
	PropertyRepository propertyRepository;
	
	/**
	 * Inserts a property in table "property" of database "ease_rent"
	 * 
	 * @author Juhilee Nazare
	 * 
	 * <p> The full service url for this endpoint is : </p>
	 * <p> <code>http://localhost:8080/property/<code> </p>
	 * 
	 * @param tenant
	 * @throws IllegalArgumentException
	 * @return tenantId
	 */
	@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
	@PostMapping("/")
	public String addProperty(HttpSession session, @RequestBody Property property) throws IllegalArgumentException, RuntimeException {

		try {
			if (property.getAddress() == null || property.getPropertyType() == null) 
				throw new IllegalArgumentException("Property type or address must not be null."); 

			property.setOwnerId((String)session.getAttribute("ownerId"));
			if(property.getOwnerId() == null || property.getOwnerId() == "")
				throw new RuntimeException("Owner id cannot be empty or null");
		}
		catch(IllegalArgumentException e) {
			e.getMessage();
		}
		catch(RuntimeException e) {
			e.getMessage();
		}

		logger.info("Inserting tenant: " + property.toString());
		return (propertyRepository.save(property)).getPropertyId();
	}
	
	
	/**
	 * Updates a property in table "property" of database "ease_rent" using property id
	 * 
	 * @author Juhilee Nazare
	 * 
	 * <p> The full service url for this endpoint is : </p>
	 * <p> <code>http://localhost:8080/property/{propertyId}</code> </p>
	 * <p> where <code>{tenantId}</code> is the id of tenant whose details are to be updated </p>
	 * 
	 * @param tenant
	 * @param tenantId
	 * @return response
	 * @throws NullPointerException
	 */
	@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
	@PutMapping("/{propertyId}")
	public Response updateProperty(@RequestBody Property property, @PathVariable String propertyId) throws NullPointerException {
		
		Response response = new Response();
		
		if(propertyId == null || propertyId.isEmpty())
			throw new NullPointerException("Property Id must not be null or empty.");
		
		Property updatedProperty = propertyService.updateProperty(property, propertyId);
		logger.debug("Updated values for propertyId are : " + updatedProperty.toString());
		
		if(updatedProperty != null && !updatedProperty.getPropertyId().isEmpty()) {
			response.setStatus("true");
			response.setMessage("Successfully updated property with id = " + propertyId);
		}
		else {
			response.setStatus("false");
			response.setMessage("Failed to update property with id = " + propertyId);
		}

		return response;
	}
	

}
