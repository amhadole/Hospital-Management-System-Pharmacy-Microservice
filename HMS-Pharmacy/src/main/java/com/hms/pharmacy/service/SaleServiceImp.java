package com.hms.pharmacy.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.hms.pharmacy.dto.SaleDto;
import com.hms.pharmacy.dto.SaleItemDto;
import com.hms.pharmacy.dto.SaleRequest;
import com.hms.pharmacy.entity.Sale;
import com.hms.pharmacy.exception.HmsException;
import com.hms.pharmacy.repository.SaleRepository;

import jakarta.transaction.Transactional;

@Service
public class SaleServiceImp implements SaleService {
	private final SaleRepository saleRepository;
	private final SaleItemService saleItemService;
	private final MedicineInventoryService medicineInventoryService;
	
	public SaleServiceImp(SaleRepository saleRepository, SaleItemService saleItemService ,MedicineInventoryService medicineInventoryService) {
		this.saleRepository = saleRepository;
		this.saleItemService = saleItemService;
		this.medicineInventoryService = medicineInventoryService;
	}

	@Override
	@Transactional  
	public Long createSale(SaleRequest saleRequestDto) throws HmsException {
		if (saleRepository.existsByPrescriptionId(saleRequestDto.getPrescriptionId())) {
			throw new HmsException("SALE_ALREADY_EXISTS");
		}
		for(SaleItemDto saleItem: saleRequestDto.getSaleItems()) {
			saleItem.setBatchNo(medicineInventoryService.sellStock(saleItem.getMedicineId(), saleItem.getQuantity()));
		}
		Sale sale = new Sale(null, saleRequestDto.getPrescriptionId(), LocalDateTime.now(), saleRequestDto.getTotalAmount());
		sale = saleRepository.save(sale);
		saleItemService.createSaleItems(sale.getId(), saleRequestDto.getSaleItems());
		return sale.getId();
	}

	@Override
	public SaleDto getSaleById(Long id) throws HmsException {
		return saleRepository.findById(id).orElseThrow(() -> new HmsException("SALE_NOT_FOUND")).toDto();
	}

	@Override
	public SaleDto getSaleByPrescriptionId(Long prescriptionId) throws HmsException {
		return saleRepository.findByPrescriptionId(prescriptionId).orElseThrow(() -> new HmsException("SALE_NOT_FOUND"))
				.toDto();
	}

	@Override
	public String updateSale(SaleDto saleDto) throws HmsException {
		Sale sale = saleRepository.findById(saleDto.getId()).orElseThrow(() -> new HmsException("SALE_NOT_FOUND"));
		sale.setSaleDate(saleDto.getSaleDate());
		sale.setTotalAmount(saleDto.getTotalAmount());
		saleRepository.save(sale);
		return "Sale Updated Successfully";
	}

}
