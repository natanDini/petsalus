package br.com.petsalus.services;

import br.com.petsalus.dtos.response.CarrinhoRes;
import br.com.petsalus.dtos.response.ProdutoRes;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Carrinho;
import br.com.petsalus.entities.Produto;
import br.com.petsalus.entities.User;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.mappers.ProdutoResMapper;
import br.com.petsalus.repositories.CarrinhoRepository;
import br.com.petsalus.utils.CarrinhoUtils;
import br.com.petsalus.utils.CustomExceptionUtils;
import br.com.petsalus.utils.ProdutoUtils;
import br.com.petsalus.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final UserUtils userUtils;
    private final ProdutoUtils produtoUtils;
    private final CarrinhoUtils carrinhoUtils;
    private final CustomExceptionUtils customExceptionUtils;

    private final RetornoService retornoService;

    private final CarrinhoRepository carrinhoRepository;

    public ResponseEntity<Retorno> registrar(Long produtoId, Long quantidade, Jwt jwt) throws CustomException {

        User tutor = userUtils.findByJwt(jwt);
        Produto produto = produtoUtils.findById(produtoId);

        if (produto.getQtdEstoque().compareTo(BigDecimal.valueOf(quantidade)) < 0) {
            throw customExceptionUtils.errorAndBadRequest("Quantidade em estoque insuficiente para quantidade requisitada.");
        }

        Carrinho carrinho;

        if (carrinhoRepository.existsByTutorAndProduto(tutor, produto)) {
            carrinho = carrinhoUtils.findByTutorAndProduto(tutor, produto);
        } else {
            carrinho = new Carrinho();
        }

        carrinho.setTutor(tutor);
        carrinho.setProduto(produto);
        carrinho.setQuantidade(BigDecimal.valueOf(quantidade));

        carrinhoRepository.save(carrinho);

        log.info(" >>> Produto adicionado ao carrinho com sucesso.");
        return retornoService.retornoSucesso("Produto adicionado com sucesso.");
    }

    public ResponseEntity<Retorno> atualizar(Long itemCarrinhoId, Long qtd) throws CustomException {

        Carrinho carrinho = carrinhoUtils.findById(itemCarrinhoId);
        Produto produto = carrinho.getProduto();

        if (produto.getQtdEstoque().compareTo(BigDecimal.valueOf(qtd)) < 0) {
            throw customExceptionUtils.errorAndBadRequest("Quantidade em estoque insuficiente para quantidade requisitada.");
        }

        carrinho.setQuantidade(BigDecimal.valueOf(qtd));
        carrinhoRepository.save(carrinho);

        log.info(" >>> Quantidade de Produto atualizada no carrinho com sucesso.");
        return retornoService.retornoSucesso("Quantidade de Produto atualizada no carrinho com sucesso.");
    }

    public CarrinhoRes meuCarrinho(Jwt jwt) {

        User tutor = userUtils.findByJwt(jwt);

        List<Carrinho> produtos = carrinhoRepository.findByTutor(tutor);
        List<ProdutoRes> produtosMapped = ProdutoResMapper.map(produtos);

        BigDecimal total = BigDecimal.ZERO;

        for (Carrinho produto : produtos) {
            total = total.add(produto.getProduto().getPreco().multiply(produto.getQuantidade()));
        }

        return CarrinhoRes.builder()
                .produtos(produtosMapped)
                .precoTotalCarrinho(ProdutoResMapper.formatarPreco(total))
                .build();
    }

    public ResponseEntity<Retorno> deletarProdutoCarrinho(@PathVariable Long itemCarrinhoId) throws CustomException {

        Carrinho carrinho = carrinhoUtils.findById(itemCarrinhoId);

        carrinhoRepository.delete(carrinho);

        log.info(" >>> Item do Carrinho deletado com sucesso.");
        return retornoService.retornoSucesso("Item do Carrinho deletado com sucesso.");
    }

    public ResponseEntity<Retorno> deletar(Jwt jwt) throws CustomException {

        User tutor = userUtils.findByJwt(jwt);

        List<Carrinho> produtosCarrinho = carrinhoRepository.findByTutor(tutor);

        carrinhoRepository.deleteAll(produtosCarrinho);

        log.info(" >>> Carrinho deletado com sucesso.");
        return retornoService.retornoSucesso("Carrinho deletado com sucesso.");
    }
}