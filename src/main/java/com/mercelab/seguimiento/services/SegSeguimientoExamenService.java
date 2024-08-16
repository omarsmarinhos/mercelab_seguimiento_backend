package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.Seguimiento;
import com.mercelab.seguimiento.models._m_seguimiento.*;
import com.mercelab.seguimiento.models._m_seguimiento.dto.NotificarDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoExamenDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegHistorialRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegSeguimientoExamenRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegSeguimientoRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegTratamientoExamenRepository;
import com.mercelab.seguimiento.services.mapping.SeguimientoExamenMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SegSeguimientoExamenService {

    @Autowired
    private SegSeguimientoExamenRepository seguimientoExamenRepository;

    @Autowired
    private SegSeguimientoRepository seguimientoRepository;

    @Autowired
    private SegTratamientoExamenRepository tratamientoExamenRepository;

    @Autowired
    private SegHistorialRepository historialRepository;

    @Autowired
    private SeguimientoExamenMapper seguimientoExamenMapper;

    @Transactional
    public void guardar(List<SegSeguimientoExamenDto> list) {
        SegSeguimiento seguimiento = seguimientoRepository.findById(list.get(0).getSeguimientoId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el seguimiento con ID " + list.get(0).getSeguimientoId()));

        list.forEach(e -> {
            SegSeguimientoExamen seguimientoExamen = seguimientoExamenMapper.toEntity(e);
            seguimientoExamen.setSeguimiento(seguimiento);

            SegTratamientoExamen tratamientoExamen = tratamientoExamenRepository.findById(e.getTratamientoExamenId())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el tratamiento examen con ID " + e.getTratamientoExamenId()));
            seguimientoExamen.setTratamientoExamen(tratamientoExamen);
            seguimientoExamen = seguimientoExamenRepository.save(seguimientoExamen);

            SegHistorial segHistorial = new SegHistorial();
            segHistorial.setFechaInicio(seguimientoExamen.getFechaProgramada());
            segHistorial.setElementoId(seguimientoExamen.getId());
            segHistorial.setElementoTipoNombre(Seguimiento.EXAMEN);
            segHistorial.setSegId(seguimiento.getId());
            historialRepository.save(segHistorial);
        });

        if (seguimiento.getEstadoId() == Estado.VACIO) {
            seguimiento.setEstadoId(Estado.ACTIVO);
        }
    }

    public List<SegSeguimientoExamenDto> getTratamientoExamenesUnicosAsignados(Long seguimientoId) {
        return seguimientoExamenRepository.getTratamientosExamenesUnicosAsignados(seguimientoId)
                .stream().map(seguimientoExamenMapper::toSegSeguimientoExamenDtoWithExtras).toList();
    }

    public List<SegSeguimientoExamenDto> getTratamientoExamenesAsignados(Long seguimientoId, Long tratamientoExamenId) {
        return seguimientoExamenRepository.getTratamientosExamenesAsignados(seguimientoId, tratamientoExamenId)
                .stream().map(seguimientoExamenMapper::toSegSeguimientoExamenDto).toList();
    }

    public Boolean estaRegistradoFechaProgramada(SegSeguimientoExamenDto seguimientoExamenDto) {
        return seguimientoExamenRepository.existeFechaProgramada(
                        seguimientoExamenDto.getSeguimientoId(),
                        seguimientoExamenDto.getTratamientoExamenId(),
                        seguimientoExamenDto.getFechaProgramada())
                .isPresent();
    }

    public void anular(Long id) {
        SegSeguimientoExamen seguimientoExamen = seguimientoExamenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el seguimiento examen con ID " + id));
        seguimientoExamen.setEstadoId(Estado.ELIMINADO);
        seguimientoExamenRepository.save(seguimientoExamen);
        SegHistorial historial = historialRepository.getSegHistorialByElementoIdAndElementoTipoNombre(id, Seguimiento.EXAMEN)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));
        historial.setEstadoId(Estado.ELIMINADO);
        historialRepository.save(historial);
    }

    @Transactional
    public void anularTodoTratamientoExamen(Long seguimientoId, Long tratamientoExamenId) {
        SegSeguimiento seguimiento = seguimientoRepository.findById(seguimientoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el seguimiento con ID " + seguimientoId));
        List<SegSeguimientoExamen> list = seguimientoExamenRepository
                .getTratamientosExamenesAsignados(seguimientoId, tratamientoExamenId);
        list.forEach(e -> {
            e.setEstadoId(Estado.ELIMINADO);
            SegHistorial historial = historialRepository.getSegHistorialByElementoIdAndElementoTipoNombre(e.getId(), Seguimiento.EXAMEN)
                    .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));
            historial.setEstadoId(Estado.ELIMINADO);
        });
    }

    public Page<SeguimientosDto> getSeguimientosExamensPorFechaProxima(LocalDate fechaDesde, LocalDate fechaHasta, String q, Pageable pageable) {
        return seguimientoExamenRepository.getTotalPaginado(fechaDesde, fechaHasta, q, pageable);
    }

    public Page<SeguimientosDto> getSeguimientosParaVentanaEmergente() {
        Integer mesActual = LocalDate.now().getMonthValue();
        Pageable pageable = PageRequest.of(0, 3);
        return seguimientoExamenRepository.getTotalParaVentanaEmergente(mesActual, pageable);
    }

    public Long countSeguimientosParaHoy() {
        return seguimientoExamenRepository.countSeguimientosParaHoy();
    }

    public Long countSeguimientosAtrasados() {
        return seguimientoExamenRepository.countSeguimientosAtrasados();
    }

    public Long countSeguimientosProximosDias(Integer dias) {
        LocalDate date = LocalDate.now().plusDays(dias);
        return seguimientoExamenRepository.countSeguimientosProximosDias(date);
    }

    public NotificarDto getDataNotificar(Long seguimientoExamenId) {
        return seguimientoExamenRepository.getDataModalNotificar(seguimientoExamenId);
    }

    @Transactional
    public void notificarSeguimiento(Long id, String respuesta) {
        SegSeguimientoExamen seguimientoExamen = seguimientoExamenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No encontrado"));

        seguimientoExamen.setEstadoId(Estado.TERMINADO);
        seguimientoExamen.setFechaNotificacion(LocalDate.now());
        seguimientoExamen.setRespuesta(respuesta);

        SegHistorial historial = historialRepository.getSegHistorialByElementoIdAndElementoTipoNombre(id, Seguimiento.EXAMEN)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));
        historial.setEstadoId(Estado.TERMINADO);
        historial.setFechaFin(LocalDate.now());
    }

    public List<SeguimientoCantidad> getCantidadDeSeguimientosTerminadosPorMes() {
        return seguimientoExamenRepository.getSeguimientosTerminadosPorMes();
    }

    public List<SeguimientoCantidad> getSeguimientos() {
        return seguimientoExamenRepository.getSeguimientos();
    }

}
