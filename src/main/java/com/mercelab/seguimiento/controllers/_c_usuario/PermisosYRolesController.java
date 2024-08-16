package com.mercelab.seguimiento.controllers._c_usuario;

import com.mercelab.seguimiento._setting._api.ApiResponse;
import com.mercelab.seguimiento._setting._api.exceptions.NotValidException;
import com.mercelab.seguimiento.controllers.helpers.PaginationResponseUtil;
import com.mercelab.seguimiento.models._m_seguimiento.SegRol;
import com.mercelab.seguimiento.models._m_seguimiento.dto.Rol;
import com.mercelab.seguimiento.services.SegPermisosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/permisos")
public class PermisosYRolesController implements ApiResponse {

    @Autowired
    private SegPermisosService permisosService;

    @GetMapping("/{rolId}")
    public ResponseEntity<Object> getPermisosYRoles(@PathVariable Long rolId) {
        return showOne(permisosService.getPermisosYRoles(rolId));
    }

    @GetMapping("/menus")
    public ResponseEntity<Object> getMenus() {
        return showOne(permisosService.getMenus());
    }

    @GetMapping("/roles")
    public ResponseEntity<Object> gerRoles(
            @RequestParam(value = "q", required = false) String query,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<SegRol> roles = permisosService.getRoles(query, pageable);
        Map<String, Object> response = PaginationResponseUtil.generatePaginationResponse(roles);
        return showOne(response);
    }

    @PostMapping("/rol")
    public ResponseEntity<Object> guardarRol(@Valid @RequestBody Rol rol, BindingResult result) throws NotValidException {
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }
        permisosService.guardar(rol);
        return savedSuccessfully();
    }

}
