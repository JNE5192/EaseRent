package com.example.rent.management.system.easerent.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.rent.management.system.easerent.dto.Address;
import com.example.rent.management.system.easerent.entity.Property;
import com.example.rent.management.system.easerent.repository.PropertyRepository;

@Service
public class PropertyService {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);
	
	@Autowired 
	PropertyRepository propertyRepository;
	
	public Property updateProperty(Property updatedProperty, String propertyId) throws RuntimeException {

		return propertyRepository.findById(propertyId)
				.map( existingProperty -> {
					existingProperty.setPropertyType(updatedProperty.getPropertyType());
					existingProperty.setNoOfRooms(updatedProperty.getNoOfRooms());
					existingProperty.setArea(updatedProperty.getArea());
					existingProperty.setAddress(updatedProperty.getAddress());
					logger.debug("Updated tenant details are : " + existingProperty.toString());

					return propertyRepository.save(existingProperty);
				})
				.orElseThrow(()-> new RuntimeException("Property with property id : " + propertyId + " cannot be found"));
	}

	
	
	public Property saveProperty(String ownerId, String propertyType, int noOfRooms, double area, Address address, String imageName, String contentType, MultipartFile photo) throws IOException {
        Property property = new Property();
        property.setOwnerId(ownerId);
        property.setPropertyType(propertyType);
        property.setNoOfRooms(noOfRooms);
        property.setArea(area);
        property.setAddress(address);
        property.setImageName(imageName);
        property.setContentType(contentType);
    
        if (photo != null && !photo.isEmpty()) {
            property.setPhoto(photo.getBytes()); // Convert image to byte array
        }

        return propertyRepository.save(property);
    }
	
	
	public Property getPropertyById(String propertyId) {
        return propertyRepository.findByPropertyId(propertyId);
    }
	
	
	public void deleteProperty(String propertyId) throws RuntimeException {

		if(propertyRepository.findByPropertyId(propertyId) !=null) {
			logger.debug("Deleting property with id : " + propertyId);
			propertyRepository.deleteById(propertyId);	
			//force push changes to table, might not work correctly every time
			propertyRepository.flush();
		}
		else {
			throw new RuntimeException("Property with property id : " + propertyId + " cannot be found");
		}
	}
}
