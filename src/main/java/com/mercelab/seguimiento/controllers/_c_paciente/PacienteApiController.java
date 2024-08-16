package com.mercelab.seguimiento.controllers._c_paciente;


import com.mercelab.seguimiento._setting._api.ApiResponse;
import com.mercelab.seguimiento._setting._api.exceptions.NotValidException;
import com.mercelab.seguimiento.controllers.helpers.PaginationResponseUtil;
import com.mercelab.seguimiento.models._m_seguimiento.SegPaciente;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegPacienteDto;
import com.mercelab.seguimiento.services.SegPacienteService;
import com.mercelab.seguimiento.services.ServiceGlbTipodocumento;
import com.mercelab.seguimiento.validator.SegPersonaDtoValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteApiController implements ApiResponse {

    @Autowired
    private ServiceGlbTipodocumento serviceGlbTipodocumento;
    @Autowired
    private SegPacienteService pacienteService;
    @Autowired
    private SegPersonaDtoValidator segPersonaDtoValidator;

    @GetMapping("/predata")
    public ResponseEntity<Object> getDefault() {
        Map<String, Object> response = new HashMap<>();
        response.put("glbTipodocumento", serviceGlbTipodocumento.findAll());
        return showOne(response);
    }

    @GetMapping
    public ResponseEntity<Object> getAllPacientes(@RequestParam(value = "q", required = false) String query,
                                                  @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SegPacienteDto> pacientes = pacienteService.listarPaginacionyBusqueda(query, pageable);
        Map<String, Object> response = PaginationResponseUtil.generatePaginationResponse(pacientes);
        return showOne(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> buscarPacientePorId(@PathVariable Long id) {
        Optional<SegPacienteDto> optionalPaciente = pacienteService.buscarPorId(id);

        if (optionalPaciente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (optionalPaciente.get().getEstadoId() != 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El paciente está anulado");
        }

        return showOne(optionalPaciente.get());
    }

    @PostMapping()
    public ResponseEntity<Object> crearPaciente(@Valid @RequestBody SegPacienteDto pacienteDto
            , BindingResult result) throws NotValidException {
        segPersonaDtoValidator.validate(pacienteDto.getPersona(), result);
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }
        SegPacienteDto newPacienteDto = pacienteService.guardar(pacienteDto);
        return savedSuccessfully(newPacienteDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editarPaciente(@PathVariable Long id
            , @Valid @RequestBody SegPacienteDto pacienteDto
            , BindingResult result) throws NotValidException {
        segPersonaDtoValidator.validate(pacienteDto.getPersona(), result);
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }

        SegPacienteDto newPacienteDto = pacienteService.editar(id, pacienteDto);

        return updateSuccessfully(newPacienteDto);
    }

    @PutMapping("/anular/{id}")
    public ResponseEntity<Object> anularPaciente(@PathVariable Long id) {
        SegPacienteDto newPacienteDto = pacienteService.anular(id);
        return deleteSuccessfully();
    }

    @GetMapping("/verificar/{nro}")
    public ResponseEntity<Object> verificarSiExistePacientePorNroDocumento(@PathVariable String nro) {
        Optional<SegPaciente> pacienteOptional = pacienteService.buscarPorNumeroDocumento(nro);

        if (pacienteOptional.isPresent()) {
            return showOne("Este número de documento ya está registrado.");
        } else {
            return showOne("");
        }

    }
}
