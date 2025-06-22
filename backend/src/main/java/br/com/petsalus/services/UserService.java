package br.com.petsalus.services;

import br.com.petsalus.dtos.request.UserAdd;
import br.com.petsalus.dtos.request.UserEdit;
import br.com.petsalus.dtos.response.PerfilCompleto;
import br.com.petsalus.dtos.response.PerfilPequeno;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Endereco;
import br.com.petsalus.entities.User;
import br.com.petsalus.enums.UserRole;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.mappers.PerfilCompletoMapper;
import br.com.petsalus.mappers.PerfilPequenoMapper;
import br.com.petsalus.repositories.UserRepository;
import br.com.petsalus.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserUtils userUtils;

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

        log.info(" >>> User registrado com sucesso.");
        return retornoService.retornoSucesso("User registrado com sucesso.");
    }

    public String uploadFoto(MultipartFile foto, Jwt jwt)
            throws CustomException, IOException {

        User user = userUtils.findByJwt(jwt);

        user.setFotoPerfil(foto.getBytes());

        userRepository.save(user);

        String fotoBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(foto.getBytes());

        log.info(" >>> Foto registrada com sucesso.");
        return fotoBase64;
    }

    public PerfilPequeno perfilPequeno(Jwt jwt) throws CustomException {

        User user = userUtils.findByJwt(jwt);

        log.info(" >>> Retornando perfil pequeno com sucesso.");
        return PerfilPequenoMapper.map(user);
    }

    public PerfilCompleto perfilCompleto(@AuthenticationPrincipal Jwt jwt) throws CustomException {

        User user = userUtils.findByJwt(jwt);

        log.info(" >>> Retornando perfil completo com sucesso.");
        return PerfilCompletoMapper.map(user);
    }

    public ResponseEntity<Retorno> deletar(Jwt jwt) throws CustomException {

        User user = userUtils.findByJwt(jwt);

        userRepository.delete(user);

        log.info(" >>> User/Conta deletado com sucesso.");
        return retornoService.retornoSucesso("User deletado com sucesso.");
    }

    public ResponseEntity<Retorno> editar(Jwt jwt, UserEdit userEdit) throws CustomException {

        User user = userUtils.findByJwt(jwt);

        Endereco endereco = enderecoService.editar(user.getEndereco().getId(), userEdit.endereco());

        user.setEndereco(endereco);
        user.setNome(userEdit.nome());
        user.setEmail(userEdit.email());
        user.setTelefone(userEdit.telefone());

        userRepository.save(user);

        log.info(" >>> User editado com sucesso.");
        return retornoService.retornoSucesso("User editado com sucesso.");
    }
}