package br.com.petsalus.entities;

import br.com.petsalus.enums.CompraStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column
    private BigDecimal valorTotal;

    @Column
    private boolean retirarNaLoja;

    @Column
    private boolean receberNoMeuEndereco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompraStatus status;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private User tutor;

    @OneToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;
}