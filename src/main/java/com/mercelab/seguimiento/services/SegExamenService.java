package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.SegExamen;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SegExamenService {

    @Autowired
    private SegExamenRepository examenRepository;

    public Page<SegExamen> listarExamenes(String q, Pageable pageable) {
        return examenRepository.listarExamenes(q, pageable);
    }

}
