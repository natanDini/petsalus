package br.com.petsalus.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pets")
@Getter
@Setter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "especie", nullable = false, length = 50)
    private String especie;

    @Column(name = "raca", length = 50)
    private String raca;

    @Column(name = "idade")
    private Integer idade;

    @Column(name = "peso")
    private Integer peso;

    @OneToMany(mappedBy = "pet")
    private Set<UsuarioPet> usuarioPets; // Relacionamento com a tabela intermediária
}
