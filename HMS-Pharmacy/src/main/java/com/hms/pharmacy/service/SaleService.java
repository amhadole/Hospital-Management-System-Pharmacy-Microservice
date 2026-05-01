package com.hms.pharmacy.service;

import com.hms.pharmacy.dto.SaleDto;
import com.hms.pharmacy.dto.SaleRequest;
import com.hms.pharmacy.exception.HmsException;

public interface SaleService {
	Long createSale(SaleRequest saleRequestDto)throws HmsException;
	
	SaleDto getSaleById(Long id)throws HmsException;
	
	SaleDto getSaleByPrescriptionId(Long prescriptionId)throws HmsException;
	
	String updateSale(SaleDto saleDto)throws HmsException;
}
