package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.MeusProdutosCompras;
import br.com.petsalus.dtos.response.ProdutosComprasEmpresa;
import br.com.petsalus.entities.ItemCompra;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@UtilityClass
public class ProdutosComprasEmpresaMapper {

    private static final NumberFormat FORMATO_BRL = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public ProdutosComprasEmpresa map(ItemCompra produto) {

        String fotoBase64 = produto.getProduto().getFotoPerfil() != null
                ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(produto.getProduto().getFotoPerfil())
                : null;

        return ProdutosComprasEmpresa.builder()
                .id(produto.getId())
                .qtd(produto.getQuantidade())
                .valorProduto(formatarPreco(produto.getValorProduto()))
                .valorTotalProduto(formatarPreco(produto.getValorTotalProduto()))
                .nomeProduto(produto.getProduto().getNome())
                .fotoProduto(fotoBase64)
                .build();
    }

    public static List<ProdutosComprasEmpresa> map(List<ItemCompra> produtos) {
        return produtos.stream()
                .map(ProdutosComprasEmpresaMapper::map)
                .collect(Collectors.toList());
    }

    public static String formatarPreco(BigDecimal preco) {
        return preco != null ? FORMATO_BRL.format(preco) : null;
    }
}