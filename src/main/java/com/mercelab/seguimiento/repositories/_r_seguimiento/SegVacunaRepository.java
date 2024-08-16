package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegVacuna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SegVacunaRepository extends JpaRepository<SegVacuna, Long> {

    @Query("SELECT v FROM SegVacuna v WHERE " +
            "(:termino is null) or v.descripcion LIKE %:termino%")
    Page<SegVacuna> listarVacunas(@Param("termino") String termino, Pageable pageable);

}
