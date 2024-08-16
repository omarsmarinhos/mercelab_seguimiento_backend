package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.*;
import com.mercelab.seguimiento.models._m_seguimiento.dto.NotificarDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoVacunaDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegHistorialRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegSeguimientoRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegSeguimientoVacunaRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegTratamientoVacunaRepository;
import com.mercelab.seguimiento.services.mapping.SeguimientoVacunaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SegSeguimientoVacunaService {

    @Autowired
    private SegSeguimientoVacunaRepository seguimientoVacunaRepository;

    @Autowired
    private SegSeguimientoRepository seguimientoRepository;

    @Autowired
    private SegTratamientoVacunaRepository tratamientoVacunaRepository;

    @Autowired
    private SegHistorialRepository historialRepository;

    @Autowired
    private SeguimientoVacunaMapper seguimientoVacunaMapper;

    @Transactional
    public void guardar(List<SegSeguimientoVacunaDto> list) {
        SegSeguimiento seguimiento = seguimientoRepository.findById(list.get(0).getSeguimientoId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el seguimiento con ID " + list.get(0).getSeguimientoId()));

        list.forEach(e -> {
            SegSeguimientoVacuna seguimientoVacuna = seguimientoVacunaMapper.toEntity(e);
            seguimientoVacuna.setSeguimiento(seguimiento);

            SegTratamientoVacuna tratamientoVacuna = tratamientoVacunaRepository.findById(e.getTratamientoVacunaId())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el tratamiento vacuna con ID " + e.getTratamientoVacunaId()));
            seguimientoVacuna.setTratamientoVacuna(tratamientoVacuna);
            seguimientoVacuna = seguimientoVacunaRepository.save(seguimientoVacuna);

            SegHistorial segHistorial = new SegHistorial();
            segHistorial.setFechaInicio(seguimientoVacuna.getFechaProgramada());
            segHistorial.setElementoId(seguimientoVacuna.getId());
            segHistorial.setElementoTipoNombre(Seguimiento.VACUNA);
            segHistorial.setSegId(seguimiento.getId());
            historialRepository.save(segHistorial);
        });

        if (seguimiento.getEstadoId() == Estado.VACIO) {
            seguimiento.setEstadoId(Estado.ACTIVO);
        }
    }

    public List<SegSeguimientoVacunaDto> getTratamientoVacunasUnicasAsignadas(Long seguimientoId) {
        return seguimientoVacunaRepository.getTratamientosVacunasUnicasAsignadas(seguimientoId)
                .stream().map(seguimientoVacunaMapper::toSegSeguimientoVacunaDtoWithExtras).toList();
    }

    public List<SegSeguimientoVacunaDto> getTratamientoVacunasAsignadas(Long seguimientoId, Long tratamientoExamenId) {
        return seguimientoVacunaRepository.getTratamientosVacunasAsignadas(seguimientoId, tratamientoExamenId)
                .stream().map(seguimientoVacunaMapper::toSegSeguimientoVacunaDto).toList();
    }

    public Boolean estaRegistradoFechaProgramada(SegSeguimientoVacunaDto seguimientoVacunaDto) {
        return seguimientoVacunaRepository.existeFechaProgramada(
                        seguimientoVacunaDto.getSeguimientoId(),
                        seguimientoVacunaDto.getTratamientoVacunaId(),
                        seguimientoVacunaDto.getFechaProgramada())
                .isPresent();
    }

    public void anular(Long id) {
        SegSeguimientoVacuna seguimientoVacuna = seguimientoVacunaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el seguimiento vacuna con ID " + id));
        seguimientoVacuna.setEstadoId(Estado.ELIMINADO);
        seguimientoVacunaRepository.save(seguimientoVacuna);
        SegHistorial historial = historialRepository.getSegHistorialByElementoIdAndElementoTipoNombre(id, Seguimiento.VACUNA)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));
        historial.setEstadoId(Estado.ELIMINADO);
        historialRepository.save(historial);
    }

    @Transactional
    public void anularTodoTratamientoVacuna(Long seguimientoId, Long tratamientoVacunaId) {
        SegSeguimiento seguimiento = seguimientoRepository.findById(seguimientoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el seguimiento con ID " + seguimientoId));
        List<SegSeguimientoVacuna> list = seguimientoVacunaRepository
                .getTratamientosVacunasAsignadas(seguimientoId, tratamientoVacunaId);
        list.forEach(e -> {
            e.setEstadoId(Estado.ELIMINADO);
            SegHistorial historial = historialRepository.getSegHistorialByElementoIdAndElementoTipoNombre(e.getId(), Seguimiento.VACUNA)
                    .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));
            historial.setEstadoId(Estado.ELIMINADO);
        });
    }

    public Page<SeguimientosDto> getSeguimientosVacunasPorFechaProxima(LocalDate fechaDesde, LocalDate fechaHasta, String q, Pageable pageable) {
        return seguimientoVacunaRepository.getTotalPaginado(fechaDesde, fechaHasta, q, pageable);
    }

    public Page<SeguimientosDto> getSeguimientosParaVentanaEmergente() {
        Integer mesActual = LocalDate.now().getMonthValue();
        Pageable pageable = PageRequest.of(0, 3);
        return seguimientoVacunaRepository.getTotalParaVentanaEmergente(mesActual, pageable);
    }

    public Long countSeguimientosParaHoy() {
        return seguimientoVacunaRepository.countSeguimientosParaHoy();
    }

    public Long countSeguimientosAtrasados() {
        return seguimientoVacunaRepository.countSeguimientosAtrasados();
    }

    public Long countSeguimientosProximosDias(Integer dias) {
        LocalDate date = LocalDate.now().plusDays(dias);
        return seguimientoVacunaRepository.countSeguimientosProximosDias(date);
    }

    public NotificarDto getDataNotificar(Long seguimientoExamenId) {
        return seguimientoVacunaRepository.getDataModalNotificar(seguimientoExamenId);
    }

    @Transactional
    public void notificarSeguimiento(Long id, String respuesta) {
        SegSeguimientoVacuna seguimientoVacuna = seguimientoVacunaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No encontrado"));

        seguimientoVacuna.setEstadoId(Estado.TERMINADO);
        seguimientoVacuna.setFechaNotificacion(LocalDate.now());
        seguimientoVacuna.setRespuesta(respuesta);

        SegHistorial historial = historialRepository.getSegHistorialByElementoIdAndElementoTipoNombre(id, Seguimiento.VACUNA)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado con id " + id));
        historial.setEstadoId(Estado.TERMINADO);
        historial.setFechaFin(LocalDate.now());
    }

    public List<SeguimientoCantidad> getCantidadDeSeguimientosTerminadosPorMes() {
        return seguimientoVacunaRepository.getSeguimientosTerminadosPorMes();
    }

    public List<SeguimientoCantidad> getSeguimientos() {
        return seguimientoVacunaRepository.getSeguimientos();
    }
}
