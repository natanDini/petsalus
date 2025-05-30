package br.com.petsalus.services;

import br.com.petsalus.dtos.request.UserAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Endereco;
import br.com.petsalus.entities.User;
import br.com.petsalus.enums.UserRole;
import br.com.petsalus.exceptions.ConflictException;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.repositories.UserRepository;
import br.com.petsalus.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserUtils userUtils;
    private final RetornoService retornoService;

    private final UserRepository userRepository;

    private final EnderecoService enderecoService;

    public ResponseEntity<Retorno> registrar(UserAdd userAdd) throws CustomException {

        validaCpfExistente(userAdd.cpf());
        validaEmailExistente(userAdd.email());
        validaTelefoneExistente(userAdd.telefone());

        Endereco endereco = enderecoService.salvar(userAdd.endereco());

        salvar(endereco, userAdd);

        log.info(" >>> User registrado com sucesso");
        return retornoService.retornoSucesso("User registrado com sucesso");
    }

    public User salvar(Endereco endereco, UserAdd userAdd){

        User user = new User();

        user.setFotoPerfil(null);
        user.setEndereco(endereco);
        user.setCpf(userAdd.cpf());
        user.setNome(userAdd.nome());
        user.setEmail(userAdd.email());
        user.setUsername(userAdd.username());
        user.setTelefone(userAdd.telefone());
        user.setUserRole(UserRole.valueOf(userAdd.userRole()));
        user.setSenha(passwordEncoder.encode(userAdd.senha()));

        return userRepository.save(user);
    }

    public void validaCpfExistente(String cpf){
        if(userRepository.existsByCpf(cpf)){
            throw new ConflictException("CPF informado já está em uso.");
        }
    }

    public void validaEmailExistente(String email){
        if(userRepository.existsByEmail(email)){
            throw new ConflictException("Email informado já está em uso.");
        }
    }

    public void validaTelefoneExistente(String telefone){
        if(userRepository.existsByTelefone(telefone)){
            throw new ConflictException("Telefone informado já está em uso.");
        }
    }
}