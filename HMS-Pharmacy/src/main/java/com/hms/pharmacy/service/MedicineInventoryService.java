package com.hms.pharmacy.service;

import java.util.List;

import com.hms.pharmacy.dto.MedicineInventoryDto;
import com.hms.pharmacy.exception.HmsException;

public interface MedicineInventoryService {
	List<MedicineInventoryDto> getAllMedicines() throws HmsException;

	MedicineInventoryDto getMedicineById(Long id) throws HmsException;

	MedicineInventoryDto addMedicine(MedicineInventoryDto medicineInventoryDto) throws HmsException;

	MedicineInventoryDto updateMedicine(MedicineInventoryDto medicineInventoryDto) throws HmsException;

	String deleteMedicine(Long id) throws HmsException;
	
	void deleteExpiredMedicines()throws HmsException;

}
