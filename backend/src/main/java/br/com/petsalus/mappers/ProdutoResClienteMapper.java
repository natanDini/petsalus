package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.ProdutoResCliente;
import br.com.petsalus.entities.Produto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@UtilityClass
public class ProdutoResClienteMapper {

    private static final NumberFormat FORMATO_BRL = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public ProdutoResCliente map(Produto produto) {

        String fotoBase64 = produto.getFotoPerfil() != null
                ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(produto.getFotoPerfil())
                : null;

        return ProdutoResCliente.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(formatarPreco(produto.getPreco()))
                .fotoPerfil(fotoBase64)
                .build();
    }

    public static List<ProdutoResCliente> map(List<Produto> produtos) {
        return produtos.stream()
                .map(ProdutoResClienteMapper::map)
                .collect(Collectors.toList());
    }

    public static String formatarPreco(BigDecimal preco) {
        return preco != null ? FORMATO_BRL.format(preco) : null;
    }
}