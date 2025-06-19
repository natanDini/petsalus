package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.PetShortRes;
import br.com.petsalus.entities.Pet;
import lombok.experimental.UtilityClass;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class PetShortResMapper {

    public PetShortRes map(Pet pet) {

        String fotoBase64 = pet.getFotoPerfil() != null
                ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(pet.getFotoPerfil())
                : null;

        return PetShortRes.builder()
                .id(pet.getId())
                .nome(pet.getNome())
                .fotoPerfil(fotoBase64)
                .build();
    }

    public static List<PetShortRes> map(List<Pet> pets) {
        return pets.stream()
                .map(PetShortResMapper::map)
                .collect(Collectors.toList());
    }
}