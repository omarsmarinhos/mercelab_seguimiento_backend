package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.SegVacuna;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegVacunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SegVacunaService {

    @Autowired
    private SegVacunaRepository vacunaRepository;

    public Page<SegVacuna> listarVacunas(String q, Pageable pageable) {
        return vacunaRepository.listarVacunas(q, pageable);
    }

}
