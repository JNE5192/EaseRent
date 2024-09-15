package com.example.rent.management.system.easerent.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rent.management.system.easerent.entity.PropertyReceipt;
import com.example.rent.management.system.easerent.repository.PropertyReceiptRepository;

@Service
public class PropertyReceiptService {
	
	@Autowired
	PropertyReceiptRepository propertyReceiptRepository;
	
	public double calculateElectricityBill(String propertyId, int electricityUnits, double rate, Date date) {
		List<PropertyReceipt> previousRecords = propertyReceiptRepository.findPreviousRecords(propertyId, date);
		int previousElectricityUnits = 0;
		if (!previousRecords.isEmpty()) {
            // The first record in the list is the most recent previous record
			System.out.println("-------------------INSIDE IF -----------");
			System.out.println("-------------------Previous Records -----------" + previousRecords + "\n");
			System.out.println("-------------------Previous Records (0)-----------" + previousRecords.get(0) );
            previousElectricityUnits = previousRecords.get(0).getElectricityUnits();
            System.out.println("-------------------Previous Records (0)-----------" + previousElectricityUnits );
        }
		return (electricityUnits - previousElectricityUnits)*rate;
	}

	
	public double calculateWaterBill(String propertyId, int waterUnits, double rate, Date date) {
		List<PropertyReceipt> previousRecords = propertyReceiptRepository.findPreviousRecords(propertyId, date);
		int previousWaterUnits = 0;
		if (!previousRecords.isEmpty()) {
            // The first record in the list is the most recent previous record
			previousWaterUnits = previousRecords.get(0).getWaterUnits();
        }
		return (waterUnits - previousWaterUnits)*rate;
	}
	
	public double calculateRent(double area) {
		double rentAmt;
		if(area >= 0 && area <= 270) {
			rentAmt = 5000;
		}
		else if(area > 270 && area <= 420) {
			rentAmt = 8000;
		}
		else if(area > 420 && area <= 575) {
			rentAmt = 10000;
		}
		else if(area > 575 && area <= 700) {
			rentAmt = 13000;
		}
		else if(area > 700 && area <= 800) {
			rentAmt = 17000;
		}
		else if(area > 800 && area <= 1000) {
			rentAmt = 22000;
		}
		else if(area > 1000 && area <= 1300) {
			rentAmt = 28000;
		}
		else {
			rentAmt = 36000;
		}
		return rentAmt;
	}

	
	public double calculateTotalAmtReceived(String propertyId, double amountReceived, Date date) {
		double prevTotalAmtReceived = 0;
		prevTotalAmtReceived = propertyReceiptRepository.sumPreviousAmountReceived(propertyId, date);
		return prevTotalAmtReceived + amountReceived;	
	}
	
	public double calculateExpectedAmt(String propertyId, double currentExpectedAmt, Date date) {
		double prevTotalExpectedAmt = 0;
		prevTotalExpectedAmt = propertyReceiptRepository.sumPreviousExpectedAmount(propertyId, date);
		return prevTotalExpectedAmt + currentExpectedAmt;	
	}
}
