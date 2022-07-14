package com.hospital.management.service.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital.management.response.ServiceResponse;
import com.hospital.management.exception.ServiceCustomException;
import com.hospital.management.model.Patient;
import com.hospital.management.model.Staff;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.repository.StaffRepository;
import com.hospital.management.service.HospitalService;
import com.hospital.management.utils.AppUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
	private final PatientRepository patientRepository;
	private final StaffRepository staffRepository;

	@Override
	public ServiceResponse<Staff> createNewStaf(String name) {
		return new ServiceResponse<>(HttpStatus.OK, "staff created successfully",
				staffRepository.save(Staff.builder().name(name).build()));
	}

	@Override
	public ServiceResponse<Staff> updateExistingStaff(String staffId, String name) {
		if (!validateStaff(staffId)) {
			throw new ServiceCustomException(HttpStatus.FORBIDDEN, "please provide valid staff id to gain access");
		}
		Staff staff = staffRepository.findByStaffId(UUID.fromString(staffId)).get();
		staff.setName(name);
		return new ServiceResponse<>(HttpStatus.OK, "staff update successfully", staffRepository.save(staff));
	}

	@Override
	public ServiceResponse<Page<Patient>> getPatientsByAgeRange(String staffId, int ageRange, int pageNumber,
			int pageSize) {
		if (!validateStaff(staffId)) {
			throw new ServiceCustomException(HttpStatus.FORBIDDEN, "please provide valid staff id to gain access");
		}
		Page<Patient> response = patientRepository.findByAgeRange(ageRange, AppUtils.getPage(pageNumber, pageSize));
		return new ServiceResponse<>(HttpStatus.OK, "patients retrieved successfully", response);
	}

	@Override
	public ResponseEntity<Resource> downloadPatientDetail(String staffId, Long patientId) {
		if (!validateStaff(staffId)) {
			throw new ServiceCustomException(HttpStatus.FORBIDDEN, "please provide valid staff id to gain access");
		}
		return patientRepository.findById(patientId).map(patient -> {
			String fileName = "patient-report.csv";
			File file = AppUtils.generateExcelFile(patient, fileName);
			Resource resource = null;
			try {
				resource = new UrlResource(file.toURI());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		}).orElseThrow(() -> new ServiceCustomException(HttpStatus.NOT_FOUND, "patient with id " + patientId + " does not exist"));
	}

	@Override
	public ServiceResponse<List<Patient>> deletePatientProfileByDateRange(String staffId, LocalDate fromDate,
			LocalDate toDate) {
		if (!validateStaff(staffId)) {
			throw new ServiceCustomException(HttpStatus.FORBIDDEN, "please provide valid staff id to gain access");
		}
		List<Patient> result = patientRepository.findByDateCreatedBetween(fromDate.atStartOfDay(),
				toDate.atTime(23, 59, 59));
		patientRepository.deleteAll(result);
		return new ServiceResponse<>(HttpStatus.OK, "patients deleted successfull", result);
	}

	public boolean validateStaff(String staffId) {
		return staffRepository.existsStaffByStaffId(UUID.fromString(staffId));
	}

}
