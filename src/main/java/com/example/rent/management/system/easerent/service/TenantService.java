package com.example.rent.management.system.easerent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rent.management.system.easerent.entity.Tenant;
import com.example.rent.management.system.easerent.repository.TenantRepository;


@Service
public class TenantService {
	
	private static final Logger logger = LoggerFactory.getLogger(TenantService.class);

	@Autowired
	private TenantRepository tenantRepository;
	
	
	public Tenant updateTenant(Tenant updatedTenant, Long tenantId) throws RuntimeException {

		return tenantRepository.findById(tenantId)
				.map( existingTenant -> {
					existingTenant.setTenantName(updatedTenant.getTenantName());
					existingTenant.setAge(updatedTenant.getAge());
					existingTenant.setNoOfFamilyMembers(existingTenant.getNoOfFamilyMembers());
					existingTenant.setPermanentAddress(existingTenant.getPermanentAddress());

					logger.debug("Updated tenant details are : " + existingTenant.toString());

					return tenantRepository.save(existingTenant);
				})
				.orElseThrow(()-> new RuntimeException("Tenant with tenant id : " + tenantId + " cannot be found"));

	}

	
	public void deleteTenant(Long tenantId) throws RuntimeException {
		
		if(tenantRepository.findById(tenantId).isPresent()) {
			logger.debug("Deleting tenant with id : " + tenantId);
			tenantRepository.deleteById(tenantId);	
			//force push changes to table, might not work correctly every time
			tenantRepository.flush();
		}
		else {
			throw new RuntimeException("Tenant with tenant id : " + tenantId + " cannot be found");
		}
	}
	
}
