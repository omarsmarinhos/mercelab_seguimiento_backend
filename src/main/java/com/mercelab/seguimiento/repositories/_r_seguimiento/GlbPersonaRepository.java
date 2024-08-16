package com.mercelab.seguimiento.repositories._r_seguimiento;


import com.mercelab.seguimiento.models._m_seguimiento.GlbPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlbPersonaRepository extends JpaRepository<GlbPersona,Long> {
        GlbPersona findByPersNdocumentoidentidad(String numeroDocIdent);

}
