package com.hospital.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;

import com.hospital.management.model.Patient;
import com.hospital.management.utils.AppUtils;
import com.hospital.management.utils.TestHelper;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
@TestInstance(Lifecycle.PER_CLASS)
class PatientRepositoryTest {
	private @Autowired PatientRepository patientRepository;

	@BeforeEach
	void setUp() throws Exception {
		patientRepository.deleteAll();
	}

	@Test
	void testFindByAgeRange() {
		Patient patient = TestHelper.createPatient();
		patient.setAge(4);
		patientRepository.save(patient);
		Page<Patient> response = patientRepository.findByAgeRange(2, AppUtils.getPage(1, 6));
		assertThat(response.getContent().isEmpty()).isFalse();
	}

	@Test
	void testFindByDateCreatedBetween() {
		LocalDateTime dateFrom = LocalDateTime.of(2020, 9, 22, 23, 50);
		LocalDateTime date = LocalDateTime.of(2021, 9, 22, 23, 50);
		Patient patient = TestHelper.createPatient();
		patient.setDateCreated(date);
		patientRepository.save(patient);
		List<Patient> result = patientRepository.findByDateCreatedBetween(dateFrom, LocalDateTime.now());
		assertThat(result.isEmpty()).isFalse();
	}

}
