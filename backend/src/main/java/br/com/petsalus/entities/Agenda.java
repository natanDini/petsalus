package br.com.petsalus.entities;

import java.time.LocalDateTime;

import br.com.petsalus.enums.AgendaStatus;
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
@Table(name = "agenda")
public class Agenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String servico;

	@Column
	private LocalDateTime data;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AgendaStatus status;

	@ManyToOne
	@JoinColumn(name = "pet_id", referencedColumnName = "id", nullable = false)
	private Pet pet;

	@ManyToOne
	@JoinColumn(name = "empresa_id", referencedColumnName = "id", nullable = false)
	private Empresa empresa;
}
