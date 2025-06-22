package br.com.petsalus.services;

import br.com.petsalus.dtos.request.ProdutoAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Empresa;
import br.com.petsalus.entities.Produto;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.repositories.ProdutoRepository;
import br.com.petsalus.utils.EmpresaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final EmpresaUtils empresaUtils;

    private final RetornoService retornoService;

    private final ProdutoRepository produtoRepository;

    public ResponseEntity<Retorno> registrar(Long empresaId, ProdutoAdd produtoAdd) throws CustomException {

        Empresa empresa = empresaUtils.findById(empresaId);

        Produto produto = new Produto();

        produto.setNome(produtoAdd.nome());
        produto.setPreco(produtoAdd.preco());
        produto.setDisponivel(produtoAdd.disponivel());
        produto.setQtdEstoque(produtoAdd.qtdEstoque());
        produto.setQtdVendida(BigDecimal.ZERO);
        produto.setDescricao(produtoAdd.descricao());
        produto.setFotoPerfil(null);
        produto.setEmpresa(empresa);

        produtoRepository.save(produto);

        log.info(" >>> Produto registrado com sucesso.");
        return retornoService.retornoSucesso("Produto registrado com sucesso.");
    }
}