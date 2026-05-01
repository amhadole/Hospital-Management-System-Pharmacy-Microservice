package com.hms.pharmacy.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hms.pharmacy.entity.MedicineInventory;
import com.hms.pharmacy.enums.StockStatus;

@Repository
public interface MedicineInventoryRepository extends JpaRepository<MedicineInventory, Long>{

	List<MedicineInventory> findByExpiryDateBefore(LocalDate now);

	List<MedicineInventory> findByMedicineIdAndExpiryDateAfterAndQuantityGreaterThanAndStatusOrderByExpiryDateAsc(Long medicineId,
			LocalDate date, Integer quantity, StockStatus status);

}
