package br.com.petsalus.entities;

import br.com.petsalus.enums.ModeloComercial;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "empresa")
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private LocalTime horaAbertura;

	@Column
	private LocalTime horaEncerramento;

	@Column
	@CNPJ
	private String cnpj;

	@Column
	private String nome;

	@Column
	@Email
	private String email;

	@Column
	private String telefone;

	@Column
	private String descricao;

	@Column(columnDefinition = "BYTEA")
	private byte[] fotoPerfil;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ModeloComercial modeloComercial;

	@Column
	private boolean trabalhaVinteQuatroHoras;

	@OneToOne
	@JoinColumn(name = "endereco_id", referencedColumnName = "id", nullable = false)
	private Endereco endereco;

	@ManyToOne
	@JoinColumn(name = "dono_id", referencedColumnName = "id", nullable = false)
	private User dono;
}
