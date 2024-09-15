package com.example.rent.management.system.easerent.entity;

import com.example.rent.management.system.easerent.dto.Address;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;

@Entity
public class Property {
	
	@Column(nullable = false)
	private String ownerId;
	
	@Id
	private String propertyId;
	
	@Column(nullable = false)
	private String propertyType;
	
	@Column(nullable = false)
	private int noOfRooms;
	
	@Column(nullable = false)
	private double area;
	
	@Embedded
	@Column(nullable = false)
	private Address address;
	
	private String imageName;
	
    private String contentType;

    @Column(columnDefinition = "LONGBLOB")
    @Lob
    private byte[] photo;
    
    @Column(columnDefinition = "boolean default false")
    private boolean isAllocated = false;

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

	public int getNoOfRooms() {
		return noOfRooms;
	}

	public void setNoOfRooms(int noOfRooms) {
		this.noOfRooms = noOfRooms;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public boolean getIsAllocated() {
		return isAllocated;
	}

	public void setBase64image(boolean isAllocated) {
		this.isAllocated = isAllocated;
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
