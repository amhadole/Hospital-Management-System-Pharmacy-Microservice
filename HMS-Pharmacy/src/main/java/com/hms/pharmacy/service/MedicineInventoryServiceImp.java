package com.hms.pharmacy.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hms.pharmacy.dto.MedicineInventoryDto;
import com.hms.pharmacy.entity.MedicineInventory;
import com.hms.pharmacy.enums.StockStatus;
import com.hms.pharmacy.exception.HmsException;
import com.hms.pharmacy.repository.MedicineInventoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MedicineInventoryServiceImp implements MedicineInventoryService {
	private final MedicineInventoryRepository medicineInventoryRepository;
	private final MedicineService medicineService;

	public MedicineInventoryServiceImp(MedicineInventoryRepository medicineInventoryRepository,
			MedicineService medicineService) {
		this.medicineInventoryRepository = medicineInventoryRepository;
		this.medicineService = medicineService;
	}

	@Override
	public List<MedicineInventoryDto> getAllMedicines() throws HmsException {
		return medicineInventoryRepository.findAll().stream().map(MedicineInventory::toDto).toList();
	}

	@Override
	public MedicineInventoryDto getMedicineById(Long id) throws HmsException {

		return medicineInventoryRepository.findById(id).orElseThrow(() -> new HmsException("INVENTORY_NOT_FOUND"))
				.toDto();
	}

	@Override
	public MedicineInventoryDto addMedicine(MedicineInventoryDto medicineInventoryDto) throws HmsException {

		medicineInventoryDto.setAddedDate(LocalDate.now());
		medicineService.addStock(medicineInventoryDto.getMedicineId(), medicineInventoryDto.getQuantity());
		medicineInventoryDto.setInitialQuantity(medicineInventoryDto.getQuantity());
		medicineInventoryDto.setStatus(StockStatus.ACTIVE);
		return medicineInventoryRepository.save(medicineInventoryDto.toEntity()).toDto();
	}

	@Override
	public MedicineInventoryDto updateMedicine(MedicineInventoryDto medicineInventoryDto) throws HmsException {
		MedicineInventory existingInventory = medicineInventoryRepository.findById(medicineInventoryDto.getId())
				.orElseThrow(() -> new HmsException("INVENTORY_NOT_FOUND"));
		existingInventory.setBatchNo(medicineInventoryDto.getBatchNo());
		if (existingInventory.getQuantity() < medicineInventoryDto.getQuantity()) {
			medicineService.addStock(medicineInventoryDto.getMedicineId(),
					medicineInventoryDto.getQuantity() - existingInventory.getQuantity());
		} else if (existingInventory.getQuantity() > medicineInventoryDto.getQuantity()) {
			medicineService.removeStock(medicineInventoryDto.getMedicineId(),
					existingInventory.getQuantity() - medicineInventoryDto.getQuantity());
		}
		existingInventory.setQuantity(medicineInventoryDto.getQuantity());
		existingInventory.setInitialQuantity(medicineInventoryDto.getInitialQuantity());
		existingInventory.setExpiryDate(medicineInventoryDto.getExpiryDate());
		return medicineInventoryRepository.save(existingInventory).toDto();

	}

	@Override
	public String deleteMedicine(Long id) throws HmsException {
		medicineInventoryRepository.deleteById(id);
		return "Inventory Delete Successfully";

	}

	public void markExpired(List<MedicineInventory> inventories) throws HmsException {
		for (MedicineInventory inventory : inventories) {
			if (inventory.getExpiryDate().isBefore(LocalDate.now())) {
				inventory.setStatus(StockStatus.EXPIRED);
			}
		}
		medicineInventoryRepository.saveAll(inventories);
	}

	@Override
	@Scheduled(cron = "0 14 17 * * ?")
	public void deleteExpiredMedicines() throws HmsException {
		System.out.println("Deleting Expired medicine...");
		List<MedicineInventory> expiredMedicines = medicineInventoryRepository.findByExpiryDateBefore(LocalDate.now());
		for (MedicineInventory medicine : expiredMedicines) {
			medicineService.removeStock(medicine.getMedicine().getId(), medicine.getQuantity());
		}
		this.markExpired(expiredMedicines);
	}

}
