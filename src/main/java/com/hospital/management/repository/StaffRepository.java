package com.hospital.management.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.management.model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
	Optional<Staff> findByStaffId(UUID staffId);

	Optional<Staff> findByName(String name);

	boolean existsStaffByStaffId(UUID staffId);
}
