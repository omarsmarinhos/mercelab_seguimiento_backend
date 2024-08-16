package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.Estado;
import com.mercelab.seguimiento.models._m_seguimiento.SegEnfermedad;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegDiagnosticoDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegDiagnosticoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SegDiagnosticoService {

    @Autowired
    private SegDiagnosticoRepository diagnosticoRepository;

    public Page<SegDiagnosticoDto> listarDiagnosticos(String query, Boolean esCronico, Pageable pageable) {
        return diagnosticoRepository.listarDiagnosticos(query, esCronico, pageable);
    }

    public void anular(Long id) {
        SegEnfermedad enfermedadUpdate = diagnosticoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("No se encontró el diagnóstico con el ID " + id));
        enfermedadUpdate.setEstadoId(Estado.ELIMINADO);
        diagnosticoRepository.save(enfermedadUpdate);
    }
}
