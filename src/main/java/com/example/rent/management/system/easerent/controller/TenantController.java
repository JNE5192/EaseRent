package com.example.rent.management.system.easerent.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rent.management.system.easerent.dto.Response;
import com.example.rent.management.system.easerent.entity.Tenant;
import com.example.rent.management.system.easerent.repository.TenantRepository;
import com.example.rent.management.system.easerent.service.TenantService;

@RestController
@RequestMapping("/tenant")
public class TenantController {
	
	private static final Logger logger = LoggerFactory.getLogger(TenantController.class);
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private TenantRepository tenantRepository;
	
	/**
	 * Inserts a tenant in table "tenant" of database "rent_ease"
	 * 
	 * @author Juhilee Nazare
	 * 
	 * <p> The full service url for this endpoint is : </p>
	 * <p> <code>http://localhost:8080/tenant/<code> </p>
	 * 
	 * @param tenant
	 * @throws IllegalArgumentException
	 * @return tenantId
	 */
	@PostMapping("/")
	public Long addTenant(@RequestBody Tenant tenant) throws IllegalArgumentException {
		
		if (tenant.getTenantName() == null || tenant.getPermanentAddress() == null) 
			throw new IllegalArgumentException("Tenant name and address must not be null."); 
		
		logger.debug("Inserting tenant: " + tenant.toString());

		return (tenantRepository.save(tenant)).getTenantId();
	}

	
	/**
	 * Updates a tenant in table "tenant" of database "rent_ease" using tenant id
	 * 
	 * @author Juhilee Nazare
	 * 
	 * <p> The full service url for this endpoint is : </p>
	 * <p> <code>http://localhost:8080/tenant/{tenantId}</code> </p>
	 * <p> where <code>{tenantId}</code> is the id of tenant whose details are to be updated </p>
	 * 
	 * @param tenant
	 * @param tenantId
	 * @return response
	 * @throws NullPointerException
	 */
	@PutMapping("/{tenantId}")
	public Response updateTenant(@RequestBody Tenant tenant, @PathVariable Long tenantId) throws NullPointerException {
		
		Response response = new Response();
		
		if(tenantId == null || tenantId == 0)
			throw new NullPointerException("Tenant Id must not be null or empty.");
		
		Tenant updatedTenant = tenantService.updateTenant(tenant, tenantId);
		logger.debug("Updated values for tenant are : " + updatedTenant.toString());
		
		if(updatedTenant != null && updatedTenant.getTenantId() != 0) {
			response.setStatus("true");
			response.setMessage("Successfully updated tenant with id = " + tenantId);
		}
		else {
			response.setStatus("false");
			response.setMessage("Failed to update tenant with id = " + tenantId);
		}

		return response;
	}
	
	
	/**
	 * Retrieves a tenant by id from table "tenant" of database "rent_ease" using tenant id
	 * 
	 * @author Juhilee Nazare
	 * 
	 * <p> The full service url for this endpoint is : </p>
	 * <p> <code>http://localhost:8080/tenant/{tenantId}</code> </p>
	 * <p> where <code>{tenantId}</code> is the id of tenant whose details are to be retrieved </p>
	 * 
	 *
	 * @param tenantId
	 * @return tenant
	 * @throws NullPointerException
	 */
	@GetMapping("/{tenantId}")
	public Tenant getTenant(@PathVariable Long tenantId) throws NullPointerException {
		
		if(tenantId == null || tenantId == 0) 
			throw new NullPointerException("Tenant Id must not be null or empty.");
		
		return tenantRepository.findById(tenantId).get();		
	}
	
	
	/**
	 * Deletes a tenant by id from table "tenant" of database "rent_ease" using tenant id
	 * 
	 * @author Juhilee Nazare
	 * 
	 * <p> The full service url for this endpoint is : </p>
	 * <p> <code>http://localhost:8080/tenant/{tenantId}</code> </p>
	 * <p> where <code>{tenantId}</code> is the id of tenant whose details are to be deleted </p>
	 * 
	 * @param tenantId
	 * @return response
	 * @throws NullPointerException
	 */
	
	@DeleteMapping("/{tenantId}")
	public Response deleteTenant(@PathVariable Long tenantId) throws NullPointerException {
		Response response = new Response();
		
		if(tenantId == null || tenantId == 0) 
			throw new NullPointerException("Tenant Id must not be null or empty.");
		
		tenantService.deleteTenant(tenantId);
			
		if(tenantRepository.existsById(tenantId)) {
			response.setStatus("false");
			response.setMessage("Failed to delete tenant with id = " + tenantId);
		}
		else {
			response.setStatus("true");
			response.setMessage("Successfully deleted tenant with id = " + tenantId);
		}
		
		return response;	
	}
	
}
