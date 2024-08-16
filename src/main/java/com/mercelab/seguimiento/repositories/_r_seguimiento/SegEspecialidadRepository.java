package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegEspecialidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SegEspecialidadRepository extends JpaRepository<SegEspecialidad, Long> {

    @Query("SELECT e FROM SegEspecialidad e WHERE " +
            "(:termino is null) or " +
            "e.descripcion LIKE %:termino%")
    Page<SegEspecialidad> listarEspecialidades(String termino, Pageable pageable);

}
