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
import lombok.Data;

@Data
@Entity
@Table(name = "app_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private String email;

	@Column
	private String username;

	@Column
	private String password;

	@Column
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole applicationRole;

	@Column(columnDefinition = "BYTEA")
	private byte[] profilePicture;

	@OneToOne
	@JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
	private Address address;
}