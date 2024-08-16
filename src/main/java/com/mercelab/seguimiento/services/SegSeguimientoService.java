package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimiento;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegSeguimientoRepository;
import com.mercelab.seguimiento.services.mapping.SeguimientoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SegSeguimientoService {

    @Autowired
    private SegSeguimientoRepository seguimientoRepository;

    @Autowired
    private SeguimientoMapper seguimientoMapper;

    public SegSeguimientoDto guardar(SegSeguimientoDto seguimientoDto) {
        return seguimientoMapper.toSegSeguimientoDto(seguimientoRepository.save(seguimientoMapper.toEntity(seguimientoDto)));
    }

    public SegSeguimientoDto editar(Long id, SegSeguimientoDto seguimientoDto) {
        SegSeguimiento seguimientoUpdate = seguimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el seguimiento con ID " + id));

        seguimientoMapper.actualizarSeguimientoDesdeDto(seguimientoUpdate, seguimientoDto);
        return seguimientoMapper.toSegSeguimientoDto(seguimientoRepository.save(seguimientoUpdate));
    }

    public Optional<SegSeguimientoDto> getPorId(Long id) {
        return seguimientoRepository.findById(id).map(seguimientoMapper::toSegSeguimientoDto);
    }
}
