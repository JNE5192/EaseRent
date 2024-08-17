package com.example.rent.management.system.easerent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rent.management.system.easerent.entity.OwnerAuthentication;

@Repository
public interface OwnerAuthRepository extends JpaRepository<OwnerAuthentication,String> {
	
	

}
