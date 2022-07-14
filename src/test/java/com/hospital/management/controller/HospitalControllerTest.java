package com.hospital.management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.hospital.management.model.Patient;
import com.hospital.management.model.Staff;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.repository.StaffRepository;
import com.hospital.management.utils.TestHelper;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class HospitalControllerTest {

	private @Autowired MockMvc mockMvc;
	private @Autowired StaffRepository staffRepository;
	private @Autowired PatientRepository patientRepository;

	@BeforeEach
	void setUp() throws Exception {
		staffRepository.deleteAll();
		patientRepository.deleteAll();

	}

	@Test
	void testCreateNewStaf() throws Exception {
		mockMvc.perform(post("/api/v1/hospital/create-staff").contentType(MediaType.APPLICATION_JSON).queryParam("name",
				"frank")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("staff created successfully")).andReturn();
		assertThat(staffRepository.findByName("frank").isEmpty()).isFalse();
	}

	@Test
	void testGetPatientsByAgeRange() throws Exception {
		Staff staff = TestHelper.createStaff();
		staffRepository.save(staff);
		Patient patient = TestHelper.createPatient();
		patient.setAge(3);
		patientRepository.save(patient);
		mockMvc.perform(get("/api/v1/hospital/" + staff.getStaffId().toString() + "/getPatientsByAgeRange")
				.contentType(MediaType.APPLICATION_JSON).queryParam("ageRange", "2").queryParam("pageNumber", "1")
				.queryParam("pageSize", "10")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("patients retrieved successfull")).andReturn();
	}

}
