package br.com.petsalus.repositories;

import br.com.petsalus.entities.Empresa;
import br.com.petsalus.enums.ModeloComercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    List<Empresa> findAllByAndModeloComercialIn(List<ModeloComercial> modelos);

    List<Empresa> findAllByTrabalhaVinteQuatroHorasIsTrueAndModeloComercialIn(List<ModeloComercial> modelos);
}