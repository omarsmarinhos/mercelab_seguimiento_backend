package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimientoMedicamento;
import com.mercelab.seguimiento.models._m_seguimiento.dto.NotificarDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SegSeguimientoMedicamentoRepository extends JpaRepository<SegSeguimientoMedicamento, Long> {

    @Query("SELECT sm, tm FROM SegSeguimientoMedicamento sm " +
            "JOIN sm.tratamientoMedicamento tm " +
            "WHERE sm.seguimiento.id = :seguimientoId " +
            "AND sm.estadoId = 1 " +
            "AND sm.tratamientoMedicamento.estadoId = 1 " +
            "GROUP BY sm.tratamientoMedicamento.id")
    List<SegSeguimientoMedicamento> getTratamientosMedicamentosUnicosAsignados(Long seguimientoId);

    @Query("SELECT sm FROM SegSeguimientoMedicamento sm " +
            "WHERE sm.seguimiento.id =:seguimientoId " +
            "AND sm.tratamientoMedicamento.id =:tratamientoMedicamentoId " +
            "AND sm.estadoId = 1 " +
            "ORDER BY sm.fechaProgramada asc ")
    List<SegSeguimientoMedicamento> getTratamientosMedicamentosAsignados(Long seguimientoId, Long tratamientoMedicamentoId);
    @Query("SELECT sm FROM SegSeguimientoMedicamento sm " +
            "WHERE sm.seguimiento.id =:seguimientoId " +
            "AND sm.tratamientoMedicamento.id =:tratamientoMedicamentoId " +
            "AND sm.estadoId = 1 " +
            "AND sm.fechaProgramada =:fecha")
    Optional<SegSeguimientoMedicamento> existeFechaProgramada(Long seguimientoId
            , Long tratamientoMedicamentoId, LocalDate fecha);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto(" +
            "p.codHistorialClinico, p.persona.apellidoPaterno, p.persona.apellidoMaterno, p.persona.nombres, " +
            "sm.seguimiento.id, sm.id, tm.medicamentoId, tm.id, " +
            "sm.tipoNotificacion, sm.estadoId, sm.fechaProgramada, 'Medicamento', e) " +
            "FROM SegSeguimientoMedicamento sm " +
            "JOIN SegPaciente p ON p.id = sm.seguimiento.pacienteId " +
            "JOIN SegTratamientoMedicamento tm ON tm = sm.tratamientoMedicamento " +
            "JOIN SegTratamiento t ON t = tm.tratamiento " +
            "JOIN SegEnfermedad e ON e.id = t.enfermedadId " +
            "WHERE sm.estadoId = 1 and p.estadoId = 1 " +
            " AND (:termino IS NULL " +
            "OR p.persona.apellidoPaterno LIKE %:termino%) " +
            " AND (:fechaDesde IS NULL OR sm.fechaProgramada >= :fechaDesde) " +
            "AND (:fechaHasta IS NULL OR sm.fechaProgramada <= :fechaHasta) " +
            "ORDER BY sm.fechaProgramada")
    Page<SeguimientosDto> getTotalPaginado(
            LocalDate fechaDesde,
            LocalDate fechaHasta,
            String termino,
            Pageable pageable
    );

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto(" +
            "p.codHistorialClinico, p.persona.apellidoPaterno, p.persona.apellidoMaterno, p.persona.nombres, " +
            "sm.seguimiento.id, sm.id, tm.medicamentoId, tm.id, " +
            "sm.tipoNotificacion, sm.estadoId, sm.fechaProgramada, 'Medicamento', e) " +
            "FROM SegSeguimientoMedicamento sm " +
            "JOIN SegPaciente p ON p.id = sm.seguimiento.pacienteId " +
            "JOIN SegTratamientoMedicamento tm ON tm = sm.tratamientoMedicamento " +
            "JOIN SegTratamiento t ON t = tm.tratamiento " +
            "JOIN SegEnfermedad e ON e.id = t.enfermedadId " +
            "WHERE sm.estadoId = 1 and p.estadoId = 1 " +
            "AND MONTH(sm.fechaProgramada) = :mesActual " +
            "ORDER BY sm.fechaProgramada")
    Page<SeguimientosDto> getTotalParaVentanaEmergente(Integer mesActual, Pageable pageable);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.NotificarDto(" +
            "p.codHistorialClinico, p.persona.apellidoPaterno, p.persona.apellidoMaterno, p.persona.nombres, " +
            "p.persona.telefono, sm.observacion) " +
            "FROM SegSeguimientoMedicamento sm " +
            "JOIN SegPaciente p ON p.id = sm.seguimiento.pacienteId " +
            "where sm.id =:seguimientoMedicamentoId")
    NotificarDto getDataModalNotificar(Long seguimientoMedicamentoId);

    @Query("SELECT COUNT(sm) " +
            "FROM SegSeguimientoMedicamento sm " +
            "WHERE sm.estadoId = 1 " +
            "AND sm.fechaProgramada = CURRENT_DATE")
    Long countSeguimientosParaHoy();

    @Query("SELECT COUNT(sm) " +
            "FROM SegSeguimientoMedicamento sm " +
            "WHERE sm.estadoId = 1 " +
            "AND sm.fechaProgramada < CURRENT_DATE")
    Long countSeguimientosAtrasados();

    @Query("SELECT COUNT(sm) " +
            "FROM SegSeguimientoMedicamento sm " +
            "WHERE sm.estadoId = 1 " +
            "AND sm.fechaProgramada <=:fecha")
    Long countSeguimientosProximosDias(LocalDate fecha);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad(" +
            "MONTHNAME(sm.fechaNotificacion), count(sm)) " +
            "FROM SegSeguimientoMedicamento sm " +
            "WHERE sm.estadoId = 5 AND YEAR(sm.fechaNotificacion) = YEAR(CURRENT_DATE)" +
            "GROUP BY MONTH(sm.fechaNotificacion)")
    List<SeguimientoCantidad> getSeguimientosTerminadosPorMes();

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad(" +
            "CASE WHEN sm.estadoId = 1 THEN 'En seguimiento' " +
            "WHEN sm.estadoId = 4 THEN 'Eliminado' " +
            "WHEN sm.estadoId = 5 THEN 'Terminado' " +
            "END " +
            ", count(sm)) " +
            "FROM SegSeguimientoMedicamento sm " +
            "WHERE sm.estadoId IN (1, 4, 5) " +
            "AND YEAR(sm.fechaProgramada) = YEAR(CURRENT_DATE) " +
            "AND MONTH(sm.fechaProgramada) = MONTH(CURRENT_DATE) " +
            "GROUP BY sm.estadoId")
    List<SeguimientoCantidad> getSeguimientos();
}
