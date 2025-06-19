package br.com.petsalus.services;

import br.com.petsalus.dtos.request.PetAdd;
import br.com.petsalus.dtos.response.PetRes;
import br.com.petsalus.dtos.response.PetShortRes;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Especie;
import br.com.petsalus.entities.Pet;
import br.com.petsalus.entities.Raca;
import br.com.petsalus.entities.User;
import br.com.petsalus.enums.Sexo;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.mappers.PetResMapper;
import br.com.petsalus.mappers.PetShortResMapper;
import br.com.petsalus.repositories.PetRepository;
import br.com.petsalus.utils.*;
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
public class PetService {

    private final PetUtils petUtils;
    private final RacaUtils racaUtils;
    private final UserUtils userUtils;
    private final EmptyUtils emptyUtils;
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

    public PetRes getById(Long petId) throws CustomException {

        Pet pet = petUtils.findById(petId);

        log.info(" >>> Retornando pet com sucesso.");
        return PetResMapper.map(pet);
    }

    public List<PetShortRes> myPets(Jwt jwt) throws CustomException {

        User user = userUtils.findByJwt(jwt);

        List<Pet> pets = petRepository.findByTutor(user);

        emptyUtils.validaListaVazia(pets, "Não foram encontrados pets cadastrados para esse usuário.");

        log.info(" >>> Retornando lista de pets de usuário com sucesso.");
        return PetShortResMapper.map(pets);
    }

    public List<PetShortRes> petsByTutor(String cpf) throws CustomException {

        User user = userUtils.findByCpf(cpf);

        List<Pet> pets = petRepository.findByTutor(user);

        emptyUtils.validaListaVazia(pets, "Não foram encontrados pets cadastrados para esse usuário.");

        log.info(" >>> Retornando lista de pets de tutor com sucesso.");
        return PetShortResMapper.map(pets);
    }

    public ResponseEntity<Retorno> deletar(Long petId) throws CustomException {

        Pet pet = petUtils.findById(petId);

        petRepository.delete(pet);

        log.info(" >>> Pet removido com sucesso.");
        return retornoService.retornoSucesso("Pet removido com sucesso.");
    }

    public ResponseEntity<Retorno> editar(Long petId, PetAdd petAdd) throws CustomException {

        Raca raca = racaUtils.findById(petAdd.racaId());
        Especie especie = especieUtils.findById(petAdd.especieId());

        Pet pet = petUtils.findById(petId);

        pet.setRaca(raca);
        pet.setEspecie(especie);
        pet.setNome(petAdd.nome());
        pet.setPeso(petAdd.peso());
        pet.setIdade(petAdd.idade());
        pet.setSexo(Sexo.valueOf(petAdd.sexo()));

        petRepository.save(pet);

        log.info("Pet editado com sucesso.");
        return retornoService.retornoSucesso("Pet editado com sucesso.");
    }
}