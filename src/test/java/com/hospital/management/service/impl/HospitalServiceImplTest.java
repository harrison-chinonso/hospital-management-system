package com.hospital.management.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.hospital.management.response.ServiceResponse;
import com.hospital.management.model.Patient;
import com.hospital.management.model.Staff;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.repository.StaffRepository;
import com.hospital.management.service.HospitalService;
import com.hospital.management.utils.TestHelper;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class HospitalServiceImplTest {

	@Mock
	private PatientRepository patientRepository;
	@Mock
	private StaffRepository staffRepository;

	private HospitalService hospitalService;

	@BeforeEach
	void setUp() throws Exception {
		hospitalService = new HospitalServiceImpl(patientRepository, staffRepository);
	}

	@Test
	void testCreateNewStaf() {
		Staff staff = TestHelper.createStaff();
		staff.setStaffId(UUID.randomUUID());
		when(staffRepository.save(any(Staff.class))).thenReturn(staff);
		ServiceResponse<Staff> response = hospitalService.createNewStaf("frank");
		assertNotNull(response);
		verify(staffRepository, times(1)).save(any(Staff.class));
	}

	@Test
	void testGetPatientsByAgeRange() {
		Page<Patient> bookPage = new PageImpl<>(List.of(TestHelper.createPatient()));
		when(staffRepository.existsStaffByStaffId(any(UUID.class))).thenReturn(true);
		when(patientRepository.findByAgeRange(anyInt(), any(Pageable.class))).thenReturn(bookPage);
		ServiceResponse<Page<Patient>> response = hospitalService.getPatientsByAgeRange(UUID.randomUUID().toString(), 5,
				1, 4);
		List<Patient> content = response.getData().getContent();
		assertThat(content.isEmpty()).isFalse();
		verify(patientRepository, times(1)).findByAgeRange(anyInt(), any(Pageable.class));
	}

}
