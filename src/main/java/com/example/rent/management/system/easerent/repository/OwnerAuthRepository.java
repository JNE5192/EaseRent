package com.example.rent.management.system.easerent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rent.management.system.easerent.entity.OwnerAuthentication;

@Repository
public interface OwnerAuthRepository extends JpaRepository<OwnerAuthentication,String> {	
	boolean existsByEmail(String email);
	Optional<OwnerAuthentication> findByEmail(String email);
	Optional<OwnerAuthentication> findByOwnerId(String ownerId);
}
