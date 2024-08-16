package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimientoExamen;
import com.mercelab.seguimiento.models._m_seguimiento.dto.NotificarDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SegSeguimientoExamenRepository extends JpaRepository<SegSeguimientoExamen, Long> {

    @Query("SELECT se, te FROM SegSeguimientoExamen se " +
            "JOIN se.tratamientoExamen te " +
            "WHERE se.seguimiento.id = :seguimientoId " +
            "AND se.estadoId = 1 " +
            "AND se.tratamientoExamen.estadoId = 1 " +
            "GROUP BY se.tratamientoExamen.id")
    List<SegSeguimientoExamen> getTratamientosExamenesUnicosAsignados(@Param("seguimientoId") Long seguimientoId);

    @Query("SELECT se FROM SegSeguimientoExamen se " +
            "WHERE se.seguimiento.id =:seguimientoId " +
            "AND se.tratamientoExamen.id =:tratamientoExamenId " +
            "AND se.estadoId = 1 " +
            "ORDER BY se.fechaProgramada asc ")
    List<SegSeguimientoExamen> getTratamientosExamenesAsignados(@Param("seguimientoId") Long seguimientoId,
                                                                @Param("tratamientoExamenId") Long tratamientoExamenId);
    @Query("SELECT se FROM SegSeguimientoExamen  se " +
            "WHERE se.seguimiento.id =:seguimientoId " +
            "AND se.tratamientoExamen.id =:tratamientoExamenId " +
            "AND se.estadoId = 1 " +
            "AND se.fechaProgramada =:fecha")
    Optional<SegSeguimientoExamen> existeFechaProgramada(Long seguimientoId
            , Long tratamientoExamenId, LocalDate fecha);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto(" +
            "p.codHistorialClinico, p.persona.apellidoPaterno, p.persona.apellidoMaterno, p.persona.nombres, " +
            "se.seguimiento.id, se.id, te.examen.descripcion, te.id, " +
            "se.tipoNotificacion, se.estadoId, se.fechaProgramada, 'Examen', e) " +
            "FROM SegSeguimientoExamen se " +
            "JOIN SegPaciente p ON p.id = se.seguimiento.pacienteId " +
            "JOIN SegTratamientoExamen te ON te = se.tratamientoExamen " +
            "JOIN SegTratamiento t ON t = te.tratamiento " +
            "JOIN SegEnfermedad e ON e.id = t.enfermedadId " +
            "WHERE se.estadoId = 1 and p.estadoId = 1 " +
            " AND (:termino IS NULL OR te.examen.descripcion LIKE %:termino% " +
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
            "se.seguimiento.id, se.id, te.examen.descripcion, te.id, " +
            "se.tipoNotificacion, se.estadoId, se.fechaProgramada, 'Examen', e) " +
            "FROM SegSeguimientoExamen se " +
            "JOIN SegPaciente p ON p.id = se.seguimiento.pacienteId " +
            "JOIN SegTratamientoExamen te ON te = se.tratamientoExamen " +
            "JOIN SegTratamiento t ON t = te.tratamiento " +
            "JOIN SegEnfermedad e ON e.id = t.enfermedadId " +
            "WHERE se.estadoId = 1 and p.estadoId = 1 " +
            " AND MONTH(se.fechaProgramada) = :mesActual " +
            "ORDER BY se.fechaProgramada")
    Page<SeguimientosDto> getTotalParaVentanaEmergente(Integer mesActual, Pageable pageable);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.NotificarDto(" +
            "p.codHistorialClinico, p.persona.apellidoPaterno, p.persona.apellidoMaterno, p.persona.nombres, " +
            "p.persona.telefono, se.observacion) " +
            "FROM SegSeguimientoExamen se " +
            "JOIN SegPaciente p ON p.id = se.seguimiento.pacienteId " +
            "where se.id =:seguimientoExamenId")
    NotificarDto getDataModalNotificar(Long seguimientoExamenId);

    @Query("SELECT COUNT(se) " +
            "FROM SegSeguimientoExamen se " +
            "WHERE se.estadoId = 1 " +
            "AND se.fechaProgramada = CURRENT_DATE")
    Long countSeguimientosParaHoy();

    @Query("SELECT COUNT(se) " +
            "FROM SegSeguimientoExamen se " +
            "WHERE se.estadoId = 1 " +
            "AND se.fechaProgramada < CURRENT_DATE")
    Long countSeguimientosAtrasados();

    @Query("SELECT COUNT(se) " +
            "FROM SegSeguimientoExamen se " +
            "WHERE se.estadoId = 1 " +
            "AND se.fechaProgramada <=:fecha")
    Long countSeguimientosProximosDias(LocalDate fecha);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad(" +
            "MONTHNAME(se.fechaNotificacion), count(se)) " +
            "FROM SegSeguimientoExamen se " +
            "WHERE se.estadoId = 5 AND YEAR(se.fechaNotificacion) = YEAR(CURRENT_DATE)" +
            "GROUP BY MONTH(se.fechaNotificacion)")
    List<SeguimientoCantidad> getSeguimientosTerminadosPorMes();

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad(" +
            "CASE WHEN s.estadoId = 1 THEN 'En seguimiento' " +
            "WHEN s.estadoId = 4 THEN 'Eliminado' " +
            "WHEN s.estadoId = 5 THEN 'Terminado' " +
            "END " +
            ", count(s)) " +
            "FROM SegSeguimientoExamen s " +
            "WHERE s.estadoId IN (1, 4, 5) " +
            "AND YEAR(s.fechaProgramada) = YEAR(CURRENT_DATE) " +
            "AND MONTH(s.fechaProgramada) = MONTH(CURRENT_DATE) " +
            "GROUP BY s.estadoId")
    List<SeguimientoCantidad> getSeguimientos();

}
