package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimientoEspecialidad;
import com.mercelab.seguimiento.models._m_seguimiento.*;
import com.mercelab.seguimiento.models._m_seguimiento.dto.NotificarDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoEspecialidadDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.*;
import com.mercelab.seguimiento.services.mapping.SeguimientoEspecialidadMapper;
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
public class SegSeguimientoEspecialidadService {

    @Autowired
    private SegSeguimientoEspecialidadRepository seguimientoEspecialidadRepository;

    @Autowired
    private SegSeguimientoRepository seguimientoRepository;

    @Autowired
    private SegTratamientoEspecialidadRepository tratamientoEspecialidadRepository;

    @Autowired
    private SegHistorialRepository historialRepository;

    @Autowired
    private SeguimientoEspecialidadMapper seguimientoEspecialidadMapper;

    @Transactional
    public void guardar(List<SegSeguimientoEspecialidadDto> list) {
        SegSeguimiento seguimiento = seguimientoRepository.findById(list.get(0).getSeguimientoId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el seguimiento con ID " + list.get(0).getSeguimientoId()));

        list.forEach(e -> {
            SegSeguimientoEspecialidad seguimientoEspecialidad = seguimientoEspecialidadMapper.toEntity(e);
            seguimientoEspecialidad.setSeguimiento(seguimiento);

            SegTratamientoEspecialidad tratamientoEspecialidad = tratamientoEspecialidadRepository.findById(e.getTratamientoEspecialidadId())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el tratamiento especialidad con ID " + e.getTratamientoEspecialidadId()));
            seguimientoEspecialidad.setTratamientoEspecialidad(tratamientoEspecialidad);
            seguimientoEspecialidad = seguimientoEspecialidadRepository.save(seguimientoEspecialidad);

            SegHistorial segHistorial = new SegHistorial();
            segHistorial.setFechaInicio(seguimientoEspecialidad.getFechaProgramada());
            segHistorial.setElementoId(seguimientoEspecialidad.getId());
            segHistorial.setElementoTipoNombre(Seguimiento.ESPECIALIDAD);
            segHistorial.setSegId(seguimiento.getId());
            historialRepository.save(segHistorial);
        });

        if (seguimiento.getEstadoId() == Estado.VACIO) {
            seguimiento.setEstadoId(Estado.ACTIVO);
        }
    }

    public List<SegSeguimientoEspecialidadDto> getTratamientoEspecialidadesUnicosAsignados(Long seguimientoId) {
        return seguimientoEspecialidadRepository.getTratamientosEspecialidadesUnicosAsignados(seguimientoId)
                .stream().map(seguimientoEspecialidadMapper::toSegSeguimientoEspecialidadDtoWithExtras).toList();
    }

    public List<SegSeguimientoEspecialidadDto> getTratamientoEspecialidadesAsignados(Long seguimientoId, Long tratamientoEspecialidadId) {
        return seguimientoEspecialidadRepository.getTratamientosEspecialidadesAsignados(seguimientoId, tratamientoEspecialidadId)
                .stream().map(seguimientoEspecialidadMapper::toSegSeguimientoEspecialidadDtoWithExtras).toList();
    }

    public Boolean estaRegistradoFechaProgramada(SegSeguimientoEspecialidadDto segSeguimientoEspecialidadDto) {
        return seguimientoEspecialidadRepository.existeFechaProgramada(
                        segSeguimientoEspecialidadDto.getSeguimientoId(),
                        segSeguimientoEspecialidadDto.getTratamientoEspecialidadId(),
                        segSeguimientoEspecialidadDto.getFechaProgramada())
                .isPresent();
    }

    public void anular(Long id) {
        SegSeguimientoEspecialidad seguimientoEspecialidad = seguimientoEspecialidadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el seguimiento especialidad con ID " + id));
        seguimientoEspecialidad.setEstadoId(Estado.ELIMINADO);
        seguimientoEspecialidadRepository.save(seguimientoEspecialidad);
        SegHistorial historial = historialRepository.getSegHistorialByElementoIdAndElementoTipoNombre(id, Seguimiento.ESPECIALIDAD)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));
        historial.setEstadoId(Estado.ELIMINADO);
        historialRepository.save(historial);
    }

    @Transactional
    public void anularTodoTratamientoEspecialidad(Long seguimientoId, Long tratamientoEspecialidadId) {
        SegSeguimiento seguimiento = seguimientoRepository.findById(seguimientoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el seguimiento con ID " + seguimientoId));
        List<SegSeguimientoEspecialidad> list = seguimientoEspecialidadRepository
                .getTratamientosEspecialidadesAsignados(seguimientoId, tratamientoEspecialidadId);
        list.forEach(e -> {
            e.setEstadoId(Estado.ELIMINADO);
            SegHistorial historial = historialRepository.getSegHistorialByElementoIdAndElementoTipoNombre(e.getId(), Seguimiento.ESPECIALIDAD)
                    .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));
            historial.setEstadoId(Estado.ELIMINADO);
        });
    }

    public Page<SeguimientosDto> getSeguimientosEspecialidadesPorFechaProxima(LocalDate fechaDesde, LocalDate fechaHasta, String q, Pageable pageable) {
        return seguimientoEspecialidadRepository.getTotalPaginado(fechaDesde, fechaHasta, q, pageable);
    }

    public Page<SeguimientosDto> getSeguimientosParaVentanaEmergente() {
        Integer mesActual = LocalDate.now().getMonthValue();
        Pageable pageable = PageRequest.of(0, 3);
        return seguimientoEspecialidadRepository.getTotalParaVentanaEmergente(mesActual, pageable);
    }

    public Long countSeguimientosParaHoy() {
        return seguimientoEspecialidadRepository.countSeguimientosParaHoy();
    }

    public Long countSeguimientosAtrasados() {
        return seguimientoEspecialidadRepository.countSeguimientosAtrasados();
    }

    public Long countSeguimientosProximosDias(Integer dias) {
        LocalDate date = LocalDate.now().plusDays(dias);
        return seguimientoEspecialidadRepository.countSeguimientosProximosDias(date);
    }

    public NotificarDto getDataNotificar(Long seguimientoExamenId) {
        return seguimientoEspecialidadRepository.getDataModalNotificar(seguimientoExamenId);
    }

    @Transactional
    public void notificarSeguimiento(Long id, String respuesta) {
        SegSeguimientoEspecialidad seguimientoEspecialidad = seguimientoEspecialidadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No encontrado"));

        seguimientoEspecialidad.setEstadoId(Estado.TERMINADO);
        seguimientoEspecialidad.setFechaNotificacion(LocalDate.now());
        seguimientoEspecialidad.setRespuesta(respuesta);

        SegHistorial historial = historialRepository.getSegHistorialByElementoIdAndElementoTipoNombre(id, Seguimiento.ESPECIALIDAD)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));
        historial.setEstadoId(Estado.TERMINADO);
        historial.setFechaFin(LocalDate.now());
    }

    public List<SeguimientoCantidad> getCantidadDeSeguimientosTerminadosPorMes() {
        return seguimientoEspecialidadRepository.getSeguimientosTerminadosPorMes();
    }

    public List<SeguimientoCantidad> getSeguimientos() {
        return seguimientoEspecialidadRepository.getSeguimientos();
    }

}
