package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.Estado;
import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimientoVacuna;
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

public interface SegSeguimientoVacunaRepository extends JpaRepository<SegSeguimientoVacuna, Long> {

    @Query("SELECT sv, tv FROM SegSeguimientoVacuna sv " +
            "JOIN sv.tratamientoVacuna tv " +
            "WHERE sv.seguimiento.id = :seguimientoId " +
            "AND sv.estadoId = " + Estado.ACTIVO +
            " AND sv.tratamientoVacuna.estadoId = " + Estado.ACTIVO +
            " GROUP BY sv.tratamientoVacuna.id")
    List<SegSeguimientoVacuna> getTratamientosVacunasUnicasAsignadas(Long seguimientoId);

    @Query("SELECT sv FROM SegSeguimientoVacuna sv " +
            "WHERE sv.seguimiento.id =:seguimientoId " +
            "AND sv.tratamientoVacuna.id =:tratamientoVacunaId " +
            "AND sv.estadoId = " + Estado.ACTIVO +
            " ORDER BY sv.fechaProgramada asc ")
    List<SegSeguimientoVacuna> getTratamientosVacunasAsignadas(Long seguimientoId, Long tratamientoVacunaId);
    @Query("SELECT sv FROM SegSeguimientoVacuna  sv " +
            "WHERE sv.seguimiento.id =:seguimientoId " +
            "AND sv.tratamientoVacuna.id =:tratamientoVacunaId " +
            "AND sv.estadoId = " + Estado.ACTIVO +
            " AND sv.fechaProgramada =:fecha")
    Optional<SegSeguimientoVacuna> existeFechaProgramada(Long seguimientoId
            , Long tratamientoVacunaId, LocalDate fecha);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto(" +
            "p.codHistorialClinico, p.persona.apellidoPaterno, p.persona.apellidoMaterno, p.persona.nombres, " +
            "sv.seguimiento.id, sv.id, tv.vacuna.descripcion, tv.id, " +
            "sv.tipoNotificacion, sv.estadoId, sv.fechaProgramada, 'Vacuna', e) " +
            "FROM SegSeguimientoVacuna sv " +
            "JOIN SegPaciente p ON p.id = sv.seguimiento.pacienteId " +
            "JOIN SegTratamientoVacuna tv ON tv = sv.tratamientoVacuna " +
            "JOIN SegTratamiento t ON t = tv.tratamiento " +
            "JOIN SegEnfermedad e ON e.id = t.enfermedadId " +
            "WHERE sv.estadoId = 1 and p.estadoId = 1 " +
            " AND (:termino IS NULL OR tv.vacuna.descripcion LIKE %:termino% " +
            "OR p.persona.apellidoPaterno LIKE %:termino%) " +
            " AND (:fechaDesde IS NULL OR sv.fechaProgramada >= :fechaDesde) " +
            "AND (:fechaHasta IS NULL OR sv.fechaProgramada <= :fechaHasta) " +
            "ORDER BY sv.fechaProgramada")
    Page<SeguimientosDto> getTotalPaginado(
            LocalDate fechaDesde,
            LocalDate fechaHasta,
            String termino,
            Pageable pageable
    );

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto(" +
            "p.codHistorialClinico, p.persona.apellidoPaterno, p.persona.apellidoMaterno, p.persona.nombres, " +
            "sv.seguimiento.id, sv.id, tv.vacuna.descripcion, tv.id, " +
            "sv.tipoNotificacion, sv.estadoId, sv.fechaProgramada, 'Vacuna', e) " +
            "FROM SegSeguimientoVacuna sv " +
            "JOIN SegPaciente p ON p.id = sv.seguimiento.pacienteId " +
            "JOIN SegTratamientoVacuna tv ON tv = sv.tratamientoVacuna " +
            "JOIN SegTratamiento t ON t = tv.tratamiento " +
            "JOIN SegEnfermedad e ON e.id = t.enfermedadId " +
            "WHERE sv.estadoId = " + Estado.ACTIVO +
            " AND MONTH(sv.fechaProgramada) = :mesActual " +
            " ORDER BY sv.fechaProgramada")
    Page<SeguimientosDto> getTotalParaVentanaEmergente(Integer mesActual, Pageable pageable);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.NotificarDto(" +
            "p.codHistorialClinico, p.persona.apellidoPaterno, p.persona.apellidoMaterno, p.persona.nombres, " +
            "p.persona.telefono, sv.observacion) " +
            "FROM SegSeguimientoVacuna sv " +
            "JOIN SegPaciente p ON p.id = sv.seguimiento.pacienteId " +
            "where sv.id =:seguimientoVacunaId")
    NotificarDto getDataModalNotificar(Long seguimientoVacunaId);

    @Query("SELECT COUNT(sv) " +
            "FROM SegSeguimientoVacuna sv " +
            "WHERE sv.estadoId = 1 " +
            "AND sv.fechaProgramada = CURRENT_DATE")
    Long countSeguimientosParaHoy();

    @Query("SELECT COUNT(sv) " +
            "FROM SegSeguimientoVacuna sv " +
            "WHERE sv.estadoId = 1 " +
            "AND sv.fechaProgramada < CURRENT_DATE")
    Long countSeguimientosAtrasados();

    @Query("SELECT COUNT(sv) " +
            "FROM SegSeguimientoVacuna sv " +
            "WHERE sv.estadoId = 1 " +
            "AND sv.fechaProgramada <=:fecha")
    Long countSeguimientosProximosDias(LocalDate fecha);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad(" +
            "MONTHNAME(sv.fechaNotificacion), count(sv)) " +
            "FROM SegSeguimientoVacuna sv " +
            "WHERE sv.estadoId = 5 AND YEAR(sv.fechaNotificacion) = YEAR(CURRENT_DATE)" +
            "GROUP BY MONTH(sv.fechaNotificacion)")
    List<SeguimientoCantidad> getSeguimientosTerminadosPorMes();

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad(" +
            "CASE WHEN sv.estadoId = 1 THEN 'En seguimiento' " +
            "WHEN sv.estadoId = 4 THEN 'Eliminado' " +
            "WHEN sv.estadoId = 5 THEN 'Terminado' " +
            "END " +
            ", count(sv)) " +
            "FROM SegSeguimientoVacuna sv " +
            "WHERE sv.estadoId IN (1, 4, 5) " +
            "AND YEAR(sv.fechaProgramada) = YEAR(CURRENT_DATE) " +
            "AND MONTH(sv.fechaProgramada) = MONTH(CURRENT_DATE) " +
            "GROUP BY sv.estadoId")
    List<SeguimientoCantidad> getSeguimientos();
}
