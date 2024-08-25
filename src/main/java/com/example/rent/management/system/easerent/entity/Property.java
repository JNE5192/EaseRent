package com.example.rent.management.system.easerent.entity;

import com.example.rent.management.system.easerent.dto.Address;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Property {
	
	@Column(nullable = false)
	private String ownerId;
	
	@Id
	private String propertyId;
	
	@Column(nullable = false)
	private String propertyType;
	
	@Embedded
	@Column(nullable = false)
	private Address address;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	
	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Property [ownerId=" + ownerId + ", propertyId=" + propertyId + ", address=" + address + "]";
	}
	
	@PrePersist
	protected void onCreate() {
		this.propertyId = generateCustomPropertyId(this.ownerId, this.propertyType, this.address);
	}
	
	public String generateCustomPropertyId(String ownerId, String propertyType, Address address) {
		if (propertyType.equalsIgnoreCase("Flat")) 
			return ownerId + "-" + "F" +  address.getHouseNo();
		else 
			return ownerId + "-" + "B" +  address.getHouseNo();
	}
	
}
