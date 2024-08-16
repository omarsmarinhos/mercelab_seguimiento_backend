package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.Estado;
import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoMedicamento;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoMedicamentoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface SegTratamientoMedicamentoRepository extends JpaRepository<SegTratamientoMedicamento, Long> {

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoMedicamentoDto(tm) " +
            "FROM SegTratamientoMedicamento tm " +
            "WHERE tm.tratamiento.id =:tratamientoId " +
            "AND tm.estadoId = 1")
    List<SegTratamientoMedicamentoDto> getMedicamentosAsignados(Long tratamientoId);

    @Query("SELECT tm FROM SegTratamientoMedicamento tm " +
            "WHERE tm.tratamiento.id =:tratamientoId AND tm.medicamentoId =:medicamentoId " +
            "AND tm.estadoId = " + Estado.ACTIVO)
    Optional<SegTratamientoMedicamento> existeTratamientoMedicamento(Long tratamientoId, Long medicamentoId);
}
