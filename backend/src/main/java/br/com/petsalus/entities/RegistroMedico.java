package br.com.petsalus.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "registro_medico")
public class RegistroMedico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "pet_id", referencedColumnName = "id", nullable = false)
	private Pet pet;

	@ManyToOne
	@JoinColumn(name = "servico_agenda_id", referencedColumnName = "id", nullable = false)
	private ServicoAgenda servicoAgenda;
}