package com.hospital.management.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.management.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	@Query("select p from Patient p where p.age >= ?1")
	Page<Patient> findByAgeRange(int ageRange, Pageable pageable);

	@Query("select p from Patient p where p.dateCreated between ?1 and ?2")
	List<Patient> findByDateCreatedBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
