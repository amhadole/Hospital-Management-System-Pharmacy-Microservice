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
import com.hms.pharmacy.dto.SaleDto;
import com.hms.pharmacy.dto.SaleItemDto;
import com.hms.pharmacy.exception.HmsException;
import com.hms.pharmacy.service.SaleItemService;
import com.hms.pharmacy.service.SaleService;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/pharmacy/sale")
public class SaleController {
	private final SaleService saleService;
	private final SaleItemService saleItemService;
	
	public SaleController(SaleService saleService, SaleItemService saleItemService) {
		this.saleService = saleService;
		this.saleItemService = saleItemService;
	}
	
	@PostMapping("/createSale")
	public ResponseEntity<ApiResponse<Long>> createSale(@RequestBody SaleDto saleDto)throws HmsException{
		Long sale = saleService.createSale(saleDto);
		ApiResponse<Long> response = new ApiResponse<Long>(HttpStatus.CREATED.value(), "Sale Created Successfully", sale, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<Long>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/updateSale")
	public ResponseEntity<ApiResponse<String>> updateSale(@RequestBody SaleDto saleDto)throws HmsException{
		String updateSale = saleService.updateSale(saleDto);
		ApiResponse<String> response = new ApiResponse<String>(HttpStatus.OK.value(), updateSale, updateSale, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getSale/{id}")
	public ResponseEntity<ApiResponse<SaleDto>> getSaleById(@PathVariable Long id)throws HmsException{
		SaleDto saleById = saleService.getSaleById(id);
		ApiResponse<SaleDto> response = new ApiResponse<SaleDto>(HttpStatus.OK.value(), "Sale Fetch By Id", saleById, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<SaleDto>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getSaleItem/{saleId}")
	public ResponseEntity<ApiResponse<List<SaleItemDto>>> getSaleItem(@PathVariable Long saleId)throws HmsException{
		List<SaleItemDto> saleItemBySaleId = saleItemService.getSaleItemBySaleId(saleId);
		ApiResponse<List<SaleItemDto>> response = new ApiResponse<List<SaleItemDto>>(HttpStatus.OK.value(), "Sale Item Fetch By Sale Id", saleItemBySaleId, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<List<SaleItemDto>>>(response, HttpStatus.OK);
	}
	
	
}
