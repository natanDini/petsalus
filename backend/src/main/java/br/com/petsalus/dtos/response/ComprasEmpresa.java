package br.com.petsalus.dtos.response;

import br.com.petsalus.entities.Endereco;
import lombok.Builder;

import java.util.List;

@Builder
public record ComprasEmpresa (
        Long id,
        String dataHora,
        String valorTotal,
        boolean retirarNaLoja,
        boolean receberNoMeuEndereco,
        String comprador,
        String cpfComprador,
        Endereco endereco,
        String fotoComprador,
        List<ProdutosComprasEmpresa> produtos
){
}
