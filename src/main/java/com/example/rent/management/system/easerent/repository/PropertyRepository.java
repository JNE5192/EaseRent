package com.example.rent.management.system.easerent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rent.management.system.easerent.entity.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property,String>{
	List<Property> findByOwnerId(String ownerId);
	Property findByPropertyId(String propertyId);
}
