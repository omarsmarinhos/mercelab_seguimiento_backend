package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.*;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoVacunaDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegTratamientoRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegTratamientoVacunaRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegVacunaRepository;
import com.mercelab.seguimiento.services.mapping.TratamientoVacunaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SegTratamientoVacunaService {

    @Autowired
    private SegTratamientoVacunaRepository tratamientoVacunaRepository;

    @Autowired
    private SegTratamientoRepository tratamientoRepository;

    @Autowired
    private SegVacunaRepository vacunaRepository;

    @Autowired
    private TratamientoVacunaMapper tratamientoVacunaMapper;

    public List<SegTratamientoVacunaDto> getVacunasAsignadas(Long tratamientoId) {
        return tratamientoVacunaRepository.getVacunasAsignadas(tratamientoId);
    }

    @Transactional
    public SegTratamientoVacunaDto agregarVacuna(SegTratamientoVacunaDto tratamientoVacunaDto) {
        SegTratamiento tratamiento = tratamientoRepository.findById(tratamientoVacunaDto.getTratamientoId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el tratamiento con ID " + tratamientoVacunaDto.getTratamientoId()));

        SegVacuna vacuna = vacunaRepository.findById(tratamientoVacunaDto.getVacunaId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la vacuna con el ID " + tratamientoVacunaDto.getVacunaId()));

        SegTratamientoVacuna tratamientoVacuna = tratamientoVacunaMapper.toEntity(tratamientoVacunaDto);

        if (tratamiento.getEstadoId() == Estado.VACIO) {
            tratamiento.setEstadoId(Estado.ACTIVO);
            tratamientoRepository.save(tratamiento);
        }

        tratamientoVacuna.setTratamiento(tratamiento);
        tratamientoVacuna.setVacuna(vacuna);

        return tratamientoVacunaMapper.toSegTratamientoVacunaDto(tratamientoVacunaRepository.save(tratamientoVacuna));
    }


    public void anular(Long id) {
        SegTratamientoVacuna tratamientoVacuna = tratamientoVacunaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el tratamiento con vacuna de ID " + id));
        tratamientoVacuna.setEstadoId(Estado.ELIMINADO);
        tratamientoVacunaRepository.save(tratamientoVacuna);
    }

    public Boolean estaRegistradoTratamientoVacuna(SegTratamientoVacunaDto tratamientoVacunaDto) {
        return tratamientoVacunaRepository.existeTratamientoVacuna(
                        tratamientoVacunaDto.getTratamientoId(),
                        tratamientoVacunaDto.getVacunaId())
                .isPresent();
    }

}
