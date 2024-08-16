package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegEnfermedad;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegDiagnosticoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SegDiagnosticoRepository extends JpaRepository<SegEnfermedad, Long> {

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SegDiagnosticoDto(" +
            "e, t, s) " +
            "FROM SegEnfermedad e " +
            "JOIN SegPaciente p ON e.paciente = p " +
            "LEFT JOIN SegTratamiento t on e.id = t.enfermedadId " +
            "LEFT JOIN SegSeguimiento s on t.id = s.tratamientoId " +
            "WHERE p.estadoId = 1 AND e.estadoId = 1 " +
            "AND (:termino IS NULL " +
            "OR p.codHistorialClinico LIKE %:termino% " +
            "OR p.persona.apellidoPaterno LIKE %:termino%) " +
            "AND (:esCronico IS NULL " +
            "OR e.esCronico =:esCronico)")
    Page<SegDiagnosticoDto> listarDiagnosticos(@Param("termino") String termino, Boolean esCronico, Pageable pageable);

}
