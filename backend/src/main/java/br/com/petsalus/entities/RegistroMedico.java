package br.com.petsalus.entities;

import br.com.petsalus.enums.RegistroMedicoStatus;
import br.com.petsalus.enums.Sexo;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "registro_medico")
public class RegistroMedico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RegistroMedicoStatus status;

	@ManyToOne
	@JoinColumn(name = "pet_id", referencedColumnName = "id", nullable = false)
	private Pet pet;

	@ManyToOne
	@JoinColumn(name = "servico_agenda_id", referencedColumnName = "id", nullable = false)
	private ServicoAgenda servicoAgenda;
}