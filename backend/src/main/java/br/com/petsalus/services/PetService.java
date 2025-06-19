package br.com.petsalus.services;

import br.com.petsalus.dtos.request.PetAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Especie;
import br.com.petsalus.entities.Pet;
import br.com.petsalus.entities.Raca;
import br.com.petsalus.entities.User;
import br.com.petsalus.enums.Sexo;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.repositories.PetRepository;
import br.com.petsalus.utils.EspecieUtils;
import br.com.petsalus.utils.PetUtils;
import br.com.petsalus.utils.RacaUtils;
import br.com.petsalus.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService {

    private final PetUtils petUtils;
    private final RacaUtils racaUtils;
    private final UserUtils userUtils;
    private final EspecieUtils especieUtils;
    private final RetornoService retornoService;

    private final PetRepository petRepository;

    public ResponseEntity<Retorno> resgistrar(PetAdd petAdd, Jwt jwt){

        User user = userUtils.findByJwt(jwt);

        Raca raca = racaUtils.findById(petAdd.racaId());
        Especie especie = especieUtils.findById(petAdd.especieId());

        Pet pet = new Pet();

        pet.setRaca(raca);
        pet.setTutor(user);
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

    public ResponseEntity<Retorno> uploadFoto(Long petId, MultipartFile foto) throws CustomException, IOException {

        Pet pet = petUtils.findById(petId);

        pet.setFotoPerfil(foto.getBytes());

        petRepository.save(pet);

        log.info("Foto de pet registrada com sucesso.");
        return retornoService.retornoSucesso("Foto de pet registrada com sucesso.");
    }
}