package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.PetRes;
import br.com.petsalus.entities.Pet;
import lombok.experimental.UtilityClass;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class PetResMapper {

    public PetRes map(Pet pet) {

        String fotoBase64 = pet.getFotoPerfil() != null
                ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(pet.getFotoPerfil())
                : null;

        return PetRes.builder()
                .id(pet.getId())
                .peso(pet.getPeso())
                .nome(pet.getNome())
                .idade(pet.getIdade())
                .sexo(pet.getSexo().getDescricao())
                .raca(pet.getRaca().getNome())
                .especie(pet.getEspecie().getNome())
                .fotoPerfil(fotoBase64)
                .build();
    }

    public static List<PetRes> map(List<Pet> pets) {
        return pets.stream()
                .map(PetResMapper::map)
                .collect(Collectors.toList());
    }
}