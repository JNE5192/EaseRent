package com.example.rent.management.system.easerent.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rent.management.system.easerent.dto.Response;
import com.example.rent.management.system.easerent.entity.PropertyReceipt;
import com.example.rent.management.system.easerent.repository.PropertyReceiptRepository;
import com.example.rent.management.system.easerent.service.PropertyReceiptService;

@RestController
@RequestMapping("/propertyreceipts")
public class PropertyReceiptBillsController {
	
	@Autowired
	PropertyReceiptService propertyReceiptService;
	
	@Autowired
	PropertyReceiptRepository propertyReceiptRepository;
	
	
	@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
	@PostMapping("/")
	public PropertyReceipt addReceipt(@RequestBody PropertyReceipt propertyReceipt) throws ParseException {
			if (propertyReceipt.getReceiptNo() == null) 
				throw new IllegalArgumentException("Receipt number must not be null."); 
			
		double electricityBill = propertyReceiptService.calculateElectricityBill(propertyReceipt.getPropertyId(), propertyReceipt.getElectricityUnits(), propertyReceipt.getElectricityRate(), propertyReceipt.getDate());
		double waterBill = propertyReceiptService.calculateWaterBill(propertyReceipt.getPropertyId(), propertyReceipt.getWaterUnits(), propertyReceipt.getWaterRate(), propertyReceipt.getDate());
		double rent = propertyReceiptService.calculateRent(propertyReceipt.getArea());
		double currentExpectedAmt = electricityBill + waterBill + rent;
		double totalAmtReceived = propertyReceiptService.calculateTotalAmtReceived(propertyReceipt.getPropertyId(),propertyReceipt.getAmountReceived(), propertyReceipt.getDate());
		double totalExpectedAmt = propertyReceiptService.calculateExpectedAmt(propertyReceipt.getPropertyId(), currentExpectedAmt, propertyReceipt.getDate());
		double balance = totalExpectedAmt - totalAmtReceived;
		
		propertyReceipt.setElectricityBill(electricityBill);
		propertyReceipt.setWaterBill(waterBill);
		propertyReceipt.setRent(rent);
		propertyReceipt.setTotalAmtReceived(totalAmtReceived);
		propertyReceipt.setTotalExpectedAmount(totalExpectedAmt);
		propertyReceipt.setBalance(balance);
		propertyReceipt.setCurrentExpectedAmount(currentExpectedAmt);
		
		
		return (propertyReceiptRepository.save(propertyReceipt));
	}

}
