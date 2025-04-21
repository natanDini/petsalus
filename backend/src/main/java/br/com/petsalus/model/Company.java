package br.com.petsalus.model;

import br.com.petsalus.enums.BusinessModel;
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
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private String cnpj;

	@Column
	private String phoneNumber;

	@Column
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BusinessModel businessModel;

	@Column
	private boolean worksTwentyFourHours;

	@OneToOne
	@JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
	private Address address;
}
