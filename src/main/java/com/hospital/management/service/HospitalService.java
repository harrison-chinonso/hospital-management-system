package com.hospital.management.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.hospital.management.response.ServiceResponse;
import com.hospital.management.model.Patient;
import com.hospital.management.model.Staff;

public interface HospitalService {

	ServiceResponse<Staff> createNewStaf(String name);

	ServiceResponse<Staff> updateExistingStaff(String staffId, String name);

	ServiceResponse<Page<Patient>> getPatientsByAgeRange(String staffId, int ageRange, int pageNumber, int pageSize);

	ResponseEntity<Resource> downloadPatientDetail(String staffId, Long patientId);

	ServiceResponse<List<Patient>> deletePatientProfileByDateRange(String staffId, LocalDate fromDate,
			LocalDate toDate);
}
