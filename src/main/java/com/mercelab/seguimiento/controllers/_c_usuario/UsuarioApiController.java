package com.mercelab.seguimiento.controllers._c_usuario;


import com.mercelab.seguimiento._setting._api.ApiResponse;
import com.mercelab.seguimiento._setting._api.exceptions.NotValidException;
import com.mercelab.seguimiento.controllers.helpers.PaginationResponseUtil;
import com.mercelab.seguimiento.models._m_seguimiento.SegUsuario;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegUsuarioRequestDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegUsuarioTableDto;
import com.mercelab.seguimiento.services.SegUsuarioService;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioApiController implements ApiResponse {

    @Autowired
    private SegUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Object> getUsuarios(
            @RequestParam(value = "q", required = false) String query
            , @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SegUsuarioTableDto> usuarios = usuarioService.listarUsuarios(query, pageable);
        Map<String, Object> response = PaginationResponseUtil.generatePaginationResponse(usuarios);
        return showOne(response);
    }

    @GetMapping("/roles")
    public ResponseEntity<Object> getRoles() {
        return showOne(usuarioService.listarRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Optional<SegUsuarioRequestDto> usuarioOptional = usuarioService.getUserById(id);
        if (usuarioOptional.isEmpty()) {
            throw new EntityNotFoundException("Usuario con id " + id + " no encontrado.");
        }
        return showOne(usuarioOptional.get());
    }

    @PutMapping("/anular/{id}")
    public ResponseEntity<Object> anularUsuario(@PathVariable Long id) {
        usuarioService.anular(id);
        return deleteSuccessfully();
    }

    @PostMapping
    public ResponseEntity<Object> guardarUsuario(
            @Valid @RequestBody SegUsuarioRequestDto usuarioDto,
            BindingResult result) throws NotValidException {
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }
        return showOne(usuarioService.guardar(usuarioDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody SegUsuarioRequestDto usuarioDto,
            BindingResult result) throws NotValidException {
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }
        return showOne(usuarioService.editar(id, usuarioDto));
    }
}
