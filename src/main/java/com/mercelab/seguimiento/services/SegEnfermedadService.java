package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.SegCie10;
import com.mercelab.seguimiento.models._m_seguimiento.SegEnfermedad;
import com.mercelab.seguimiento.models._m_seguimiento.SegPaciente;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegEnfermedadDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegCie10Repository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegEnfermedadRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegPacienteRepository;
import com.mercelab.seguimiento.services.mapping.EnfermedadMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SegEnfermedadService {

    @Autowired
    private EnfermedadMapper enfermedadMapper;

    @Autowired
    private SegEnfermedadRepository enfermedadRepository;

    @Autowired
    private SegPacienteRepository pacienteRepository;

    @Autowired
    private SegCie10Repository cie10Repository;

    public Page<SegCie10> listarEnfermedadesCie10(String q, Pageable pageable) {
        return cie10Repository.listarCie10(q, pageable);
    }

    @Transactional
    public void guardar(List<SegEnfermedadDto> segEnfermedadDtos) {
        SegPaciente paciente = pacienteRepository.findById(segEnfermedadDtos.get(0).getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el paciente con ID " + segEnfermedadDtos.get(0).getPacienteId()));

        segEnfermedadDtos.forEach(e -> {
            SegEnfermedad enfermedad = enfermedadMapper.toEntity(e);
            enfermedad.setPaciente(paciente);

            if (e.getCie10Id() != null) {
                SegCie10 cie10 = cie10Repository.findById(e.getCie10Id())
                        .orElseThrow(() -> new EntityNotFoundException("No se encontró la enfermedad CIE10 con el ID " +
                                e.getCie10Id()));
                enfermedad.setCie10(cie10);
            }
            System.out.println(enfermedad);
            enfermedadRepository.save(enfermedad);
        });
    }

    public Boolean estaRegistradaEnfermedad(SegEnfermedadDto enfermedadDto) {
        return enfermedadRepository.existePacienteYEnfermedadCie10(
                enfermedadDto.getPacienteId(),
                enfermedadDto.getCie10Id())
                .isPresent();
    }

}
