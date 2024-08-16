package com.mercelab.seguimiento.controllers._c_diagnostico;

import com.mercelab.seguimiento._setting._api.ApiResponse;
import com.mercelab.seguimiento._setting._api.exceptions.NotValidException;
import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimientoVacuna;
import com.mercelab.seguimiento.models._m_seguimiento.dto.*;
import com.mercelab.seguimiento.services.*;
import com.mercelab.seguimiento.validator.SeguimientoEspecialidadesFechaUnicaValidator;
import com.mercelab.seguimiento.validator.SeguimientoExamenesFechaUnicaValidator;
import com.mercelab.seguimiento.validator.SeguimientoMedicamentosFechaUnicaValidator;
import com.mercelab.seguimiento.validator.SeguimientoVacunasFechaUnicaValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pre/seguimiento")
public class PreSeguimientoApiController implements ApiResponse {

    @Autowired
    private SegSeguimientoService seguimientoService;

    @Autowired
    private SegSeguimientoExamenService seguimientoExamenService;

    @Autowired
    private SegSeguimientoMedicamentoService seguimientoMedicamentoService;

    @Autowired
    private SegSeguimientoVacunaService seguimientoVacunaService;

    @Autowired
    private SegSeguimientoEspecialidadService seguimientoEspecialidadService;

    @Autowired
    private SeguimientoExamenesFechaUnicaValidator examenFechaUnicaValidator;

    @Autowired
    private SeguimientoMedicamentosFechaUnicaValidator medicamentoFechaUnicaValidator;

    @Autowired
    private SeguimientoVacunasFechaUnicaValidator vacunaFechaUnicaValidator;

    @Autowired
    private SeguimientoEspecialidadesFechaUnicaValidator especialidadesFechaUnicaValidator;

    @GetMapping("/{id}")
    public ResponseEntity<Object> verSeguimientoPorId(@PathVariable Long id) {
        Optional<SegSeguimientoDto> seguimientoDtoOptional = seguimientoService.getPorId(id);

        if (seguimientoDtoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return showOne(seguimientoDtoOptional.get());
    }

    @PostMapping
    public ResponseEntity<Object> crearSeguimiento(@Valid @RequestBody SegSeguimientoDto seguimientoDto
            , BindingResult result) throws NotValidException {
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }

        SegSeguimientoDto newSeguimiento = seguimientoService.guardar(seguimientoDto);
        return savedSuccessfully(newSeguimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editarSeguimiento(@PathVariable Long id
            , @Valid @RequestBody SegSeguimientoDto seguimientoDto
            , BindingResult result) throws NotValidException {
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }

        SegSeguimientoDto newSeguimiento = seguimientoService.editar(id, seguimientoDto);
        return savedSuccessfully(newSeguimiento);
    }

    @PostMapping("/examen")
    public ResponseEntity<Object> guardarTratamientosExamenes(@Valid @RequestBody SeguimientoExamenListWrapper list
            , BindingResult result) throws NotValidException {
        examenFechaUnicaValidator.validate(list, result);
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }
        seguimientoExamenService.guardar(list.getSeguimientoExamenList());
        return savedSuccessfully();
    }

    @GetMapping("/examen/{seguimientoId}")
    public ResponseEntity<Object> getTratamientosExamenesUnicosAsignados(
            @PathVariable(name = "seguimientoId") Long seguimientoId) {
        return showAll(seguimientoExamenService.getTratamientoExamenesUnicosAsignados(seguimientoId));
    }

    @GetMapping("/examen/{seguimientoId}/{tratamientoExamenId}")
    public ResponseEntity<Object> getTratamientosExamenesAsignados(
            @PathVariable(name = "seguimientoId") Long seguimientoId,
            @PathVariable(name = "tratamientoExamenId") Long tratamientoExamenId) {
        return showAll(seguimientoExamenService.getTratamientoExamenesAsignados(seguimientoId, tratamientoExamenId));
    }

    @PutMapping("/anular/examen/{id}")
    public ResponseEntity<Object> anularSeguimientoExamen(@PathVariable Long id) {
        seguimientoExamenService.anular(id);
        return deleteSuccessfully();
    }

    @PutMapping("/anular/examenes/{seguimientoId}/{tratamientoId}")
    public ResponseEntity<Object> anularTodoTratamientoExamen(
            @PathVariable Long seguimientoId,
            @PathVariable Long tratamientoId) {
        seguimientoExamenService.anularTodoTratamientoExamen(seguimientoId, tratamientoId);
        return deleteSuccessfully();
    }

    @PostMapping("/medicamento")
    public ResponseEntity<Object> guardarTratamientosMedicamentos(
            @Valid @RequestBody SeguimientoMedicamentoListWrapper list,
            BindingResult result) throws NotValidException {
        medicamentoFechaUnicaValidator.validate(list, result);
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }
        seguimientoMedicamentoService.guardar(list.getSeguimientoMedicamentoList());
        return savedSuccessfully();
    }

    @GetMapping("/medicamento/{seguimientoId}")
    public ResponseEntity<Object> getTratamientosMedicamentosUnicosAsignados(
            @PathVariable(name = "seguimientoId") Long seguimientoId) {
        return showAll(seguimientoMedicamentoService.getTratamientoMedicamentosUnicosAsignados(seguimientoId));
    }

    @GetMapping("/medicamento/{seguimientoId}/{tratamientoMedicamentoId}")
    public ResponseEntity<Object> getTratamientosMedicamentosAsignados(
            @PathVariable(name = "seguimientoId") Long seguimientoId,
            @PathVariable(name = "tratamientoMedicamentoId") Long tratamientoMedicamentoId) {
        return showAll(seguimientoMedicamentoService.getTratamientoMedicamentosAsignados(seguimientoId, tratamientoMedicamentoId));
    }

    @PutMapping("/anular/medicamento/{id}")
    public ResponseEntity<Object> anularSeguimientoMedicamento(@PathVariable Long id) {
        seguimientoMedicamentoService.anular(id);
        return deleteSuccessfully();
    }

    @PutMapping("/anular/medicamentos/{seguimientoId}/{tratamientoId}")
    public ResponseEntity<Object> anularTodoTratamientoMedicamento(
            @PathVariable Long seguimientoId,
            @PathVariable Long tratamientoId) {
        seguimientoMedicamentoService.anularTodoTratamientoMedicamento(seguimientoId, tratamientoId);
        return deleteSuccessfully();
    }

    @PostMapping("/vacuna")
    public ResponseEntity<Object> guardarTratamientosVacunas(@Valid @RequestBody SeguimientoVacunaListWrapper list
            , BindingResult result) throws NotValidException {
        vacunaFechaUnicaValidator.validate(list, result);
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }
        seguimientoVacunaService.guardar(list.getSeguimientoVacunaList());
        return savedSuccessfully();
    }

    @GetMapping("/vacuna/{seguimientoId}")
    public ResponseEntity<Object> getTratamientosVacunasUnicasAsignadas(
            @PathVariable(name = "seguimientoId") Long seguimientoId) {
        return showAll(seguimientoVacunaService.getTratamientoVacunasUnicasAsignadas(seguimientoId));
    }

    @GetMapping("/vacuna/{seguimientoId}/{tratamientoVacunaId}")
    public ResponseEntity<Object> getTratamientosVacunasAsignadas(
            @PathVariable(name = "seguimientoId") Long seguimientoId,
            @PathVariable(name = "tratamientoVacunaId") Long tratamientoVacunaId) {
        return showAll(seguimientoVacunaService.getTratamientoVacunasAsignadas(seguimientoId, tratamientoVacunaId));
    }

    @PutMapping("/anular/vacuna/{id}")
    public ResponseEntity<Object> anularSeguimientoVacuna(@PathVariable Long id) {
        seguimientoVacunaService.anular(id);
        return deleteSuccessfully();
    }

    @PutMapping("/anular/vacunas/{seguimientoId}/{tratamientoId}")
    public ResponseEntity<Object> anularTodoTratamientoVacuna(
            @PathVariable Long seguimientoId,
            @PathVariable Long tratamientoId) {
        seguimientoVacunaService.anularTodoTratamientoVacuna(seguimientoId, tratamientoId);
        return deleteSuccessfully();
    }

    @PostMapping("/especialidad")
    public ResponseEntity<Object> guardarTratamientosEspecialidades(@Valid @RequestBody SeguimientoEspecialidadListWrapper list
            , BindingResult result) throws NotValidException {
        especialidadesFechaUnicaValidator.validate(list, result);
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }
        seguimientoEspecialidadService.guardar(list.getSeguimientoEspecialidadList());
        return savedSuccessfully();
    }

    @GetMapping("/especialidad/{seguimientoId}")
    public ResponseEntity<Object> getTratamientosEspecialidadesUnicasAsignadas(
            @PathVariable(name = "seguimientoId") Long seguimientoId) {
        return showAll(seguimientoEspecialidadService.getTratamientoEspecialidadesUnicosAsignados(seguimientoId));
    }

    @GetMapping("/especialidad/{seguimientoId}/{tratamientoEspecialidadId}")
    public ResponseEntity<Object> getTratamientosEspecialidadesAsignadas(
            @PathVariable(name = "seguimientoId") Long seguimientoId,
            @PathVariable(name = "tratamientoEspecialidadId") Long tratamientoVacunaId) {
        return showAll(seguimientoEspecialidadService.getTratamientoEspecialidadesAsignados(seguimientoId, tratamientoVacunaId));
    }

    @PutMapping("/anular/especialidad/{id}")
    public ResponseEntity<Object> anularSeguimientoEspecialidad(@PathVariable Long id) {
        seguimientoEspecialidadService.anular(id);
        return deleteSuccessfully();
    }

    @PutMapping("/anular/especialidades/{seguimientoId}/{tratamientoId}")
    public ResponseEntity<Object> anularTodoTratamientoEspecialidad(
            @PathVariable Long seguimientoId,
            @PathVariable Long tratamientoId) {
        seguimientoEspecialidadService.anularTodoTratamientoEspecialidad(seguimientoId, tratamientoId);
        return deleteSuccessfully();
    }
}
