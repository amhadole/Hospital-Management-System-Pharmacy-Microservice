package com.hms.pharmacy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hms.pharmacy.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
	Boolean existsByPrescriptionId(Long prescriptionId);

	Optional<Sale> findByPrescriptionId(Long prescriptionId);
}
