package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegDistrito;
import com.mercelab.seguimiento.models._m_seguimiento.SegProvincia;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegUbigeoDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SegUbigeoRepository extends JpaRepository<SegDistrito, Long> {

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SegUbigeoDto(" +
            "dis.id, dis.descripcion, pro.descripcion, dep.descripcion)" +
            "FROM SegDistrito dis " +
            "INNER JOIN dis.provincia pro " +
            "INNER JOIN pro.departamento dep " +
            "WHERE dis.descripcion LIKE %:buscar%")
    List<SegUbigeoDto> getDistritos(@Param("buscar") String buscar, Pageable pageable);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SegUbigeoDto(" +
            "dis.id, dis.descripcion, pro.descripcion, dep.descripcion)" +
            "FROM SegDistrito dis " +
            "INNER JOIN dis.provincia pro " +
            "INNER JOIN pro.departamento dep " +
            "WHERE dis.id =:id")
    SegUbigeoDto getPorId(@Param("id") Long id);



}
