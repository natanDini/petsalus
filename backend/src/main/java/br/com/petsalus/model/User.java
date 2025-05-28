package br.com.petsalus.model;

import br.com.petsalus.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Entity
@Table(name = "app_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@CPF
	private String cpf;

	@Column
	private String nome;

	@Column
	@Email
	private String email;

	@Column
	private String senha;

	@Column
	private String username;

	@Column
	private String telefone;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole userRole;

	@Column(columnDefinition = "BYTEA")
	private byte[] fotoPerfil;

	@OneToOne
	@JoinColumn(name = "endereco_id", referencedColumnName = "id", nullable = false)
	private Endereco endereco;
}