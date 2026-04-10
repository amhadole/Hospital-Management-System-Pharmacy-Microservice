package com.hms.pharmacy.entity;

import java.time.LocalDate;

import com.hms.pharmacy.dto.MedicineInventoryDto;
import com.hms.pharmacy.enums.StockStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineInventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medicineId")
	private Medicine medicine;		// Linked to medicine entity
	private String batchNo;			//Batch number of medicine
	private Integer quantity;		//Quantity in stock
	private LocalDate expiryDate;	//Expire date of the batch
	private LocalDate addedDate;	//Date when the batch is added in inventory
	private Integer initialQuantity; //Initial quantity of batch
	@Enumerated(EnumType.STRING)
	private StockStatus status;
	public MedicineInventoryDto toDto() {
		return new MedicineInventoryDto(id, medicine.getId(), batchNo, quantity, expiryDate, addedDate, initialQuantity, status);
	}
	
}
