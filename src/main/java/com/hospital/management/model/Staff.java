package com.hospital.management.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "staff")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Staff extends BaseModel<Staff> implements Serializable {
	private String name;

	@Builder.Default
	@Type(type = "uuid-char")
	@Column(name = "staff_id", updatable = false, nullable = false, unique = true)
	private UUID staffId = UUID.randomUUID();

	@Builder.Default
	@Column(name = "registration_date", updatable = false, nullable = false)
	private LocalDateTime registration_date = LocalDateTime.now();
}
