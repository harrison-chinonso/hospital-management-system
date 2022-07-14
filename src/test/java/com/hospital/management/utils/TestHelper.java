package com.hospital.management.utils;

import java.time.LocalDateTime;

import com.hospital.management.model.Patient;
import com.hospital.management.model.Staff;

public class TestHelper {
	public static Staff createStaff() {
		return Staff.builder().name("frank").build();
	}

	public static Patient createPatient() {
		return Patient.builder().age(4).name("kelvin").last_visit_date(LocalDateTime.now()).build();
	}
}
