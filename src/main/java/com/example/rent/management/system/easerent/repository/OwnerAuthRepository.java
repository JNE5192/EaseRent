package com.example.rent.management.system.easerent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rent.management.system.easerent.entity.OwnerAuthentication;

public interface OwnerAuthRepository extends JpaRepository<OwnerAuthentication,String> {

}
