package br.com.petsalus.dtos.response;

import br.com.petsalus.entities.Endereco;
import br.com.petsalus.enums.CompraStatus;
import lombok.Builder;

import java.util.List;

@Builder
public record MinhasCompras (
        Long id,
        String dataHora,
        String valorTotal,
        boolean retirarNaLoja,
        boolean receberNoMeuEndereco,
        CompraStatus compraStatus,
        Endereco endereco,
        List<MeusProdutosCompras> produtos
){
}
