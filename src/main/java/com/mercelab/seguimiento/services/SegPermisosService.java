package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.SegMenu;
import com.mercelab.seguimiento.models._m_seguimiento.SegMenuRoles;
import com.mercelab.seguimiento.models._m_seguimiento.SegRol;
import com.mercelab.seguimiento.models._m_seguimiento.dto.Rol;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegPermisosRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegRolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SegPermisosService {

    @Autowired
    private SegPermisosRepository permisosRepository;

    @Autowired
    private SegRolRepository rolRepository;

    public List<SegMenuRoles> getPermisosYRoles(Long rolId) {
        return permisosRepository.getPermisosYRoles(rolId);
    }

    public List<SegMenu> getMenus() {
        return permisosRepository.getMenus();
    }

    public Page<SegRol> getRoles(String termino, Pageable pageable) {
        return rolRepository.getRoles(termino, pageable);
    }

    @Transactional
    public void guardar(Rol rol) {
        SegRol segRol = new SegRol();
        segRol.setNombre(rol.getNombre());
        segRol.setEsAdmin(rol.getEsAdmin());
        segRol = rolRepository.save(segRol);

        SegRol finalSegRol = segRol;
        rol.getMenus().forEach(menu -> {
            SegMenuRoles segMenuRoles = new SegMenuRoles();
            segMenuRoles.setRol(finalSegRol);
            SegMenu segMenu = new SegMenu();
            segMenu.setId(menu.getId());
            segMenuRoles.setMenu(segMenu);
            segMenuRoles.setEstadoId(Long.valueOf(menu.getEstado()));

            if (menu.getAccesoTotal() != null) {
                if (menu.getAccesoTotal()) {
                    segMenuRoles.setLeer(true);
                    segMenuRoles.setCrear(true);
                    segMenuRoles.setEditar(true);
                    segMenuRoles.setEliminar(true);
                } else {
                    segMenuRoles.setLeer(menu.getPermisos().getLeer());
                    segMenuRoles.setCrear(menu.getPermisos().getCrear());
                    segMenuRoles.setEditar(menu.getPermisos().getEditar());
                    segMenuRoles.setEliminar(menu.getPermisos().getEliminar());
                }
            } else {
                segMenuRoles.setLeer(false);
                segMenuRoles.setCrear(false);
                segMenuRoles.setEditar(false);
                segMenuRoles.setEliminar(false);
            }

            permisosRepository.save(segMenuRoles);
        });
    }


}
