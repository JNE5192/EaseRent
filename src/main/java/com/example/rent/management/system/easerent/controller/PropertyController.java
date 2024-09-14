package com.example.rent.management.system.easerent.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.rent.management.system.easerent.dto.Address;
import com.example.rent.management.system.easerent.dto.Response;
import com.example.rent.management.system.easerent.entity.Property;
import com.example.rent.management.system.easerent.entity.Tenant;
import com.example.rent.management.system.easerent.repository.PropertyRepository;
import com.example.rent.management.system.easerent.service.ImageService;
import com.example.rent.management.system.easerent.service.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/property")
public class PropertyController {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);
	
	@Autowired
	PropertyService propertyService;
	
	@Autowired
    private ImageService imageService;
	
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
	@PostMapping("/addProperty")
	public String addProperty(HttpSession session, @RequestBody Property property) throws IllegalArgumentException, RuntimeException, IOException {

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

		logger.info("Inserting property: " + property.toString());
		
		return (propertyRepository.save(property)).getPropertyId();
	}
	
	
	@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
	@PostMapping("/")
    public Property createProperty(
    		HttpSession session,
            @RequestParam("propertyType") String propertyType,
            @RequestParam("noOfRooms") int noOfRooms,
            @RequestParam("area") double area,
            @RequestParam("address") String addressJson,
            @RequestParam("imageName") String imageName,
            @RequestParam("contentType") String contentType,
            @RequestParam("photo") MultipartFile photo) throws IOException {
		
		String ownerId = null;
		
		try {
			if (addressJson == null || propertyType == null) 
				throw new IllegalArgumentException("Property type or address must not be null."); 

			ownerId = ((String)session.getAttribute("ownerId"));
			
		}
		catch(IllegalArgumentException e) {
			e.getMessage();
		}

		ObjectMapper objectMapper = new ObjectMapper();
		Address address = objectMapper.readValue(addressJson, Address.class);
        return propertyService.saveProperty(ownerId, propertyType, noOfRooms, area, address, imageName, contentType, photo);
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
	
	
	@PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return imageService.saveImage(file);
    }
	
	
	
	@GetMapping("/{id}")
    public ResponseEntity<String> getPropertyById(@PathVariable String id) {
		Property property = propertyService.getPropertyById(id);

        if (property!= null && property.getPhoto() !=null) {
        	String base64Image = Base64.getEncoder().encodeToString(property.getPhoto());
        	return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, property.getContentType())
                    .body(base64Image);
            
        } else {
            return ResponseEntity.notFound().build();  // Return 404 Not Found if no property exists with the given ID
        }
    }
	
	
	@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
	@GetMapping("/list")
	public List<Property> listTenant(HttpSession session)  {
		String ownerId = (String) session.getAttribute("ownerId");
		if(ownerId == null || ownerId.isEmpty())
			throw new RuntimeException("Owner Id cannot be null or empty");
		/*List<Property> properties =  propertyRepository.findByOwnerId(ownerId);
		for(Property property : properties) {
			if(property!= null && property.getPhoto() != null) {
			String base64Image = Base64.getEncoder().encodeToString(property.getPhoto());
			property.setBase64image("data:" + property.getContentType() + ";base64," + base64Image);
			}
		}*/
		return propertyRepository.findByOwnerId(ownerId);
	}

	
	@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
	@DeleteMapping("/{propertyId}")
	public Response deleteProperty(@PathVariable String propertyId) throws NullPointerException {
		Response response = new Response();

		if(propertyId == null ) 
			throw new NullPointerException("Tenant Id must not be null or empty.");
		
		propertyService.deleteProperty(propertyId);
		
		if(propertyRepository.existsById(propertyId)) {
			response.setStatus("false");
			response.setMessage("Failed to delete property with id = " + propertyId);
		}
		else {
			response.setStatus("true");
			response.setMessage("Successfully deleted property with id = " + propertyId);
		}
		
		return response;	
	}
}
