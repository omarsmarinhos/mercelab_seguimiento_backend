package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SegPersonaRepository extends JpaRepository<SegPersona, Long> {

    Optional<SegPersona> findByNumeroDocumento(String nroDocumento);
}
