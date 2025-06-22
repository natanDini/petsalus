package br.com.petsalus.services;

import br.com.petsalus.entities.Carrinho;
import br.com.petsalus.entities.Compra;
import br.com.petsalus.entities.ItemCompra;
import br.com.petsalus.repositories.ItemCompraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemCompraService {

    private final ItemCompraRepository itemCompraRepository;

    public void salvar(Compra compra, List<Carrinho> produtos) {

        for (Carrinho produto : produtos) {

            ItemCompra itemCompra = new ItemCompra();

            itemCompra.setCompra(compra);
            itemCompra.setProduto(produto.getProduto());
            itemCompra.setQuantidade(produto.getQuantidade());
            itemCompra.setValorProduto(produto.getProduto().getPreco());
            itemCompra.setValorTotalProduto(produto.getProduto().getPreco().multiply(produto.getQuantidade()));

            itemCompraRepository.save(itemCompra);
        }
    }
}