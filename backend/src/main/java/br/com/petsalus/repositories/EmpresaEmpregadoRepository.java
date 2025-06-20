package br.com.petsalus.repositories;

import br.com.petsalus.entities.Empresa;
import br.com.petsalus.entities.EmpresaEmpregado;
import br.com.petsalus.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaEmpregadoRepository extends JpaRepository<EmpresaEmpregado, Long> {

    boolean existsByEmpresaAndEmpregado(Empresa e, User u);

    List<EmpresaEmpregado> findAllByEmpresa(Empresa empresa);
}