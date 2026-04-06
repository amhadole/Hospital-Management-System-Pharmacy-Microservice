package com.hms.pharmacy.service;

import java.util.List;

import com.hms.pharmacy.dto.MedicineDto;
import com.hms.pharmacy.exception.HmsException;

public interface MedicineService {
	public Long addMedicine(MedicineDto medicineDto)throws HmsException;
	
	public MedicineDto getMedicineById(Long id)throws HmsException;
	
	public String updateMedicine(MedicineDto medicineDto)throws HmsException;
	
	public List<MedicineDto> getAllMedicines()throws HmsException;
}
