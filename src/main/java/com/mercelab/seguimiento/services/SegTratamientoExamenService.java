package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.Estado;
import com.mercelab.seguimiento.models._m_seguimiento.SegExamen;
import com.mercelab.seguimiento.models._m_seguimiento.SegTratamiento;
import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoExamen;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoExamenDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegExamenRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegTratamientoExamenRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegTratamientoRepository;
import com.mercelab.seguimiento.services.mapping.TratamientoExamenMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SegTratamientoExamenService {

    @Autowired
    private SegTratamientoExamenRepository tratamientoExamenRepository;

    @Autowired
    private SegTratamientoRepository tratamientoRepository;

    @Autowired
    private SegExamenRepository examenRepository;

    @Autowired
    private TratamientoExamenMapper tratamientoExamenMapper;

    public List<SegTratamientoExamenDto> getExamenesAsignados(Long tratamientoId) {
        return tratamientoExamenRepository.getExamenesAsignados(tratamientoId);
    }

    @Transactional
    public SegTratamientoExamenDto agregarExamen(SegTratamientoExamenDto tratamientoExamenDto) {
        SegTratamiento tratamiento = tratamientoRepository.findById(tratamientoExamenDto.getTratamientoId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el tratamiento con ID " + tratamientoExamenDto.getTratamientoId()));

        SegExamen examen = examenRepository.findById(tratamientoExamenDto.getExamenId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el examen con el ID " + tratamientoExamenDto.getExamenId()));

        SegTratamientoExamen tratamientoExamen = tratamientoExamenMapper.toEntity(tratamientoExamenDto);

        if (tratamiento.getEstadoId() == Estado.VACIO) {
            tratamiento.setEstadoId(Estado.ACTIVO);
            tratamientoRepository.save(tratamiento);
        }

        tratamientoExamen.setTratamiento(tratamiento);
        tratamientoExamen.setExamen(examen);

        return tratamientoExamenMapper.toSegTratamientoExamenDto(tratamientoExamenRepository.save(tratamientoExamen));
    }

    public Optional<SegTratamientoExamenDto> getPorId(Long id) {
        return tratamientoExamenRepository.findById(id).map(tratamientoExamenMapper::toSegTratamientoExamenDto);
    }

    public void anular(Long id) {
        SegTratamientoExamen tratamientoExamen = tratamientoExamenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el tratamiento con examen de ID " + id));
        tratamientoExamen.setEstadoId(Estado.ELIMINADO);
        tratamientoExamenRepository.save(tratamientoExamen);
    }

    public Boolean estaRegistradoTratamientoExamen(SegTratamientoExamenDto tratamientoExamenDto) {
        return tratamientoExamenRepository.existeTratamientoExamen(
                        tratamientoExamenDto.getTratamientoId(),
                        tratamientoExamenDto.getExamenId())
                .isPresent();
    }

}
