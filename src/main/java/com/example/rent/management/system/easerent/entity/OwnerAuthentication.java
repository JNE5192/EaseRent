package com.example.rent.management.system.easerent.entity;

import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class OwnerAuthentication {
	
	@Id
	private String ownerId;
	
	@Column(nullable = false)
	@Size(min = 2, max = 30)
	private String ownerName;
	
	@NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
	private String password;
	
	@NotEmpty(message = "Email id cannot be empty")
	@Email(message = "Email id must be valid")
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

