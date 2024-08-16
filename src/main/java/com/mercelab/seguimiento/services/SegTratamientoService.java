package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.SegTratamiento;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoExamenDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegTratamientoRepository;
import com.mercelab.seguimiento.services.mapping.TratamientoExamenMapper;
import com.mercelab.seguimiento.services.mapping.TratamientoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SegTratamientoService {

    @Autowired
    private SegTratamientoRepository tratamientoRepository;

    @Autowired
    private TratamientoMapper tratamientoMapper;

    public SegTratamientoDto guardar(SegTratamientoDto tratamientoDto) {
        return tratamientoMapper.toTratamientoDto(tratamientoRepository.save(tratamientoMapper.toEntity(tratamientoDto)));
    }

    public SegTratamientoDto editar(Long id, SegTratamientoDto tratamientoDto) {
        SegTratamiento tratamientoUpdate = tratamientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el tratamiento con ID " + id));
        tratamientoMapper.actualizarTratamientoDesdeDto(tratamientoUpdate, tratamientoDto);
        return tratamientoMapper.toTratamientoDto(tratamientoRepository.save(tratamientoUpdate));
    }

    public Optional<SegTratamientoDto> getPorId(Long id) {
        return tratamientoRepository.findById(id).map(tratamientoMapper::toTratamientoDto);
    }

}
