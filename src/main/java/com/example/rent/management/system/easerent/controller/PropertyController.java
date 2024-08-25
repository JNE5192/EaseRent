package com.example.rent.management.system.easerent.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rent.management.system.easerent.entity.Property;
import com.example.rent.management.system.easerent.repository.PropertyRepository;
import com.example.rent.management.system.easerent.service.PropertyService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/property")
public class PropertyController {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);
	
	//@Autowired
	//PropertyService propertyService;
	
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

}
