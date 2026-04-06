package com.hms.pharmacy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hms.pharmacy.dto.MedicineDto;
import com.hms.pharmacy.entity.Medicine;
import com.hms.pharmacy.exception.HmsException;
import com.hms.pharmacy.repository.MedicineRepository;

@Service
public class MedicineServiceImp implements MedicineService{
	private final MedicineRepository medicineRepository;
	
	public MedicineServiceImp(MedicineRepository medicineRepository) {
		this.medicineRepository = medicineRepository;
	}

	@Override
	public Long addMedicine(MedicineDto medicineDto) throws HmsException {
		Optional<Medicine> opt = medicineRepository.findByNameIgnoreCaseAndDosageIgnoreCase(medicineDto.getName(), medicineDto.getDosage());
		if(opt.isPresent()) {
			throw new HmsException("MEDICINE_ALREADY_EXISTS");
		}
		medicineDto.setCreatedAt(LocalDateTime.now());
		return medicineRepository.save(medicineDto.toEntity()).getId();
		
	}

	@Override
	public MedicineDto getMedicineById(Long id) throws HmsException {
		
		return medicineRepository.findById(id).orElseThrow(()-> new HmsException("MEDICINE_NOT_FOUND")).toDto();
	}

	@Override
	public String updateMedicine(MedicineDto medicineDto) throws HmsException {
		Medicine existingMedicine = medicineRepository.findById(medicineDto.getId()).orElseThrow(()-> new HmsException("MEDICINE_NOT_FOUND"));
		if(!(medicineDto.getName().equalsIgnoreCase(existingMedicine.getName()) && medicineDto.getDosage().equalsIgnoreCase(existingMedicine.getDosage()))) {
			Optional<Medicine> opt = medicineRepository.findByNameIgnoreCaseAndDosageIgnoreCase(medicineDto.getName(), medicineDto.getDosage());
			if(opt.isPresent()) {
				throw new HmsException("MEDICINE_ALREADY_EXISTS");
			}
		}
		existingMedicine.setName(medicineDto.getName());
		existingMedicine.setDosage(medicineDto.getDosage());
		existingMedicine.setCategory(medicineDto.getCategory());
		existingMedicine.setType(medicineDto.getType());
		existingMedicine.setManufacturer(medicineDto.getManufacturer());
		existingMedicine.setUnitPrice(medicineDto.getUnitPrice());
		medicineRepository.save(existingMedicine);
		return "Medicine Updated Successfully";
	}

	@Override
	public List<MedicineDto> getAllMedicines() throws HmsException {
		return medicineRepository.findAll().stream().map(Medicine::toDto).toList();
	}
}
