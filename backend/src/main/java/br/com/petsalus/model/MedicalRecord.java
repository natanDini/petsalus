package br.com.petsalus.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "medical_record")
public class MedicalRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private LocalDate date;

	@Column
	private String description;

	@OneToOne
	@JoinColumn(name = "pet_id", referencedColumnName = "id", nullable = false)
	private Pet pet;

	@OneToOne
	@JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = true)
	private User doctor;
}
