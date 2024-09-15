package com.example.rent.management.system.easerent.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PropertyReceipt {

	@Id
	private String receiptNo;
	
	@Column(nullable = false)
	private String propertyId;
	
	@Column(nullable = false)
	private double area;
	
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date date;
	
	@Column(nullable = false, columnDefinition = "double default 0")
	private double amountReceived;
	
	@Column(nullable = false, columnDefinition = "int default 0")
	private int electricityUnits;
	
	@Column(nullable = false)
	private double electricityRate;
	
	@Column(nullable = false, columnDefinition = "int default 0")
	private int waterUnits;
	
	@Column(nullable = false)
	private double waterRate;
	
	
	private double electricityBill;
	
	private double waterBill;
	
	private double rent;
	
	@Column(columnDefinition = "double default 0")
	private double totalAmtReceived;
	
	@Column(columnDefinition = "double default 0")
	private double currentExpectedAmount;
	
	private double totalExpectedAmount;
	
	private double balance;

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getAmountReceived() {
		return amountReceived;
	}

	public void setAmountReceived(double amountReceived) {
		this.amountReceived = amountReceived;
	}

	public int getElectricityUnits() {
		return electricityUnits;
	}

	public void setElectricityUnits(int electricityUnits) {
		this.electricityUnits = electricityUnits;
	}

	public double getElectricityRate() {
		return electricityRate;
	}

	public void setElectricityRate(double electricityRate) {
		this.electricityRate = electricityRate;
	}

	public int getWaterUnits() {
		return waterUnits;
	}

	public void setWaterUnits(int waterUnits) {
		this.waterUnits = waterUnits;
	}

	public double getWaterRate() {
		return waterRate;
	}

	public void setWaterRate(double waterRate) {
		this.waterRate = waterRate;
	}


	public double getRent() {
		return rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public double getTotalAmtReceived() {
		return totalAmtReceived;
	}

	public void setTotalAmtReceived(double totalAmtReceived) {
		this.totalAmtReceived = totalAmtReceived;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getTotalExpectedAmount() {
		return totalExpectedAmount;
	}

	public void setTotalExpectedAmount(double totalExpectedAmount) {
		this.totalExpectedAmount = totalExpectedAmount;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public double getElectricityBill() {
		return electricityBill;
	}

	public void setElectricityBill(double electricityBill) {
		this.electricityBill = electricityBill;
	}

	public double getWaterBill() {
		return waterBill;
	}

	public void setWaterBill(double waterBill) {
		this.waterBill = waterBill;
	}
	

	public double getCurrentExpectedAmount() {
		return currentExpectedAmount;
	}

	public void setCurrentExpectedAmount(double currentExpectedAmount) {
		this.currentExpectedAmount = currentExpectedAmount;
	}

	@Override
	public String toString() {
		return "PropertyReceipt [receiptNo=" + receiptNo + ", propertyId=" + propertyId + ", area=" + area + ", date="
				+ date + ", amountReceived=" + amountReceived + ", electricityUnits=" + electricityUnits
				+ ", electricityRate=" + electricityRate + ", waterUnits=" + waterUnits + ", waterRate=" + waterRate
				+ ", electricityBill=" + electricityBill + ", waterBill=" + waterBill + ", rent=" + rent
				+ ", totalAmtReceived=" + totalAmtReceived + ", currentExpectedAmount=" + currentExpectedAmount
				+ ", totalExpectedAmount=" + totalExpectedAmount + ", balance=" + balance + "]";
	}

}
