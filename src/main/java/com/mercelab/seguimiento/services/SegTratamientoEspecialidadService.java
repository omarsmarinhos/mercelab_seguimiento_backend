package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.*;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoEspecialidadDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.*;
import com.mercelab.seguimiento.services.mapping.TratamientoEspecialidadMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SegTratamientoEspecialidadService {

    @Autowired
    private SegTratamientoEspecialidadRepository tratamientoEspecialidadRepository;

    @Autowired
    private SegTratamientoRepository tratamientoRepository;

    @Autowired
    private SegEspecialidadRepository especialidadRepository;

    @Autowired
    private TratamientoEspecialidadMapper tratamientoEspecialidadMapper;

    public List<SegTratamientoEspecialidadDto> getEspecialidadesAsignadas(Long tratamientoId) {
        return tratamientoEspecialidadRepository.getEspecialidadesAsignadas(tratamientoId);
    }

    @Transactional
    public SegTratamientoEspecialidadDto agregarEspecialidad(SegTratamientoEspecialidadDto tratamientoEspecialidadDto) {
        SegTratamiento tratamiento = tratamientoRepository.findById(tratamientoEspecialidadDto.getTratamientoId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el tratamiento con ID " + tratamientoEspecialidadDto.getTratamientoId()));

        SegEspecialidad especialidad = especialidadRepository.findById(tratamientoEspecialidadDto.getEspecialidadId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la vacuna con el ID " + tratamientoEspecialidadDto.getEspecialidadId()));

        SegTratamientoEspecialidad tratamientoEspecialidad = tratamientoEspecialidadMapper.toEntity(tratamientoEspecialidadDto);

        if (tratamiento.getEstadoId() == Estado.VACIO) {
            tratamiento.setEstadoId(Estado.ACTIVO);
            tratamientoRepository.save(tratamiento);
        }

        tratamientoEspecialidad.setTratamiento(tratamiento);
        tratamientoEspecialidad.setEspecialidad(especialidad);

        return tratamientoEspecialidadMapper.toSegTratamientoEspecialidadDto(tratamientoEspecialidadRepository.save(tratamientoEspecialidad));
    }


    public void anular(Long id) {
        SegTratamientoEspecialidad tratamientoEspecialidad = tratamientoEspecialidadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el tratamiento con especialidad de ID " + id));
        tratamientoEspecialidad.setEstadoId(Estado.ELIMINADO);
        tratamientoEspecialidadRepository.save(tratamientoEspecialidad);
    }

    public Boolean estaRegistradoTratamientoEspecialidad(SegTratamientoEspecialidadDto tratamientoEspecialidadDto) {
        return tratamientoEspecialidadRepository.existeTratamientoEspecialidad(
                        tratamientoEspecialidadDto.getTratamientoId(),
                        tratamientoEspecialidadDto.getEspecialidadId())
                .isPresent();
    }

}
