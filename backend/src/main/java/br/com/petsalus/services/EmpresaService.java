package br.com.petsalus.services;

import br.com.petsalus.dtos.request.EmpresaAdd;
import br.com.petsalus.dtos.response.EmpresaRes;
import br.com.petsalus.dtos.response.EmpresaShortRes;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Empresa;
import br.com.petsalus.entities.Endereco;
import br.com.petsalus.entities.User;
import br.com.petsalus.enums.ModeloComercial;
import br.com.petsalus.enums.UserRole;
import br.com.petsalus.exceptions.BadRequestException;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.mappers.EmpresaResMapper;
import br.com.petsalus.mappers.EmpresaShortResMapper;
import br.com.petsalus.repositories.EmpresaRepository;
import br.com.petsalus.utils.EmpresaUtils;
import br.com.petsalus.utils.EmptyUtils;
import br.com.petsalus.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final UserUtils userUtils;
    private final EmptyUtils emptyUtils;
    private final EmpresaUtils empresaUtils;

    private final RetornoService retornoService;
    private final EnderecoService enderecoService;

    private final EmpresaRepository empresaRepository;

    public ResponseEntity<Retorno> registrar(EmpresaAdd empresaAdd, Jwt jwt) throws CustomException {

        User user = userUtils.findByJwt(jwt);
        isUserRoleValid(user.getUserRole());

        Endereco endereco = enderecoService.salvar(empresaAdd.endereco());

        Empresa empresa = new Empresa();

        empresa.setDono(user);
        empresa.setEndereco(endereco);
        empresa.setNome(empresaAdd.nome());
        empresa.setCnpj(empresaAdd.cnpj());
        empresa.setEmail(empresaAdd.email());
        empresa.setDescricao(empresaAdd.descricao());
        empresa.setTelefone(empresaAdd.telefone());
        empresa.setHoraAbertura(empresaAdd.horaAbertura());
        empresa.setHoraEncerramento(empresaAdd.horaEncerramento());
        empresa.setTrabalhaVinteQuatroHoras(empresaAdd.trabalhaVinteQuatroHoras());
        empresa.setModeloComercial(ModeloComercial.valueOf(empresaAdd.modeloComercial()));

        empresaRepository.save(empresa);

        log.info(" >>> Empresa registrada com sucesso.");
        return retornoService.retornoSucesso("Empresa registrada com sucesso.");
    }

    public ResponseEntity<Retorno> uploadFoto(Long empresaId, MultipartFile foto) throws CustomException, IOException {

        Empresa empresa = empresaUtils.findById(empresaId);

        empresa.setFotoPerfil(foto.getBytes());

        empresaRepository.save(empresa);

        log.info(" >>> Foto de empresa registrada com sucesso.");
        return retornoService.retornoSucesso("Foto de empresa registrada com sucesso.");
    }

    public List<EmpresaShortRes> minhasEmpresas(Jwt jwt) throws CustomException {

        User dono = userUtils.findByJwt(jwt);

        List<Empresa> empresas = empresaRepository.findByDono(dono);

        emptyUtils.validaListaVazia(empresas, "Nenhuma empresa encontrada.");

        log.info(" >>> Empresas de Dono listadas com sucesso.");
        return EmpresaShortResMapper.map(empresas);
    }

    public List<EmpresaShortRes> listar() throws CustomException {

        List<Empresa> empresas = empresaRepository.findAll();

        emptyUtils.validaListaVazia(empresas, "Nenhuma empresa encontrada.");

        log.info(" >>> Empresas listadas com sucesso.");
        return EmpresaShortResMapper.map(empresas);
    }

    public EmpresaRes getById(Long empresaId) throws CustomException {

        Empresa empresa = empresaUtils.findById(empresaId);

        log.info(" >>> Retornando empresa com sucesso.");
        return EmpresaResMapper.map(empresa);
    }

    public List<EmpresaShortRes> clinicas() throws CustomException {

        List<ModeloComercial> modelosAceitos = List.of(
                ModeloComercial.CLINICA,
                ModeloComercial.CLINICA_E_PET_SHOP
        );

        List<Empresa> empresas = empresaRepository.findAllByAndModeloComercialIn(modelosAceitos);

        emptyUtils.validaListaVazia(empresas, "Nenhuma clinica encontrada.");

        log.info(" >>> Clinicas listadas com sucesso.");
        return EmpresaShortResMapper.map(empresas);
    }

    public List<EmpresaShortRes> petShops() throws CustomException {

        List<ModeloComercial> modelosAceitos = List.of(
                ModeloComercial.PET_SHOP,
                ModeloComercial.CLINICA_E_PET_SHOP
        );

        List<Empresa> empresas = empresaRepository.findAllByAndModeloComercialIn(modelosAceitos);

        emptyUtils.validaListaVazia(empresas, "Nenhum PetShop encontrado.");

        log.info(" >>> PetShops listados com sucesso.");
        return EmpresaShortResMapper.map(empresas);
    }

    public List<EmpresaShortRes> emergencias() throws CustomException {

        List<ModeloComercial> modelosAceitos = List.of(
                ModeloComercial.CLINICA,
                ModeloComercial.CLINICA_E_PET_SHOP
        );

        List<Empresa> empresas = empresaRepository
                .findAllByTrabalhaVinteQuatroHorasIsTrueAndModeloComercialIn(modelosAceitos);

        emptyUtils.validaListaVazia(empresas, "Nenhuma clinica de emergência encontrada.");

        log.info(" >>> Clinicas de emergência listadas com sucesso.");
        return EmpresaShortResMapper.map(empresas);
    }

    public ResponseEntity<Retorno> deletar(Long empresaId) throws CustomException {

        Empresa empresa = empresaUtils.findById(empresaId);

        empresaRepository.delete(empresa);

        log.info(" >>> Empresa removida com sucesso.");
        return retornoService.retornoSucesso("Empresa removida com sucesso.");
    }

    public ResponseEntity<Retorno> editar(Long empresaId, EmpresaAdd empresaAdd) throws CustomException {

        Endereco endereco = enderecoService.salvar(empresaAdd.endereco());

        Empresa empresa = empresaUtils.findById(empresaId);

        empresa.setEndereco(endereco);
        empresa.setNome(empresaAdd.nome());
        empresa.setCnpj(empresaAdd.cnpj());
        empresa.setEmail(empresaAdd.email());
        empresa.setDescricao(empresaAdd.descricao());
        empresa.setTelefone(empresaAdd.telefone());
        empresa.setHoraAbertura(empresaAdd.horaAbertura());
        empresa.setHoraEncerramento(empresaAdd.horaEncerramento());
        empresa.setTrabalhaVinteQuatroHoras(empresaAdd.trabalhaVinteQuatroHoras());
        empresa.setModeloComercial(ModeloComercial.valueOf(empresaAdd.modeloComercial()));

        empresaRepository.save(empresa);

        log.info(" >>> Empresa editada com sucesso.");
        return retornoService.retornoSucesso("Empresa editada com sucesso.");
    }

    public void isUserRoleValid(UserRole role){
        if (!role.equals(UserRole.DONO)){
            throw new BadRequestException("Para cadastrar uma empresa é necessário ser um usuário DONO.");
        }
    }
}