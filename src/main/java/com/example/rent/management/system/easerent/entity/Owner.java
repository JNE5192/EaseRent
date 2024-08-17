package com.example.rent.management.system.easerent.entity;

import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Owner {
	
	@Id
	private String ownerId;
	
	@Column(nullable = false)
	private String ownerName;
	
	private String password;
	
	private String email;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@PrePersist
	protected void onCreate() {
		this.ownerId = generateCustomId(this.ownerName);
	}
	
	public String generateCustomId(String ownerName) {
		// Generates a 5-digit random number
		int randomNumber = new Random().nextInt(90000) + 10000;
		String name = ownerName.replaceAll("\\s+", "").toUpperCase();
		String namePart = name.substring(0,3);
		return namePart + "-" + randomNumber;
	}

}

