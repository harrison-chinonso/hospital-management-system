package com.hospital.management.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import com.hospital.management.exception.ServiceCustomException;
import com.hospital.management.model.Patient;
import com.opencsv.CSVWriter;

public class AppUtils {

	public static Pageable getPage(int pageNumber, int pageSize) {
		Sort sort = Sort.by(Sort.Order.desc("dateCreated").ignoreCase());
		pageNumber = ((pageNumber <= 0 ? 0 : pageNumber - 1) * pageSize);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		return pageable;
	}

	public static File generateExcelFile(Patient patient, String filename) {
		File file = new File(filename);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> allData = new ArrayList<>();
			String[] headerData = { "No.", "Date Created", "Name", "Age", "Last Visit Date" };
			allData.add(headerData);
			String[] bodyData = { "1", patient.getDateCreated().toString(), patient.getName(),
					String.valueOf(patient.getAge()), patient.getLast_visit_date().toString() };
			allData.add(bodyData);

			writer.writeAll(allData);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceCustomException(HttpStatus.BAD_REQUEST,
					"Sorry, unable to generate report! Try again later");
		}
		return file;
	}
}
