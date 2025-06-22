package br.com.petsalus.services;

import br.com.petsalus.dtos.request.CompraAdd;
import br.com.petsalus.dtos.response.MinhasCompras;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.*;
import br.com.petsalus.enums.CompraStatus;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.mappers.MeusProdutosComprasMapper;
import br.com.petsalus.repositories.CarrinhoRepository;
import br.com.petsalus.repositories.CompraRepository;
import br.com.petsalus.repositories.ItemCompraRepository;
import br.com.petsalus.utils.CustomExceptionUtils;
import br.com.petsalus.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    private final ProdutoService produtoService;
    private final CompraRepository compraRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final ItemCompraRepository itemCompraRepository;

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

    public List<MinhasCompras> minhasCompras(Jwt jwt) throws CustomException {

        User user = userUtils.findByJwt(jwt);

        List<Compra> compras = compraRepository.findByTutorOrderByDataHoraDesc(user);

        List<MinhasCompras> minhasCompras = new ArrayList<>();

        for (Compra compra : compras) {

            List<ItemCompra> produtos = itemCompraRepository.findByCompra(compra);

            minhasCompras.add(MinhasCompras.builder()
                            .id(compra.getId())
                            .dataHora(compra.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                            .valorTotal(MeusProdutosComprasMapper.formatarPreco(compra.getValorTotal()))
                            .retirarNaLoja(compra.isRetirarNaLoja())
                            .receberNoMeuEndereco(compra.isReceberNoMeuEndereco())
                            .compraStatus(compra.getStatus())
                            .endereco(compra.getEndereco())
                            .produtos(MeusProdutosComprasMapper.map(produtos))
                    .build());
        }

        log.info(" >>> Retornando minhas compras.");
        return minhasCompras;
    }
}