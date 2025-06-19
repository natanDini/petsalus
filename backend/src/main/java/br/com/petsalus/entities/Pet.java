package br.com.petsalus.entities;

import br.com.petsalus.enums.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pet")
public class Pet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Float peso;

	@Column
	private String nome;

	@Column
	private Integer idade;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Sexo sexo;

	@Column(columnDefinition = "BYTEA")
	private byte[] fotoPerfil;

	@ManyToOne
	@JoinColumn(name = "tutor_id", referencedColumnName = "id", nullable = false)
	private User tutor;

	@ManyToOne
	@JoinColumn(name = "raca_id", referencedColumnName = "id", nullable = false)
	private Raca raca;

	@ManyToOne
	@JoinColumn(name = "especie_id", referencedColumnName = "id", nullable = false)
	private Especie especie;
}
