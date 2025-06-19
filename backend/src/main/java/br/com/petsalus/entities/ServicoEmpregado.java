package br.com.petsalus.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "servico_empregado")
public class ServicoEmpregado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "servico_id", referencedColumnName = "id", nullable = false)
    private ServicoAgenda servicoAgenda;

    @ManyToOne
    @JoinColumn(name = "empregado_id", referencedColumnName = "id", nullable = false)
    private User empregado;
}