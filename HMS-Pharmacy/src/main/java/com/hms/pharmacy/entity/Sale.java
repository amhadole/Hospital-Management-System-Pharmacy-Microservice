package com.hms.pharmacy.entity;

import java.time.LocalDateTime;

import com.hms.pharmacy.dto.SaleDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long prescriptionId;
	private LocalDateTime saleDate;
	private Double totalAmount;

	public Sale(Long id) {
		this.id = id;
	}

	public SaleDto toDto() {
		return new SaleDto(id, prescriptionId, saleDate, totalAmount);
	}

}
