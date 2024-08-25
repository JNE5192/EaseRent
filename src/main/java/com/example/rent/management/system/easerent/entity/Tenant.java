package com.example.rent.management.system.easerent.entity;

import com.example.rent.management.system.easerent.dto.Address;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Tenant {
	
	@Column(nullable = false)
	private String ownerId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tenantId;
	
	@Column(nullable = false)
	private String tenantName;
	
	private int age;
	
	private int noOfFamilyMembers;
	
	@Embedded
	private Address permanentAddress;
	
	@Column(nullable = false)
	private Long mobileNumber;
	
	
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public long getTenantId() {
		return tenantId;
	}

	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getNoOfFamilyMembers() {
		return noOfFamilyMembers;
	}

	public void setNoOfFamilyMembers(int noOfFamilyMembers) {
		this.noOfFamilyMembers = noOfFamilyMembers;
	}

	public Address getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(Address permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	
	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public String toString() {
		return "Tenant [ownerId=" + ownerId + ", tenantId=" + tenantId + ", tenantName=" + tenantName + ", age=" + age
				+ ", noOfFamilyMembers=" + noOfFamilyMembers + ", permanentAddress=" + permanentAddress
				+ ", mobileNumber=" + mobileNumber + "]";
	}

}
