package com.hms.pharmacy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hms.pharmacy.dto.SaleItemDto;
import com.hms.pharmacy.entity.Sale;
import com.hms.pharmacy.entity.SaleItem;
import com.hms.pharmacy.exception.HmsException;
import com.hms.pharmacy.repository.SaleItemRepository;
import com.hms.pharmacy.repository.SaleRepository;

@Service
public class SaleItemServiceImp implements SaleItemService{
	
	private final SaleItemRepository saleItemRepository;
	private final SaleRepository saleRepository;
	public SaleItemServiceImp(SaleItemRepository saleItemRepository, SaleRepository saleRepository) {
		this.saleItemRepository = saleItemRepository;
		this.saleRepository = saleRepository;
	}

	@Override
	public Long createSaleItem(SaleItemDto saleItemDto) throws HmsException {
		return saleItemRepository.save(saleItemDto.toEntity()).getId();
	}

	@Override
	public void createMultipleSaleItem(Long saleId, Long medicineId, List<SaleItemDto> saleItemDtos)
			throws HmsException {
		saleItemDtos.stream().map((x)->{
			x.setSaleId(saleId);
			x.setMedicineId(medicineId);
			return x.toEntity();
		}).forEach(saleItemRepository::save);
		
	}

	@Override
	public void updateSaleItem(SaleItemDto saleItemDto) throws HmsException {
		SaleItem existingSaleItem = saleItemRepository.findById(saleItemDto.getId()).orElseThrow(()-> new HmsException("SALE_ITEM_NOT_FOUND"));
		existingSaleItem.setQuantity(saleItemDto.getQuantity());
		existingSaleItem.setUnitPrice(saleItemDto.getUnitPrice());
		saleItemRepository.save(existingSaleItem);
		
	}

	@Override
	public List<SaleItemDto> getSaleItemBySaleId(Long saleId) throws HmsException {
		return saleItemRepository.findBySaleId(saleId).stream().map(SaleItem::toDto).toList();
	}

	@Override
	public SaleItemDto getSaleItem(Long id) throws HmsException {
		return saleItemRepository.findById(id).map(SaleItem::toDto).orElseThrow(()-> new HmsException("SALE_ITEM_NOT_FOUND"));
	}
	
	@Override
	public void createSaleItems(Long saleId, List<SaleItemDto> saleItemDtos) throws HmsException{
		Sale sale = saleRepository.findById(saleId).orElseThrow(()-> new HmsException("SALE_NOT_FOUND"));
		saleItemDtos.stream().map((x)->{
			SaleItem item = x.toEntity();
			item.setSale(sale);
			return item;
		}).forEach(saleItemRepository::save);
	}
}
