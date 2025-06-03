package br.com.petsalus.services;

import br.com.petsalus.dtos.request.Login;
import br.com.petsalus.dtos.response.LoginRes;
import br.com.petsalus.entities.User;
import br.com.petsalus.exceptions.UnauthorizedException;
import br.com.petsalus.security.JwtService;
import br.com.petsalus.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;

    private final UserUtils userUtils;

    private final JwtService jwtService;

    public ResponseEntity<LoginRes> login(Login login) {
       try {
           authManager.authenticate(new UsernamePasswordAuthenticationToken(login.username(), login.senha()));

           User user = userUtils.findByUsername(login.username());
           String userRole = user.getUserRole().name();

           String token = jwtService.generateToken(login.username(), userRole);

           LoginRes loginRes = LoginRes.builder()
                   .token(token)
                   .userRole(userRole)
                   .build();

           log.info(" >>> Login realizado com sucesso.");
           return ResponseEntity.ok(loginRes);
       } catch (BadCredentialsException ex){
           throw new UnauthorizedException("Credenciais inválidas.");
       }
    }
}