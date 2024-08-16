package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.*;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegPacienteDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegGeneralRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegPacienteRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegPersonaRepository;
import com.mercelab.seguimiento.services.mapping.PacienteMapper;
import com.mercelab.seguimiento.services.mapping.PersonaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SegPacienteService {

    @Autowired
    private SegPacienteRepository pacienteRepository;

    @Autowired
    private SegPersonaRepository personaRepository;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private PersonaMapper personaMapper;

    @Autowired
    private SegGeneralRepository generalRepository;

    public List<SegPaciente> getAll() {
        return pacienteRepository.findAll();
    }

    public Page<SegPacienteDto> listarPaginacionyBusqueda(String query, Pageable pageable) {
        pacienteRepository.paginarYBuscar(query, pageable).map(pacienteMapper::toPacienteDTOWithExtras)
                .forEach(System.out::println);
        return pacienteRepository.paginarYBuscar(query, pageable)
                .map(pacienteMapper::toPacienteDTOWithExtras);
    }

    public Optional<SegPacienteDto> buscarPorId(Long id) {
        return pacienteRepository.findById(id).map(pacienteMapper::toPacienteDTOWithExtras);
    }

    public Optional<SegPaciente> buscarPorNumeroDocumento(String nroDocumento) {
        return pacienteRepository.findSegPacienteByNumeroDocumento(nroDocumento);
    }

    @Transactional
    public SegPacienteDto guardar(SegPacienteDto pacienteDto) {
        String nroDocumento = pacienteDto.getPersona().getNumeroDocumento();
        Optional<SegPaciente> pacienteExistente = pacienteRepository.findSegPacienteByNumeroDocumento(nroDocumento);

        if (pacienteExistente.isPresent()) {
            throw new IllegalArgumentException("El paciente con número de documento " + nroDocumento + " ya está registrado.");
        }

        SegGeneral general = generalRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException(""));
        Long hcCorrelativo = general.getHcCorrelativo();
        hcCorrelativo++;
        general.setHcCorrelativo(hcCorrelativo);

        SegPaciente paciente = pacienteMapper.toEntity(pacienteDto);
        SegPersona persona = paciente.getPersona();

        SegDistrito distrito = new SegDistrito();
        distrito.setId(pacienteDto.getPersona().getDistritoId());
        persona.setDistrito(distrito);
        personaRepository.save(persona);

        paciente.setPersona(persona);
        paciente.setCodHistorialClinico(String.format("CM%07d", hcCorrelativo));

        return pacienteMapper.toPacienteDTO(pacienteRepository.save(paciente));
    }

    @Transactional
    public SegPacienteDto editar(Long id, SegPacienteDto pacienteDto) {

        SegPaciente pacienteUpdate = pacienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el paciente con ID " + id));

        pacienteMapper.actualizarPacienteDesdeDto(pacienteUpdate, pacienteDto);
        pacienteUpdate.setEditadoEn(LocalDateTime.now());
        if (pacienteDto.getPersona().getDistritoId() != null) {
            SegDistrito distrito = new SegDistrito();
            distrito.setId(pacienteDto.getPersona().getDistritoId());
            pacienteUpdate.getPersona().setDistrito(distrito);
        } else {
            pacienteUpdate.getPersona().setDistrito(null);
        }
        personaRepository.save(pacienteUpdate.getPersona());

        return pacienteMapper.toPacienteDTO(pacienteRepository.save(pacienteUpdate));
    }

    public SegPacienteDto anular(Long id) {
        SegPaciente pacienteUpdate = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el paciente con ID " + id));
        pacienteUpdate.setEstadoId(Estado.ELIMINADO);
        return pacienteMapper.toPacienteDTO(pacienteRepository.save(pacienteUpdate));
    }

    public Long countPacientes() {
        return pacienteRepository.countPacientes();
    }
}
