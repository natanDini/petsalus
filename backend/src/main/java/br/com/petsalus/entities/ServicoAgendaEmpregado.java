package br.com.petsalus.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "servico_agenda_empregado")
public class ServicoAgendaEmpregado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "servico_agenda_id", referencedColumnName = "id", nullable = false)
    private ServicoAgenda servicoAgenda;

    @ManyToOne
    @JoinColumn(name = "empregado_id", referencedColumnName = "id", nullable = false)
    private User empregado;
}