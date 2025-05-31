package br.com.petsalus.services;

import br.com.petsalus.dtos.request.PetAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Especie;
import br.com.petsalus.entities.Pet;
import br.com.petsalus.entities.Raca;
import br.com.petsalus.entities.User;
import br.com.petsalus.enums.Sexo;
import br.com.petsalus.repositories.PetRepository;
import br.com.petsalus.utils.EspecieUtils;
import br.com.petsalus.utils.RacaUtils;
import br.com.petsalus.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService {

    private final RacaUtils racaUtils;
    private final UserUtils userUtils;
    private final EspecieUtils especieUtils;
    private final RetornoService retornoService;

    private final PetRepository petRepository;

    public ResponseEntity<Retorno> resgistrar(PetAdd petAdd, Jwt jwt){

        String username = jwt.getSubject();
        User user = userUtils.findByUsername(username);

        Raca raca = racaUtils.findById(petAdd.racaId());
        Especie especie = especieUtils.findById(petAdd.especieId());

        Pet pet = new Pet();

        pet.setUser(user);
        pet.setRaca(raca);
        pet.setEspecie(especie);
        pet.setFotoPerfil(null);
        pet.setNome(petAdd.nome());
        pet.setPeso(petAdd.peso());
        pet.setIdade(petAdd.idade());
        pet.setSexo(Sexo.valueOf(petAdd.sexo()));

        petRepository.save(pet);

        log.info("Pet registrado com sucesso.");
        return retornoService.retornoSucesso("Pet registrado com sucesso.");
    }
}