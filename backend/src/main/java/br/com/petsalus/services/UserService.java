package br.com.petsalus.services;

import br.com.petsalus.dtos.request.UserAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Endereco;
import br.com.petsalus.entities.User;
import br.com.petsalus.enums.UserRole;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.repositories.UserRepository;
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

    private final RetornoService retornoService;
    private final EnderecoService enderecoService;

    private final UserRepository userRepository;

    public ResponseEntity<Retorno> registrar(UserAdd userAdd) throws CustomException {

        Endereco endereco = enderecoService.salvar(userAdd.endereco());

        User user = new User();

        user.setEndereco(endereco);
        user.setCpf(userAdd.cpf());
        user.setNome(userAdd.nome());
        user.setEmail(userAdd.email());
        user.setUsername(userAdd.username());
        user.setTelefone(userAdd.telefone());
        user.setUserRole(UserRole.valueOf(userAdd.userRole()));
        user.setSenha(passwordEncoder.encode(userAdd.senha()));

        userRepository.save(user);

        log.info(" >>> User registrado com sucesso");
        return retornoService.retornoSucesso("User registrado com sucesso");
    }
}