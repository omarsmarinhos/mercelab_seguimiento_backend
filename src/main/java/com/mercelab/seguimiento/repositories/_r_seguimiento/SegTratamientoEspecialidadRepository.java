package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.Estado;
import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoEspecialidad;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoEspecialidadDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SegTratamientoEspecialidadRepository extends JpaRepository<SegTratamientoEspecialidad, Long> {

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoEspecialidadDto(te) " +
            "FROM SegTratamientoEspecialidad te " +
            "WHERE te.tratamiento.id =:tratamientoId " +
            "AND te.estadoId = " + Estado.ACTIVO)
    List<SegTratamientoEspecialidadDto> getEspecialidadesAsignadas(Long tratamientoId);

    @Query("SELECT te FROM SegTratamientoEspecialidad te " +
            "WHERE te.tratamiento.id =:tratamientoId AND te.especialidad.id =:especialidadId " +
            "AND te.estadoId = " + Estado.ACTIVO)
    Optional<SegTratamientoEspecialidad> existeTratamientoEspecialidad(Long tratamientoId, Long especialidadId);
}
