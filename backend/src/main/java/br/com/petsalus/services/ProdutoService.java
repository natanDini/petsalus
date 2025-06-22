package br.com.petsalus.services;

import br.com.petsalus.dtos.request.ProdutoAdd;
import br.com.petsalus.dtos.request.ProdutoEdit;
import br.com.petsalus.dtos.response.ProdutoResCliente;
import br.com.petsalus.dtos.response.ProdutoResEmpresa;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Carrinho;
import br.com.petsalus.entities.Empresa;
import br.com.petsalus.entities.Produto;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.mappers.ProdutoResClienteMapper;
import br.com.petsalus.mappers.ProdutoResEmpresaMapper;
import br.com.petsalus.repositories.ProdutoRepository;
import br.com.petsalus.utils.EmpresaUtils;
import br.com.petsalus.utils.ProdutoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoUtils produtoUtils;
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

    public String uploadFoto(Long produtoId, MultipartFile foto) throws CustomException, IOException {

        Produto produto = produtoUtils.findById(produtoId);

        produto.setFotoPerfil(foto.getBytes());

        produtoRepository.save(produto);

        String fotoBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(foto.getBytes());

        log.info("Foto de produto registrada com sucesso.");
        return fotoBase64;
    }

    public List<ProdutoResCliente>getByEmpresaIdClient(Long empresaId) throws CustomException {

        Empresa empresa = empresaUtils.findById(empresaId);

        List<Produto> produtos = produtoRepository.findByEmpresaAndDisponivelIsTrue(empresa);

        log.info(" >>> Retornando lista de Produtos de Empresa para Cliente com sucesso.");
        return ProdutoResClienteMapper.map(produtos);
    }

    public List<ProdutoResEmpresa> getByEmpresaId(Long empresaId) throws CustomException {

        Empresa empresa = empresaUtils.findById(empresaId);

        List<Produto> produtos = produtoRepository.findByEmpresa(empresa);

        log.info(" >>> Retornando lista de Produtos de Empresa com sucesso.");
        return ProdutoResEmpresaMapper.map(produtos);
    }

    public ResponseEntity<Retorno> addEstoque(Long produtoId, Long qtdAddEstoque) throws CustomException {

        Produto produto = produtoUtils.findById(produtoId);

        produto.setQtdEstoque(produto.getQtdEstoque().add(BigDecimal.valueOf(qtdAddEstoque)));

        produtoRepository.save(produto);

        log.info(" >>> Adicionando estoque com sucesso.");
        return retornoService.retornoSucesso("Adicionando estoque com sucesso.");
    }

    public ResponseEntity<Retorno> indisponibilizar(Long produtoId) throws CustomException {

        Produto produto = produtoUtils.findById(produtoId);

        produto.setDisponivel(false);

        produtoRepository.save(produto);

        log.info(" >>> Indisponibilizando produto com sucesso.");
        return retornoService.retornoSucesso("Indisponibilizando produto com sucesso.");
    }

    public ResponseEntity<Retorno> disponibilizar(Long produtoId) throws CustomException {

        Produto produto = produtoUtils.findById(produtoId);

        produto.setDisponivel(true);

        produtoRepository.save(produto);

        log.info(" >>> Disponibilizando produto com sucesso.");
        return retornoService.retornoSucesso("Disponibilizando produto com sucesso.");
    }

    public ResponseEntity<Retorno> deletar(Long produtoId) throws CustomException {

        Produto produto = produtoUtils.findById(produtoId);

        produtoRepository.delete(produto);

        log.info(" >>> Produto deletado com sucesso.");
        return retornoService.retornoSucesso("Produto deletado com sucesso.");
    }

    public ResponseEntity<Retorno> editar(Long produtoId, ProdutoEdit produtoEdit) throws CustomException {

        Produto produto = produtoUtils.findById(produtoId);

        produto.setNome(produtoEdit.nome());
        produto.setPreco(produtoEdit.preco());
        produto.setDisponivel(produtoEdit.disponivel());
        produto.setDescricao(produtoEdit.descricao());

        produtoRepository.save(produto);

        log.info(" >>> Produto editado com sucesso.");
        return retornoService.retornoSucesso("Produto editado com sucesso.");
    }

    public void atualizarProdutosVendidos(List<Carrinho> produtos) throws CustomException {

        for (Carrinho item : produtos) {

            Produto produto = item.getProduto();

            produto.setQtdVendida(produto.getQtdVendida().add(item.getQuantidade()));
            produto.setQtdEstoque(produto.getQtdEstoque().subtract(item.getQuantidade()));

            produtoRepository.save(produto);
        }
    }
}