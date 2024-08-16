package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegEnfermedad;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegEnfermedadDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SegEnfermedadRepository extends JpaRepository<SegEnfermedad, Long> {

    @Query("SELECT e FROM SegEnfermedad e " +
            "WHERE e.paciente.id =:pacienteId AND e.cie10.id =:cie10Id")
    Optional<SegEnfermedad> existePacienteYEnfermedadCie10(Long pacienteId, Long cie10Id);
}
