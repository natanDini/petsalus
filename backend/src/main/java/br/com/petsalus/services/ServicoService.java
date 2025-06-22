package br.com.petsalus.services;

import br.com.petsalus.dtos.request.ServicoAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.dtos.response.ServicoRes;
import br.com.petsalus.entities.Empresa;
import br.com.petsalus.entities.Servico;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.mappers.ServicoResMapper;
import br.com.petsalus.repositories.ServicoRepository;
import br.com.petsalus.utils.EmpresaUtils;
import br.com.petsalus.utils.EmptyUtils;
import br.com.petsalus.utils.ServicoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicoService {

    private final EmptyUtils emptyUtils;
    private final ServicoUtils servicoUtils;
    private final EmpresaUtils empresaUtils;

    private final RetornoService retornoService;

    private final ServicoRepository servicoRepository;

    public ResponseEntity<Retorno> registrar(Long empresaId, ServicoAdd servicoAdd) throws CustomException {

        Empresa empresa = empresaUtils.findById(empresaId);

        Servico servico = new Servico();

        servico.setEmpresa(empresa);
        servico.setNome(servicoAdd.nome());
        servico.setPreco(servicoAdd.preco());
        servico.setPublico(servicoAdd.isPublico());
        servico.setDescricao(servicoAdd.descricao());
        servico.setTipoServico(servicoAdd.tipoServico());
        servico.setTempoServicoMin(servicoAdd.tempoServicoMin());

        servicoRepository.save(servico);

        log.info(" >>> Servico registrado com sucesso");
        return retornoService.retornoSucesso("Servico registrado com sucesso");
    }

    public List<ServicoRes> getServicosByEmpresaId(@PathVariable Long empresaId) throws CustomException {

        Empresa empresa = empresaUtils.findById(empresaId);

        List<Servico> servicos = servicoRepository.findByEmpresaOrderByNomeAsc(empresa);

        emptyUtils.validaListaVazia(servicos, "Nenhum serviço encontrado para essa empresa.");

        log.info(" >>> Servicos retornados com sucesso");
        return ServicoResMapper.map(servicos);
    }

    public List<ServicoRes> getServicosPublicosByEmpresaId(Long empresaId) throws CustomException {

        Empresa empresa = empresaUtils.findById(empresaId);

        List<Servico> servicos = servicoRepository.findByEmpresaAndIsPublicoIsTrue(empresa);

        emptyUtils.validaListaVazia(servicos, "Nenhum serviço público encontrado para essa empresa.");

        log.info(" >>> Servicos públicos retornados com sucesso");
        return ServicoResMapper.map(servicos);
    }

    public ResponseEntity<Retorno> deletar(Long servicoId) throws CustomException {

        Servico servico = servicoUtils.findById(servicoId);

        servicoRepository.delete(servico);

        log.info(" >>> Serviço removido com sucesso.");
        return retornoService.retornoSucesso("Serviço removido com sucesso.");
    }

    public ResponseEntity<Retorno> editar(Long servicoId, ServicoAdd servicoAdd) throws CustomException {

        Servico servico = servicoUtils.findById(servicoId);

        servico.setNome(servicoAdd.nome());
        servico.setPreco(servicoAdd.preco());
        servico.setPublico(servicoAdd.isPublico());
        servico.setDescricao(servicoAdd.descricao());
        servico.setTipoServico(servicoAdd.tipoServico());
        servico.setTempoServicoMin(servicoAdd.tempoServicoMin());

        servicoRepository.save(servico);

        log.info(" >>> Servico editado com sucesso");
        return retornoService.retornoSucesso("Servico editado com sucesso");
    }
}