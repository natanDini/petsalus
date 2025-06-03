package br.com.petsalus.services;

import br.com.petsalus.dtos.request.EmpresaAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Empresa;
import br.com.petsalus.entities.Endereco;
import br.com.petsalus.entities.User;
import br.com.petsalus.enums.ModeloComercial;
import br.com.petsalus.enums.UserRole;
import br.com.petsalus.exceptions.BadRequestException;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.repositories.EmpresaRepository;
import br.com.petsalus.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final UserUtils userUtils;

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
        empresa.setTrabalhaVinteQuatroHoras(empresaAdd.trabalhaVinteQuatroHoras());
        empresa.setModeloComercial(ModeloComercial.valueOf(empresaAdd.modeloComercial()));

        empresaRepository.save(empresa);

        log.info(" >>> Empresa registrada com sucesso.");
        return retornoService.retornoSucesso("Empresa registrada com sucesso.");
    }

    public void isUserRoleValid(UserRole role){
        if (!role.equals(UserRole.DONO)){
            throw new BadRequestException("Para cadastrar uma empresa é necessário ser um usuário DONO.");
        }
    }
}