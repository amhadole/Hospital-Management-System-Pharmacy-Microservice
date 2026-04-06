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
import com.hms.pharmacy.dto.MedicineDto;
import com.hms.pharmacy.exception.HmsException;
import com.hms.pharmacy.service.MedicineService;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/pharmacy/medicines")
public class MedicineController {
	private final MedicineService medicineService;
	
	public MedicineController(MedicineService medicineService) {
		this.medicineService = medicineService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse<Long>> addMedicine(@RequestBody MedicineDto medicineDto)throws HmsException{
		Long medicine = medicineService.addMedicine(medicineDto);
		ApiResponse<Long> response = new ApiResponse<Long>(HttpStatus.CREATED.value(), "Medicine Added Successfully", medicine, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<Long>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ApiResponse<MedicineDto>> getMedicineById(@PathVariable Long id)throws HmsException{
		MedicineDto medicineById = medicineService.getMedicineById(id);
		ApiResponse<MedicineDto> response = new ApiResponse<MedicineDto>(HttpStatus.OK.value(), "Medicine Fetch By Id", medicineById, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<MedicineDto>>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse<String>> updateMedicine(@RequestBody MedicineDto medicineDto)throws HmsException{
		String updateMedicine = medicineService.updateMedicine(medicineDto);
		ApiResponse<String> response = new ApiResponse<String>(HttpStatus.OK.value(), updateMedicine, updateMedicine, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<ApiResponse<List<MedicineDto>>> getAllMedicines()throws HmsException{
		List<MedicineDto> allMedicines = medicineService.getAllMedicines();
		ApiResponse<List<MedicineDto>> response = new ApiResponse<List<MedicineDto>>(HttpStatus.OK.value(), "Fetch All Medicines", allMedicines, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<List<MedicineDto>>>(response, HttpStatus.OK);
	}
}
