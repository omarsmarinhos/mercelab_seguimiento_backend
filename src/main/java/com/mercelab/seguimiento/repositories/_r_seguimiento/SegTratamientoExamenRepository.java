package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.Estado;
import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoExamen;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoExamenDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SegTratamientoExamenRepository extends JpaRepository<SegTratamientoExamen, Long> {

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoExamenDto(te) " +
            "FROM SegTratamientoExamen te " +
            "WHERE te.tratamiento.id =:tratamientoId " +
            "AND te.estadoId = 1")
    List<SegTratamientoExamenDto> getExamenesAsignados(@Param("tratamientoId") Long tratamientoId);

    @Query("SELECT te FROM SegTratamientoExamen te " +
            "WHERE te.tratamiento.id =:tratamientoId AND te.examen.id =:examenId " +
            "AND te.estadoId = " + Estado.ACTIVO)
    Optional<SegTratamientoExamen> existeTratamientoExamen(Long tratamientoId, Long examenId);
}
