package com.hms.pharmacy.service;

import java.util.List;

import com.hms.pharmacy.dto.SaleItemDto;
import com.hms.pharmacy.exception.HmsException;

public interface SaleItemService {
	Long createSaleItem(SaleItemDto saleItemDto)throws HmsException;
	
	void createMultipleSaleItem(Long saleId, Long medicineId, List<SaleItemDto> saleItemDtos)throws HmsException;
	
	void updateSaleItem(SaleItemDto saleItemDto)throws HmsException;
	
	List<SaleItemDto> getSaleItemBySaleId(Long saleId)throws HmsException;
	
	SaleItemDto getSaleItem(Long id)throws HmsException;
}
