package com.hospital.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.hospital.management.model.Staff;
import com.hospital.management.utils.TestHelper;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
@TestInstance(Lifecycle.PER_CLASS)
class StaffRepositoryTest {
	private @Autowired StaffRepository staffRepository;

	@BeforeEach
	void setUp() throws Exception {
		staffRepository.deleteAll();
	}

	@Test
	void testFindByStaffId() {
		Staff staff = TestHelper.createStaff();
		staffRepository.save(staff);
		assertThat(staffRepository.findByStaffId(staff.getStaffId()).isEmpty()).isFalse();
	}

	@Test
	void testExistsStaffByStaffId() {
		Staff staff = TestHelper.createStaff();
		staffRepository.save(staff);
		assertThat(staffRepository.existsStaffByStaffId(staff.getStaffId())).isTrue();
	}

}
