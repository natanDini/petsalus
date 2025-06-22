package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.ProdutoResEmpresa;
import br.com.petsalus.entities.Produto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@UtilityClass
public class ProdutoResEmpresaMapper {

    private static final NumberFormat FORMATO_BRL = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public ProdutoResEmpresa map(Produto produto) {

        String fotoBase64 = produto.getFotoPerfil() != null
                ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(produto.getFotoPerfil())
                : null;

        return ProdutoResEmpresa.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(formatarPreco(produto.getPreco()))
                .disponivel(produto.isDisponivel())
                .qtdEstoque(produto.getQtdEstoque())
                .qtdVendida(produto.getQtdVendida())
                .fotoPerfil(fotoBase64)
                .build();
    }

    public static List<ProdutoResEmpresa> map(List<Produto> produtos) {
        return produtos.stream()
                .map(ProdutoResEmpresaMapper::map)
                .collect(Collectors.toList());
    }

    public static String formatarPreco(BigDecimal preco) {
        return preco != null ? FORMATO_BRL.format(preco) : null;
    }
}