package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegCie10;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SegCie10Repository extends JpaRepository<SegCie10, Long> {

    @Query("SELECT c FROM SegCie10 c WHERE " +
            "(:termino is null) or c.codigoCie10 LIKE %:termino% OR " +
            "c.descripcion LIKE %:termino%")
    Page<SegCie10> listarCie10(@Param("termino") String termino, Pageable pageable);

}
