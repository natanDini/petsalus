package br.com.petsalus.services;

import br.com.petsalus.dtos.request.PetAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Pet;
import br.com.petsalus.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public ResponseEntity<Retorno> resgistrar(PetAdd petAdd){


    }
}