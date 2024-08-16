package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegExamen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SegExamenRepository extends JpaRepository<SegExamen, Long> {

    @Query("SELECT e FROM SegExamen e WHERE " +
            "(:termino is null) or e.codigo LIKE %:termino% OR " +
            "e.descripcion LIKE %:termino%")
    Page<SegExamen> listarExamenes(@Param("termino") String termino, Pageable pageable);

}
