package com.example.rent.management.system.easerent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rent.management.system.easerent.entity.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
	List<Tenant> findByOwnerId(String ownerId);
}
