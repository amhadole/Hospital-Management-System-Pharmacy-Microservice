package com.hms.pharmacy.entity;

import java.time.LocalDateTime;

import com.hms.pharmacy.dto.MedicineDto;
import com.hms.pharmacy.enums.MedicineCategory;
import com.hms.pharmacy.enums.MedicineType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String dosage;
	@Enumerated(EnumType.STRING)
	private MedicineCategory category;
	@Enumerated(EnumType.STRING)
	private MedicineType type;
	private String manufacturer;
	private Integer unitPrice;
	private Integer stock;
	private LocalDateTime createdAt;

	public Medicine(Long id) {
		this.id = id;
	}

	public MedicineDto toDto() {
		return new MedicineDto(id, name, dosage, category, type, manufacturer, unitPrice, stock, createdAt);
	}
}
