package br.com.petsalus.services;

import br.com.petsalus.dtos.request.EspecieAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Especie;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.repositories.EspecieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EspecieService {

    private final RacaService racaService;
    private final RetornoService retornoService;

    private final EspecieRepository especieRepository;

    public ResponseEntity<Retorno> registrar(EspecieAdd especieAdd) {

        Especie especie = new Especie();

        especie.setNome(especieAdd.nome());

        especieRepository.save(especie);

        racaService.salvarSRD(especie);

        log.info(" >>> Especie registrada com sucesso.");
        return retornoService.retornoSucesso("Especie registrada com sucesso.");
    }

    public List<Especie> getAll() throws CustomException {

        List<Especie> especies = especieRepository.findAll();

        log.info(" >>> Retornando lista de Especie com sucesso.");
        return especies;
    }
}