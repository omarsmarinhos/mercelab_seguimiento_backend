package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegPaciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SegPacienteRepository extends JpaRepository<SegPaciente, Long> {

    @Query("SELECT p FROM SegPaciente p WHERE p.estadoId = 1 " +
            "AND (:termino is null " +
            "or p.codHistorialClinico LIKE %:termino% " +
            "or p.persona.apellidoPaterno LIKE %:termino% " +
            "or p.persona.numeroDocumento LIKE %:termino%)")
    Page<SegPaciente> paginarYBuscar(String termino, Pageable pageable);
    @Query("SELECT COUNT(p) " +
            "FROM SegPaciente p " +
            "WHERE p.estadoId = 1")
    Long countPacientes();

    @Query("SELECT p FROM SegPaciente p " +
            "WHERE p.persona.numeroDocumento =:dni " +
            "AND p.estadoId = 1")
    Optional<SegPaciente> findSegPacienteByNumeroDocumento(String dni);

}
