package com.hms.pharmacy.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.pharmacy.dto.ApiResponse;
import com.hms.pharmacy.dto.MedicineInventoryDto;
import com.hms.pharmacy.exception.HmsException;
import com.hms.pharmacy.service.MedicineInventoryService;

@CrossOrigin
@RequestMapping("/pharmacy/inventory")
@Validated
@RestController
public class MedicineInventoryController {
	private final MedicineInventoryService medicineInventoryService;

	public MedicineInventoryController(MedicineInventoryService medicineInventoryService) {
		this.medicineInventoryService = medicineInventoryService;
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponse<MedicineInventoryDto>> addMedicine(
			@RequestBody MedicineInventoryDto medicineInventoryDto) throws HmsException {
		MedicineInventoryDto medicine = medicineInventoryService.addMedicine(medicineInventoryDto);
		ApiResponse<MedicineInventoryDto> response = new ApiResponse<MedicineInventoryDto>(HttpStatus.CREATED.value(),
				"Inventory Added Successfully", medicine, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<MedicineInventoryDto>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<ApiResponse<MedicineInventoryDto>> getMedicineById(@PathVariable Long id)
			throws HmsException {
		MedicineInventoryDto medicineById = medicineInventoryService.getMedicineById(id);
		ApiResponse<MedicineInventoryDto> response = new ApiResponse<MedicineInventoryDto>(HttpStatus.OK.value(),
				"Inventory Fetch successfully", medicineById, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<MedicineInventoryDto>>(response, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<ApiResponse<List<MedicineInventoryDto>>> getAllMedicine() throws HmsException {
		List<MedicineInventoryDto> allMedicines = medicineInventoryService.getAllMedicines();
		ApiResponse<List<MedicineInventoryDto>> response = new ApiResponse<List<MedicineInventoryDto>>(
				HttpStatus.OK.value(), "Fetch All Inventory", allMedicines, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<List<MedicineInventoryDto>>>(response, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<ApiResponse<MedicineInventoryDto>> updateMedicine(
			@RequestBody MedicineInventoryDto medicineInventoryDto) throws HmsException {
		MedicineInventoryDto updateMedicine = medicineInventoryService.updateMedicine(medicineInventoryDto);
		ApiResponse<MedicineInventoryDto> response = new ApiResponse<MedicineInventoryDto>(HttpStatus.OK.value(),
				"Inventory Updated Successfully", updateMedicine, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<MedicineInventoryDto>>(response, HttpStatus.OK);
	}
}
