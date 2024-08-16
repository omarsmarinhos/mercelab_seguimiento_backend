package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegMenu;
import com.mercelab.seguimiento.models._m_seguimiento.SegMenuRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SegPermisosRepository extends JpaRepository<SegMenuRoles, Long> {

    @Query("SELECT mr FROM SegMenuRoles mr " +
            "WHERE mr.rol.id =:rolId")
    List<SegMenuRoles> getPermisosYRoles(Long rolId);

    @Query("SELECT m FROM SegMenu m " +
            "WHERE m.estadoId = 1")
    List<SegMenu> getMenus();

}
