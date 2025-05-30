package br.com.petsalus.entities;

import br.com.petsalus.enums.Estados;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String cep;

	@Column(nullable = false)
	private String bairro;

	@Column(nullable = false)
	private String numero;

	@Column(nullable = false)
	private String cidade;

	@Column(nullable = false)
	private String endereco;

	@Column
	private String complemento;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Estados estado;
}
