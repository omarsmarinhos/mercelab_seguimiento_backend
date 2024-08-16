package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.*;
import com.mercelab.seguimiento.models._m_seguimiento.dto.NotificarDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoMedicamentoDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegHistorialRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegSeguimientoMedicamentoRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegSeguimientoRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegTratamientoMedicamentoRepository;
import com.mercelab.seguimiento.services.mapping.SeguimientoMedicamentoMapper;
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
public class SegSeguimientoMedicamentoService {

    @Autowired
    private SegSeguimientoMedicamentoRepository seguimientoMedicamentoRepository;

    @Autowired
    private SegSeguimientoRepository seguimientoRepository;

    @Autowired
    private SegTratamientoMedicamentoRepository tratamientoMedicamentoRepository;

    @Autowired
    private SegHistorialRepository historialRepository;

    @Autowired
    private SeguimientoMedicamentoMapper seguimientoMedicamentoMapper;

    @Transactional
    public void guardar(List<SegSeguimientoMedicamentoDto> list) {
        SegSeguimiento seguimiento = seguimientoRepository.findById(list.get(0).getSeguimientoId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el seguimiento con ID " + list.get(0).getSeguimientoId()));

        list.forEach(e -> {
            SegSeguimientoMedicamento seguimientoMedicamento = seguimientoMedicamentoMapper.toEntity(e);
            seguimientoMedicamento.setSeguimiento(seguimiento);

            SegTratamientoMedicamento tratamientoMedicamento = tratamientoMedicamentoRepository.findById(e.getTratamientoMedicamentoId())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el tratamiento medicamento con ID " + e.getTratamientoMedicamentoId()));
            seguimientoMedicamento.setTratamientoMedicamento(tratamientoMedicamento);
            seguimientoMedicamento = seguimientoMedicamentoRepository.save(seguimientoMedicamento);

            SegHistorial segHistorial = new SegHistorial();
            segHistorial.setFechaInicio(seguimientoMedicamento.getFechaProgramada());
            segHistorial.setElementoId(seguimientoMedicamento.getId());
            segHistorial.setElementoTipoNombre(Seguimiento.MEDICAMENTO);
            segHistorial.setSegId(seguimiento.getId());
            historialRepository.save(segHistorial);
        });

        if (seguimiento.getEstadoId() == Estado.VACIO) {
            seguimiento.setEstadoId(Estado.ACTIVO);
        }
    }

    public List<SegSeguimientoMedicamentoDto> getTratamientoMedicamentosUnicosAsignados(Long seguimientoId) {
        return seguimientoMedicamentoRepository.getTratamientosMedicamentosUnicosAsignados(seguimientoId)
                .stream().map(seguimientoMedicamentoMapper::toSegSeguimientoExamenDto).toList();
    }

    public List<SegSeguimientoMedicamentoDto> getTratamientoMedicamentosAsignados(Long seguimientoId, Long tratamientoMedicamentoId) {
        return seguimientoMedicamentoRepository.getTratamientosMedicamentosAsignados(seguimientoId, tratamientoMedicamentoId)
                .stream().map(seguimientoMedicamentoMapper::toSegSeguimientoExamenDto).toList();
    }

    public Boolean estaRegistradoFechaProgramada(SegSeguimientoMedicamentoDto seguimientoMedicamentoDto) {
        return seguimientoMedicamentoRepository.existeFechaProgramada(
                        seguimientoMedicamentoDto.getSeguimientoId(),
                        seguimientoMedicamentoDto.getTratamientoMedicamentoId(),
                        seguimientoMedicamentoDto.getFechaProgramada())
                .isPresent();
    }

    public void anular(Long id) {
        SegSeguimientoMedicamento seguimientoMedicamento = seguimientoMedicamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el seguimiento examen con ID " + id));
        seguimientoMedicamento.setEstadoId(Estado.ELIMINADO);
        seguimientoMedicamentoRepository.save(seguimientoMedicamento);
        SegHistorial historial = historialRepository.getSegHistorialByElementoIdAndElementoTipoNombre(id, Seguimiento.MEDICAMENTO)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));
        historial.setEstadoId(Estado.ELIMINADO);
        historialRepository.save(historial);
    }

    @Transactional
    public void anularTodoTratamientoMedicamento(Long seguimientoId, Long tratamientoMedicamentoId) {
        SegSeguimiento seguimiento = seguimientoRepository.findById(seguimientoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el seguimiento con ID " + seguimientoId));
        List<SegSeguimientoMedicamento> list = seguimientoMedicamentoRepository
                .getTratamientosMedicamentosAsignados(seguimientoId, tratamientoMedicamentoId);
        list.forEach(e -> {
            e.setEstadoId(Estado.ELIMINADO);
            SegHistorial historial = historialRepository.getSegHistorialByElementoIdAndElementoTipoNombre(e.getId(), Seguimiento.MEDICAMENTO)
                    .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));
            historial.setEstadoId(Estado.ELIMINADO);
        });
    }

    public Page<SeguimientosDto> getSeguimientosMedicamentosPorFechaProxima(LocalDate fechaDesde, LocalDate fechaHasta, String q, Pageable pageable) {
        return seguimientoMedicamentoRepository.getTotalPaginado(fechaDesde, fechaHasta, q, pageable);
    }

    public Page<SeguimientosDto> getSeguimientosParaVentanaEmergente() {
        Integer mesActual = LocalDate.now().getMonthValue();
        Pageable pageable = PageRequest.of(0, 3);
        return seguimientoMedicamentoRepository.getTotalParaVentanaEmergente(mesActual, pageable);
    }

    public Long countSeguimientosParaHoy() {
        return seguimientoMedicamentoRepository.countSeguimientosParaHoy();
    }

    public Long countSeguimientosAtrasados() {
        return seguimientoMedicamentoRepository.countSeguimientosAtrasados();
    }

    public Long countSeguimientosProximosDias(Integer dias) {
        LocalDate date = LocalDate.now().plusDays(dias);
        return seguimientoMedicamentoRepository.countSeguimientosProximosDias(date);
    }

    public NotificarDto getDataNotificar(Long seguimientoExamenId) {
        return seguimientoMedicamentoRepository.getDataModalNotificar(seguimientoExamenId);
    }

    @Transactional
    public void notificarSeguimiento(Long id, String respuesta) {
        SegSeguimientoMedicamento seguimientoMedicamento = seguimientoMedicamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No encontrado"));

        seguimientoMedicamento.setEstadoId(Estado.TERMINADO);
        seguimientoMedicamento.setFechaNotificacion(LocalDate.now());
        seguimientoMedicamento.setRespuesta(respuesta);

        SegHistorial historial = historialRepository.getSegHistorialByElementoIdAndElementoTipoNombre(id, Seguimiento.MEDICAMENTO)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));
        historial.setEstadoId(Estado.TERMINADO);
        historial.setFechaFin(LocalDate.now());
    }

    public List<SeguimientoCantidad> getCantidadDeSeguimientosTerminadosPorMes() {
        return seguimientoMedicamentoRepository.getSeguimientosTerminadosPorMes();
    }

    public List<SeguimientoCantidad> getSeguimientos() {
        return seguimientoMedicamentoRepository.getSeguimientos();
    }

}
