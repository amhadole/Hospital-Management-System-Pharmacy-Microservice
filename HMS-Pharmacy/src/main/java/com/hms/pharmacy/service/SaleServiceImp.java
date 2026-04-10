package com.hms.pharmacy.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.hms.pharmacy.dto.SaleDto;
import com.hms.pharmacy.entity.Sale;
import com.hms.pharmacy.exception.HmsException;
import com.hms.pharmacy.repository.SaleRepository;

@Service
public class SaleServiceImp implements SaleService {
	private final SaleRepository saleRepository;

	public SaleServiceImp(SaleRepository saleRepository) {
		this.saleRepository = saleRepository;
	}

	@Override
	public Long createSale(SaleDto saleDto) throws HmsException {
		if (saleRepository.existsByPrescriptionId(saleDto.getPrescriptionId())) {
			throw new HmsException("SALE_ALREADY_EXISTS");
		}
		saleDto.setSaleDate(LocalDateTime.now());
		return saleRepository.save(saleDto.toEntity()).getId();
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
