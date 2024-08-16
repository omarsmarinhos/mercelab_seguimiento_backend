package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimientoEspecialidad;
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

public interface SegSeguimientoEspecialidadRepository extends JpaRepository<SegSeguimientoEspecialidad, Long> {

    @Query("SELECT se, te FROM SegSeguimientoEspecialidad se " +
            "JOIN se.tratamientoEspecialidad te " +
            "WHERE se.seguimiento.id = :seguimientoId " +
            "AND se.estadoId = 1 " +
            "AND se.tratamientoEspecialidad.estadoId = 1 " +
            "GROUP BY se.tratamientoEspecialidad.id")
    List<SegSeguimientoEspecialidad> getTratamientosEspecialidadesUnicosAsignados(Long seguimientoId);

    @Query("SELECT se FROM SegSeguimientoEspecialidad se " +
            "WHERE se.seguimiento.id =:seguimientoId " +
            "AND se.tratamientoEspecialidad.id =:tratamientoEspecialidadId " +
            "AND se.estadoId = 1 " +
            "ORDER BY se.fechaProgramada asc ")
    List<SegSeguimientoEspecialidad> getTratamientosEspecialidadesAsignados(Long seguimientoId, Long tratamientoEspecialidadId);
    @Query("SELECT se FROM SegSeguimientoEspecialidad  se " +
            "WHERE se.seguimiento.id =:seguimientoId " +
            "AND se.tratamientoEspecialidad.id =:tratamientoEspecialidadId " +
            "AND se.estadoId = 1 " +
            "AND se.fechaProgramada =:fecha")
    Optional<SegSeguimientoEspecialidad> existeFechaProgramada(Long seguimientoId
            , Long tratamientoEspecialidadId, LocalDate fecha);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto(" +
            "p.codHistorialClinico, p.persona.apellidoPaterno, p.persona.apellidoMaterno, p.persona.nombres, " +
            "se.seguimiento.id, se.id, te.especialidad.descripcion, te.id, " +
            "se.tipoNotificacion, se.estadoId, se.fechaProgramada, 'Especialidad', e) " +
            "FROM SegSeguimientoEspecialidad se " +
            "JOIN SegPaciente p ON p.id = se.seguimiento.pacienteId " +
            "JOIN SegTratamientoEspecialidad te ON te = se.tratamientoEspecialidad " +
            "JOIN SegTratamiento t ON t = te.tratamiento " +
            "JOIN SegEnfermedad e ON e.id = t.enfermedadId " +
            "WHERE se.estadoId = 1 and p.estadoId = 1 " +
            " AND (:termino IS NULL OR te.especialidad.descripcion LIKE %:termino% " +
            "OR p.persona.apellidoPaterno LIKE %:termino%) " +
            " AND (:fechaDesde IS NULL OR se.fechaProgramada >= :fechaDesde) " +
            "AND (:fechaHasta IS NULL OR se.fechaProgramada <= :fechaHasta) " +
            "ORDER BY se.fechaProgramada")
    Page<SeguimientosDto> getTotalPaginado(
            LocalDate fechaDesde,
            LocalDate fechaHasta,
            String termino,
            Pageable pageable
    );


    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto(" +
            "p.codHistorialClinico, p.persona.apellidoPaterno, p.persona.apellidoMaterno, p.persona.nombres, " +
            "se.seguimiento.id, se.id, te.especialidad.descripcion, te.id, " +
            "se.tipoNotificacion, se.estadoId, se.fechaProgramada, 'Especialidad', e) " +
            "FROM SegSeguimientoEspecialidad se " +
            "JOIN SegPaciente p ON p.id = se.seguimiento.pacienteId " +
            "JOIN SegTratamientoEspecialidad te ON te = se.tratamientoEspecialidad " +
            "JOIN SegTratamiento t ON t = te.tratamiento " +
            "JOIN SegEnfermedad e ON e.id = t.enfermedadId " +
            "WHERE se.estadoId = 1 and p.estadoId = 1 " +
            " AND MONTH(se.fechaProgramada) = :mesActual " +
            "ORDER BY se.fechaProgramada")
    Page<SeguimientosDto> getTotalParaVentanaEmergente(Integer mesActual, Pageable pageable);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.NotificarDto(" +
            "p.codHistorialClinico, p.persona.apellidoPaterno, p.persona.apellidoMaterno, p.persona.nombres, " +
            "p.persona.telefono, se.observacion) " +
            "FROM SegSeguimientoEspecialidad se " +
            "JOIN SegPaciente p ON p.id = se.seguimiento.pacienteId " +
            "where se.id =:seguimientoEspecialidadId")
    NotificarDto getDataModalNotificar(Long seguimientoEspecialidadId);

    @Query("SELECT COUNT(se) " +
            "FROM SegSeguimientoEspecialidad se " +
            "WHERE se.estadoId = 1 " +
            "AND se.fechaProgramada = CURRENT_DATE")
    Long countSeguimientosParaHoy();

    @Query("SELECT COUNT(se) " +
            "FROM SegSeguimientoEspecialidad se " +
            "WHERE se.estadoId = 1 " +
            "AND se.fechaProgramada < CURRENT_DATE")
    Long countSeguimientosAtrasados();

    @Query("SELECT COUNT(se) " +
            "FROM SegSeguimientoEspecialidad se " +
            "WHERE se.estadoId = 1 " +
            "AND se.fechaProgramada <=:fecha")
    Long countSeguimientosProximosDias(LocalDate fecha);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad(" +
            "MONTHNAME(se.fechaNotificacion), count(se)) " +
            "FROM SegSeguimientoEspecialidad se " +
            "WHERE se.estadoId = 5 AND YEAR(se.fechaNotificacion) = YEAR(CURRENT_DATE)" +
            "GROUP BY MONTH(se.fechaNotificacion)")
    List<SeguimientoCantidad> getSeguimientosTerminadosPorMes();

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad(" +
            "CASE WHEN se.estadoId = 1 THEN 'En seguimiento' " +
            "WHEN se.estadoId = 4 THEN 'Eliminado' " +
            "WHEN se.estadoId = 5 THEN 'Terminado' " +
            "END " +
            ", count(se)) " +
            "FROM SegSeguimientoEspecialidad se " +
            "WHERE se.estadoId IN (1, 4, 5) " +
            "AND YEAR(se.fechaProgramada) = YEAR(CURRENT_DATE) " +
            "AND MONTH(se.fechaProgramada) = MONTH(CURRENT_DATE) " +
            "GROUP BY se.estadoId")
    List<SeguimientoCantidad> getSeguimientos();
}
