package br.com.petsalus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "doenca_pet")
public class DoencaPet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "pet_id", referencedColumnName = "id", nullable = false)
	private Pet pet;

	@ManyToOne
	@JoinColumn(name = "doenca_id", referencedColumnName = "id", nullable = false)
	private Doenca doenca;
}
