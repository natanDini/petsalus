package br.com.petsalus.services;

import br.com.petsalus.dtos.request.CompraAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Carrinho;
import br.com.petsalus.entities.Compra;
import br.com.petsalus.entities.Endereco;
import br.com.petsalus.entities.User;
import br.com.petsalus.enums.CompraStatus;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.repositories.CarrinhoRepository;
import br.com.petsalus.repositories.CompraRepository;
import br.com.petsalus.utils.CustomExceptionUtils;
import br.com.petsalus.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompraService {

    private final UserUtils userUtils;
    private final CustomExceptionUtils customExceptionUtils;

    private final RetornoService retornoService;
    private final EnderecoService enderecoService;
    private final CarrinhoService carrinhoService;
    private final ItemCompraService itemCompraService;

    private final CompraRepository compraRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoService produtoService;

    public ResponseEntity<Retorno> comprar(Jwt jwt, CompraAdd compraAdd) throws CustomException {

        User user = userUtils.findByJwt(jwt);
        Endereco endereco = null;

        if (!compraAdd.retirarNaLoja() && !compraAdd.receberNoMeuEndereco() && compraAdd.endereco() == null) {
            throw customExceptionUtils.errorAndBadRequest("Para receber compra sem retirar na loja e sem ser em seu endereço é necessário informar um novo endereço.");
        }

        if (!compraAdd.retirarNaLoja() && !compraAdd.receberNoMeuEndereco()) {
            endereco = enderecoService.salvar(compraAdd.endereco());
        }

        if (!compraAdd.retirarNaLoja() && compraAdd.receberNoMeuEndereco()) {
            endereco = user.getEndereco();
        }

        List<Carrinho> produtos = carrinhoRepository.findByTutor(user);

        BigDecimal total = BigDecimal.ZERO;

        for (Carrinho produto : produtos) {
            total = total.add(produto.getProduto().getPreco().multiply(produto.getQuantidade()));
        }

        Compra compra = new Compra();

        compra.setTutor(user);
        compra.setValorTotal(total);
        compra.setEndereco(endereco);
        compra.setStatus(CompraStatus.PENDENTE);
        compra.setDataHora(LocalDateTime.now());
        compra.setRetirarNaLoja(compraAdd.retirarNaLoja());
        compra.setReceberNoMeuEndereco(compraAdd.receberNoMeuEndereco());

        compraRepository.save(compra);

        itemCompraService.salvar(compra, produtos);

        produtoService.atualizarProdutosVendidos(produtos);

        carrinhoService.deletar(jwt);

        log.info(" >>> Compra realizada com sucesso.");
        return retornoService.retornoSucesso("Compra realizada com sucesso.");
    }
}