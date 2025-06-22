package br.com.petsalus.repositories;

import br.com.petsalus.entities.Empresa;
import br.com.petsalus.entities.EmpresaEmpregado;
import br.com.petsalus.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaEmpregadoRepository extends JpaRepository<EmpresaEmpregado, Long> {

    @Query("""
    SELECT DISTINCT ee.empregado
    FROM EmpresaEmpregado ee
    WHERE ee.empresa = :empresa
    """)
    List<User> findEmpregadosByEmpresa(@Param("empresa") Empresa empresa);

    @Query("""
    SELECT DISTINCT ee.empregado
    FROM EmpresaEmpregado ee
    WHERE ee.empresa = :empresa
    AND ee.empregado.userRole = "COLABORADOR"
    """)
    List<User> findColaboradoresByEmpresa(@Param("empresa") Empresa empresa);

    @Query("""
    SELECT DISTINCT ee.empregado
    FROM EmpresaEmpregado ee
    WHERE ee.empresa = :empresa
    AND ee.empregado.userRole = "VETERINARIO"
    """)
    List<User> findVeterinariosByEmpresa(@Param("empresa") Empresa empresa);

    boolean existsByEmpregado(User u);

    boolean existsByEmpresaAndEmpregado(Empresa e, User u);

    List<EmpresaEmpregado> findAllByEmpresa(Empresa empresa);
}