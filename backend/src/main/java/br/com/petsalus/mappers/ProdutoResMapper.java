package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.ProdutoRes;
import br.com.petsalus.entities.Carrinho;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@UtilityClass
public class ProdutoResMapper {

    private static final NumberFormat FORMATO_BRL = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public ProdutoRes map(Carrinho carrinho) {

        String fotoBase64 = carrinho.getProduto().getFotoPerfil() != null
                ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(carrinho.getProduto().getFotoPerfil())
                : null;

        return ProdutoRes.builder()
                .nome(carrinho.getProduto().getNome())
                .preco(formatarPreco(carrinho.getProduto().getPreco()))
                .empresa(carrinho.getProduto().getEmpresa().getNome())
                .quantidade(carrinho.getQuantidade())
                .precoTotalProduto(formatarPreco(carrinho.getProduto().getPreco().multiply(carrinho.getQuantidade())))
                .fotoPerfil(fotoBase64)
                .build();
    }

    public static List<ProdutoRes> map(List<Carrinho> produtos) {
        return produtos.stream()
                .map(ProdutoResMapper::map)
                .collect(Collectors.toList());
    }

    public static String formatarPreco(BigDecimal preco) {
        return preco != null ? FORMATO_BRL.format(preco) : null;
    }
}