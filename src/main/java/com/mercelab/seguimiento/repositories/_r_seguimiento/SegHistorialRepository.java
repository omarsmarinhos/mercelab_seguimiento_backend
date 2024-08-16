package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegHistorial;
import com.mercelab.seguimiento.models._m_seguimiento.Seguimiento;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegReporteHistorialDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegReportePacienteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SegHistorialRepository extends JpaRepository<SegHistorial, Long> {

    Optional<SegHistorial> getSegHistorialByElementoIdAndElementoTipoNombre(Long elementoId, String tipoNombre);

    @Query("SELECT distinct new com.mercelab.seguimiento.models._m_seguimiento.dto.SegReportePacienteDto(" +
            "pa.codHistorialClinico, pa.persona.apellidoPaterno, pa.persona.apellidoMaterno, pa.persona.nombres," +
            "pa.id) " +
            "from SegHistorial h " +
            "join SegSeguimiento s on s.id = h.segId " +
            "join SegPaciente pa on pa.id = s.pacienteId " +
            "where h.estadoId != 1 and pa.estadoId = 1" +
            "AND (:termino IS NULL " +
            "OR pa.codHistorialClinico LIKE %:termino% " +
            "OR pa.persona.apellidoPaterno LIKE %:termino%) " +
            "group by h.segId")
    Page<SegReportePacienteDto> getPacientesConSeguimientoConcluido(String termino, Pageable pageable);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SegReporteHistorialDto(" +
            "'Examen', se.tratamientoExamen.examen.descripcion, se.observacion, se.respuesta, " +
            "h.fechaInicio, h.fechaFin, e) " +
            "FROM SegEnfermedad e " +
            "JOIN SegTratamiento t ON e.id = t.enfermedadId " +
            "JOIN SegSeguimiento s ON s.tratamientoId = t.id " +
            "JOIN SegHistorial h on s.id = h.segId " +
            "JOIN SegSeguimientoExamen se ON se.id = h.elementoId " +
            "WHERE h.estadoId != 4 and s.pacienteId = :pacienteId AND h.elementoTipoNombre = 'Examen'")
    List<SegReporteHistorialDto> getSeguimientosDeExamenesConcluidos(Long pacienteId);


    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SegReporteHistorialDto(" +
            "'Medicamento', sm.tratamientoMedicamento.medicamentoId, sm.observacion, sm.respuesta, " +
            "h.fechaInicio, h.fechaFin, e) " +
            "FROM SegEnfermedad e " +
            "JOIN SegTratamiento t ON e.id = t.enfermedadId " +
            "JOIN SegSeguimiento s ON s.tratamientoId = t.id " +
            "JOIN SegHistorial h on s.id = h.segId " +
            "join SegSeguimientoMedicamento sm on sm.id = h.elementoId " +
            "WHERE h.estadoId != 4 and s.pacienteId =:pacienteId and h.elementoTipoNombre = 'Medicamento'")
    List<SegReporteHistorialDto> getSeguimientosDeMedicamentosConcluidos(Long pacienteId);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SegReporteHistorialDto(" +
            "'Vacuna', sv.tratamientoVacuna.vacuna.descripcion, sv.observacion, sv.respuesta, " +
            "h.fechaInicio, h.fechaFin, e) " +
            "FROM SegEnfermedad e " +
            "JOIN SegTratamiento t ON e.id = t.enfermedadId " +
            "JOIN SegSeguimiento s ON s.tratamientoId = t.id " +
            "JOIN SegHistorial h on s.id = h.segId " +
            "JOIN SegSeguimientoVacuna sv ON sv.id = h.elementoId " +
            "WHERE h.estadoId != 4 and s.pacienteId = :pacienteId AND h.elementoTipoNombre = 'Vacuna'")
    List<SegReporteHistorialDto> getSeguimientosDeVacunasConcluidos(Long pacienteId);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SegReporteHistorialDto(" +
            "'Especialidad', se.tratamientoEspecialidad.especialidad.descripcion, se.observacion, se.respuesta, " +
            "h.fechaInicio, h.fechaFin, e) " +
            "FROM SegEnfermedad e " +
            "JOIN SegTratamiento t ON e.id = t.enfermedadId " +
            "JOIN SegSeguimiento s ON s.tratamientoId = t.id " +
            "JOIN SegHistorial h on s.id = h.segId " +
            "JOIN SegSeguimientoEspecialidad se ON se.id = h.elementoId " +
            "WHERE h.estadoId != 4 AND s.pacienteId = :pacienteId AND h.elementoTipoNombre = 'Especialidad'")
    List<SegReporteHistorialDto> getSeguimientosDeEspecialidadesConcluidos(Long pacienteId);

}
