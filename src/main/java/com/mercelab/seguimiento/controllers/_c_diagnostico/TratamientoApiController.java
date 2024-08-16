package com.mercelab.seguimiento.controllers._c_diagnostico;

import com.mercelab.seguimiento._setting._api.ApiResponse;
import com.mercelab.seguimiento._setting._api.exceptions.NotValidException;
import com.mercelab.seguimiento.controllers.helpers.PaginationResponseUtil;
import com.mercelab.seguimiento.models._m_seguimiento.SegEspecialidad;
import com.mercelab.seguimiento.models._m_seguimiento.SegExamen;
import com.mercelab.seguimiento.models._m_seguimiento.SegVacuna;
import com.mercelab.seguimiento.models._m_seguimiento.dto.*;
import com.mercelab.seguimiento.services.*;
import com.mercelab.seguimiento.validator.TratamientoEspecialidadUnicaValidator;
import com.mercelab.seguimiento.validator.TratamientoExamenUnicoValidator;
import com.mercelab.seguimiento.validator.TratamientoMedicamentoUnicoValidator;
import com.mercelab.seguimiento.validator.TratamientoVacunaUnicaValidator;
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
@RequestMapping("tratamiento")
public class TratamientoApiController implements ApiResponse {

    @Autowired
    private SegTratamientoService tratamientoService;

    @Autowired
    private SegTratamientoExamenService tratamientoExamenService;

    @Autowired
    private SegTratamientoMedicamentoService tratamientoMedicamentoService;

    @Autowired
    private SegTratamientoVacunaService tratamientoVacunaService;

    @Autowired
    private SegTratamientoEspecialidadService tratamientoEspecialidadService;

    @Autowired
    private SegExamenService examenService;

    @Autowired
    private SegVacunaService vacunaService;

    @Autowired
    private SegEspecialidadService especialidadService;

    @Autowired
    private TratamientoExamenUnicoValidator tratamienoExamenUnicoValidator;

    @Autowired
    private TratamientoMedicamentoUnicoValidator tratamientoMedicamentoUnicoValidator;

    @Autowired
    private TratamientoVacunaUnicaValidator tratamientoVacunaUnicaValidator;

    @Autowired
    private TratamientoEspecialidadUnicaValidator tratamientoEspecialidadUnicaValidator;

    @GetMapping("/{id}")
    public ResponseEntity<Object> verTratamientoPorId(@PathVariable Long id) {
        Optional<SegTratamientoDto> tratamientoDtoOptional = tratamientoService.getPorId(id);

        if (tratamientoDtoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return showOne(tratamientoDtoOptional.get());
    }

    @PostMapping
    public ResponseEntity<Object> crearTratamiento(@Valid @RequestBody SegTratamientoDto tratamientoDto
            , BindingResult result) throws NotValidException{
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }

        SegTratamientoDto newTratamiento = tratamientoService.guardar(tratamientoDto);
        return savedSuccessfully(newTratamiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editarTratamiento(@PathVariable Long id
            , @Valid @RequestBody SegTratamientoDto tratamientoDto
            , BindingResult result) throws NotValidException{
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }

        SegTratamientoDto newTratamiento = tratamientoService.editar(id, tratamientoDto);
        return savedSuccessfully(newTratamiento);
    }

    @GetMapping("/examen")
    public ResponseEntity<Object> getAllExamenes(
            @RequestParam(value = "q", required = false) String query
            , @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<SegExamen> examenesPage = examenService.listarExamenes(query, pageable);
        Map<String, Object> response = PaginationResponseUtil.generatePaginationResponse(examenesPage);
        return showOne(response);
    }

    @GetMapping("/examen/{id}")
    public ResponseEntity<Object> getExamenesAsignados(@PathVariable(name = "id") Long id) {
        return showAll(tratamientoExamenService.getExamenesAsignados(id));
    }
    @PostMapping("/examen")
    public ResponseEntity<Object> guardarExamen(@Valid @RequestBody SegTratamientoExamenDto tratamientoExamenDto
            , BindingResult result) throws NotValidException {
        tratamienoExamenUnicoValidator.validate(tratamientoExamenDto, result);
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }

        SegTratamientoExamenDto newTratamientoExamenDto = tratamientoExamenService.agregarExamen(tratamientoExamenDto);
        return savedSuccessfully(newTratamientoExamenDto);
    }

    @PutMapping("/examen/{id}")
    public ResponseEntity<Object> anularExamen(@PathVariable Long id) {
        tratamientoExamenService.anular(id);
        return deleteSuccessfully();
    }

    @GetMapping("/medicamento/{id}")
    public ResponseEntity<Object> getMedicamentosAsignados(@PathVariable(name = "id") Long id) {
        return showAll(tratamientoMedicamentoService.getMedicamentosAsignados(id));
    }
    @PostMapping("/medicamento")
    public ResponseEntity<Object> guardarMedicamento(@Valid @RequestBody SegTratamientoMedicamentoDto tratamientoMedicamentoDto
            , BindingResult result) throws NotValidException {
        tratamientoMedicamentoUnicoValidator.validate(tratamientoMedicamentoDto, result);
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }

        SegTratamientoMedicamentoDto newTratamientoMedicamentoDto = tratamientoMedicamentoService.agregarMedicamento(tratamientoMedicamentoDto);
        return savedSuccessfully(newTratamientoMedicamentoDto);
    }

    @PutMapping("/medicamento/{id}")
    public ResponseEntity<Object> anularMedicamento(@PathVariable Long id) {
        tratamientoMedicamentoService.anular(id);
        return deleteSuccessfully();
    }

    @GetMapping("/vacuna")
    public ResponseEntity<Object> getAllVacunas(
            @RequestParam(value = "q", required = false) String query
            , @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<SegVacuna> vacunasPage = vacunaService.listarVacunas(query, pageable);
        Map<String, Object> response = PaginationResponseUtil.generatePaginationResponse(vacunasPage);
        return showOne(response);
    }

    @GetMapping("/vacuna/{id}")
    public ResponseEntity<Object> getVacunasAsignadas(@PathVariable(name = "id") Long id) {
        return showAll(tratamientoVacunaService.getVacunasAsignadas(id));
    }
    @PostMapping("/vacuna")
    public ResponseEntity<Object> guardarVacuna(@Valid @RequestBody SegTratamientoVacunaDto tratamientoVacunaDto
            , BindingResult result) throws NotValidException {
        tratamientoVacunaUnicaValidator.validate(tratamientoVacunaDto, result);
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }

        SegTratamientoVacunaDto newTratamientoVacunaDto = tratamientoVacunaService.agregarVacuna(tratamientoVacunaDto);
        return savedSuccessfully(newTratamientoVacunaDto);
    }

    @PutMapping("/vacuna/{id}")
    public ResponseEntity<Object> anularVacuna(@PathVariable Long id) {
        tratamientoVacunaService.anular(id);
        return deleteSuccessfully();
    }

    @GetMapping("/especialidad")
    public ResponseEntity<Object> getAllEspecialidades(
            @RequestParam(value = "q", required = false) String query
            , @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<SegEspecialidad> especialidadPage = especialidadService.listarEspecialidades(query, pageable);
        Map<String, Object> response = PaginationResponseUtil.generatePaginationResponse(especialidadPage);
        return showOne(response);
    }

    @GetMapping("/especialidad/{id}")
    public ResponseEntity<Object> getEspecialidadesAsignadas(@PathVariable(name = "id") Long id) {
        return showAll(tratamientoEspecialidadService.getEspecialidadesAsignadas(id));
    }
    @PostMapping("/especialidad")
    public ResponseEntity<Object> guardarEspecialidad(@Valid @RequestBody SegTratamientoEspecialidadDto tratamientoEspecialidadDto
            , BindingResult result) throws NotValidException {
        tratamientoEspecialidadUnicaValidator.validate(tratamientoEspecialidadDto, result);
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }

        SegTratamientoEspecialidadDto newTratamientoEspecialidadDto = tratamientoEspecialidadService.agregarEspecialidad(tratamientoEspecialidadDto);
        return savedSuccessfully(newTratamientoEspecialidadDto);
    }

    @PutMapping("/especialidad/{id}")
    public ResponseEntity<Object> anularEspecialidad(@PathVariable Long id) {
        tratamientoEspecialidadService.anular(id);
        return deleteSuccessfully();
    }

}
