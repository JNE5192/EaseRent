package com.example.rent.management.system.easerent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
