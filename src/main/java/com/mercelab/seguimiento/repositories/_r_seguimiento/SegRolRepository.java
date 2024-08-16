package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.Estado;
import com.mercelab.seguimiento.models._m_seguimiento.SegRol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SegRolRepository extends JpaRepository<SegRol, Long> {

    @Query("select r from SegRol r " +
            "where r.estadoId = 1 " +
            " and :termino is null or r.nombre like %:termino%")
    Page<SegRol> getRoles(String termino, Pageable pageable);

}
