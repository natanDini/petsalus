package br.com.petsalus.services;

import br.com.petsalus.dtos.request.EspecieAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Especie;
import br.com.petsalus.repositories.EspecieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EspecieService {

    private final RacaService racaService;
    private final RetornoService retornoService;

    private final EspecieRepository especieRepository;

    public ResponseEntity<Retorno> registrar(EspecieAdd especieAdd) {

        Especie especie = salvar(especieAdd);

        racaService.salvarSRD(especie);

        log.info(" >>> Especie registrada com sucesso.");
        return retornoService.retornoSucesso("Especie registrada com sucesso.");
    }

    public Especie salvar(EspecieAdd especieAdd){

        Especie especie = new Especie();

        especie.setNome(especieAdd.nome());

        return especieRepository.save(especie);
    }
}