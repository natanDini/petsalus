package br.com.petsalus.services;

import br.com.petsalus.dtos.request.RacaAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Especie;
import br.com.petsalus.entities.Raca;
import br.com.petsalus.repositories.RacaRepository;
import br.com.petsalus.utils.EspecieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RacaService {

    private final EspecieUtils especieUtils;
    private final RetornoService retornoService;

    private final RacaRepository racaRepository;

    public ResponseEntity<Retorno> registrar(RacaAdd racaAdd){

        Especie especie = especieUtils.findById(racaAdd.especieId());

        Raca raca = new Raca();

        raca.setEspecie(especie);
        raca.setNome(racaAdd.nome());

        racaRepository.save(raca);

        log.info("Raça registrada com sucesso.");
        return retornoService.retornoSucesso("Raça registrada com sucesso.");
    }

    public void salvarSRD(Especie especie) {

        Raca raca = new Raca();

        raca.setNome("Sem Raça Definida");
        raca.setEspecie(especie);

        racaRepository.save(raca);
    }
}