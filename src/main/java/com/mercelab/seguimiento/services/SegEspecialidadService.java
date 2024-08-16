package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.SegEspecialidad;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegEspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SegEspecialidadService {

    @Autowired
    private SegEspecialidadRepository especialidadRepository;

    public Page<SegEspecialidad> listarEspecialidades(String q, Pageable pageable) {
        return especialidadRepository.listarEspecialidades(q, pageable);
    }

}
