package com.hospital.management.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.response.ServiceResponse;
import com.hospital.management.model.Patient;
import com.hospital.management.model.Staff;
import com.hospital.management.service.HospitalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/hospital-record/")
@RequiredArgsConstructor
public class HospitalController {
	private final HospitalService hospitalService;

	@PostMapping(value = "create-staff", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ServiceResponse<Staff>> createNewStaf(
			@RequestParam(value = "name") String name) {
		ServiceResponse<Staff> response = hospitalService.createNewStaf(name);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@PreAuthorize("hasRole('STAFF')")
	@PutMapping(value = "{staffId}/update-staff", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ServiceResponse<Staff>> updateExistingStaff(@PathVariable("staffId") String staffId,
			@RequestParam(value = "name") String name) {
		ServiceResponse<Staff> response = hospitalService.updateExistingStaff(staffId, name);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@PreAuthorize("hasRole('STAFF')")
	@GetMapping(value = "{staffId}/getPatientsByAgeRange", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ServiceResponse<Page<Patient>>> getPatientsByAgeRange(@PathVariable("staffId") String staffId,
			@RequestParam(value = "ageRange", defaultValue = "2") int ageRange,
			@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		ServiceResponse<Page<Patient>> response = hospitalService.getPatientsByAgeRange(staffId, ageRange, pageNumber,
				pageSize);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@PreAuthorize("hasRole('STAFF')")
	@GetMapping(value = "{staffId}/downloadPatientDetail", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> downloadPatientDetail(@PathVariable("staffId") String staffId,
			@RequestParam(value = "patientId") Long patientId) {
		return hospitalService.downloadPatientDetail(staffId, patientId);
	}

	@PreAuthorize("hasRole('STAFF')")
	@DeleteMapping("{staffId}/delete-patients")
	public ResponseEntity<ServiceResponse<List<Patient>>> deletePatientProfileByDateRange(
			@PathVariable("staffId") String staffId,
			@RequestParam(value = "fromDate")  @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fromDate,
			@RequestParam(value = "toDate")  @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate toDate) {
		ServiceResponse<List<Patient>> response = hospitalService.deletePatientProfileByDateRange(staffId, fromDate,
				toDate);
		return new ResponseEntity<>(response, response.getStatus());
	}
}
