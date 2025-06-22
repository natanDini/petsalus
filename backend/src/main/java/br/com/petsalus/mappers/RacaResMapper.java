package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.RacaRes;
import br.com.petsalus.entities.Raca;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RacaResMapper {

    public RacaRes map(Raca raca) {

        return RacaRes.builder()
                .id(raca.getId())
                .nome(raca.getNome())
                .build();
    }

    public static List<RacaRes> map(List<Raca> racas) {
        return racas.stream()
                .map(RacaResMapper::map)
                .collect(Collectors.toList());
    }
}