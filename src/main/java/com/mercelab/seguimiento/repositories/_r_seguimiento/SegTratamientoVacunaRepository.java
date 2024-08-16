package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.Estado;
import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoVacuna;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoVacunaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SegTratamientoVacunaRepository extends JpaRepository<SegTratamientoVacuna, Long> {

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoVacunaDto(tv) " +
            "FROM SegTratamientoVacuna tv " +
            "WHERE tv.tratamiento.id =:tratamientoId " +
            "AND tv.estadoId = " + Estado.ACTIVO)
    List<SegTratamientoVacunaDto> getVacunasAsignadas(Long tratamientoId);

    @Query("SELECT tv FROM SegTratamientoVacuna tv " +
            "WHERE tv.tratamiento.id =:tratamientoId AND tv.vacuna.id =:vacunaId " +
            "AND tv.estadoId = " + Estado.ACTIVO)
    Optional<SegTratamientoVacuna> existeTratamientoVacuna(Long tratamientoId, Long vacunaId);
}
