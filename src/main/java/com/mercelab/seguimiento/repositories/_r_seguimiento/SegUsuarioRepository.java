package com.mercelab.seguimiento.repositories._r_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegMenuRoles;
import com.mercelab.seguimiento.models._m_seguimiento.SegUsuario;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegUsuarioTableDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SegUsuarioRepository extends JpaRepository<SegUsuario, Long> {

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SegUsuarioTableDto(" +
            "u.id, u.persona.apellidoPaterno, u.persona.apellidoMaterno, u.persona.nombres" +
            ", u.persona.email, u.rol.nombre) " +
            "FROM SegUsuario u " +
            "WHERE u.persona.email =:email")
    Optional<SegUsuarioTableDto> getByEmail(String email);

    @Query("SELECT mr FROM SegMenuRoles mr " +
            "WHERE mr.rol.id =:rolId")
    List<SegMenuRoles> getPermisos(Long rolId);

    @Query("SELECT new com.mercelab.seguimiento.models._m_seguimiento.dto.SegUsuarioTableDto(" +
            "u.id, u.persona.apellidoPaterno, u.persona.apellidoMaterno, u.persona.nombres" +
            ", u.persona.email, u.rol.nombre) " +
            "FROM SegUsuario u WHERE u.estadoId = 1 " +
            "AND (:query is null or u.persona.apellidoPaterno LIKE %:query% " +
            "or u.persona.email LIKE %:query%)")
    Page<SegUsuarioTableDto> getPaginado(String query, Pageable pageable);
}
