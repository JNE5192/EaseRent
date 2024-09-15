package com.example.rent.management.system.easerent.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.rent.management.system.easerent.entity.PropertyReceipt;

@Repository
public interface PropertyReceiptRepository extends JpaRepository<PropertyReceipt, String>{
	
	@Query("SELECT e FROM PropertyReceipt e WHERE e.propertyId = :propertyId AND e.date < :currentDate ORDER BY e.date DESC")
    List<PropertyReceipt> findPreviousRecords(@Param("propertyId") String propertyId, @Param("currentDate") Date currentDate);
	
	@Query("SELECT COALESCE(SUM(e.amountReceived), 0) FROM PropertyReceipt e WHERE e.propertyId = :propertyId AND e.date < :currentDate")
    double sumPreviousAmountReceived(@Param("propertyId") String propertyId, @Param("currentDate") Date currentDate);
	
	@Query("SELECT COALESCE(SUM(e.currentExpectedAmount), 0) FROM PropertyReceipt e WHERE e.propertyId = :propertyId AND e.date < :currentDate")
    double sumPreviousExpectedAmount(@Param("propertyId") String propertyId, @Param("currentDate") Date currentDate);

}
