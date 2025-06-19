package br.com.petsalus.entities;

import br.com.petsalus.enums.ServicoAgendaStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "servico_agenda")
public class ServicoAgenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServicoAgendaStatus status;

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "servico_id", referencedColumnName = "id", nullable = false)
    private Servico servico;
}