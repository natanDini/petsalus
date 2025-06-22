package br.com.petsalus.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column
    private BigDecimal preco;

    @Column
    private boolean disponivel;

    @Column
    private BigDecimal qtdEstoque;

    @Column
    private BigDecimal qtdVendida;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(columnDefinition = "BYTEA")
    private byte[] fotoPerfil;

    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id", nullable = false)
    private Empresa empresa;
}