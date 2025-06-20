package br.com.petsalus.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "empresa_empregado")
public class EmpresaEmpregado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "empregado_id", referencedColumnName = "id", nullable = false)
    private User empregado;
}