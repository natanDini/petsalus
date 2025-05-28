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
@Table(name = "registro_medico")
public class RegistroMedico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private LocalDate data;

	@Column
	private String descricao;

	@OneToOne
	@JoinColumn(name = "doutor_id", referencedColumnName = "id")
	private User doutor;

	@OneToOne
	@JoinColumn(name = "pet_id", referencedColumnName = "id", nullable = false)
	private Pet pet;
}
