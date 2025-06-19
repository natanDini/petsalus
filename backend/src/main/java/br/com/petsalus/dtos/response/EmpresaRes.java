package br.com.petsalus.dtos.response;

import br.com.petsalus.entities.Endereco;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record EmpresaRes (
     Long id,
     String cnpj,
     String nome,
     String email,
     String telefone,
     String descricao,
     String fotoPerfil,
     String modeloComercial,
     boolean trabalhaVinteQuatroHoras,

     @JsonFormat(pattern = "HH:mm")
     LocalTime horaAbertura,

     @JsonFormat(pattern = "HH:mm")
     LocalTime horaEncerramento,

     Endereco endereco) {
}