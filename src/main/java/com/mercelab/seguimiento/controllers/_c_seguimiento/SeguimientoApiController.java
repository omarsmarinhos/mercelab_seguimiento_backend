package com.mercelab.seguimiento.controllers._c_seguimiento;

import com.mercelab.seguimiento._setting._api.ApiResponse;
import com.mercelab.seguimiento.controllers.helpers.PaginationResponseUtil;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto;
import com.mercelab.seguimiento.services.SegSeguimientoEspecialidadService;
import com.mercelab.seguimiento.services.SegSeguimientoExamenService;
import com.mercelab.seguimiento.services.SegSeguimientoMedicamentoService;
import com.mercelab.seguimiento.services.SegSeguimientoVacunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/seguimiento")
public class SeguimientoApiController implements ApiResponse {

    @Autowired
    private SegSeguimientoExamenService seguimientoExamenService;

    @Autowired
    private SegSeguimientoMedicamentoService seguimientoMedicamentoService;

    @Autowired
    private SegSeguimientoVacunaService seguimientoVacunaService;

    @Autowired
    private SegSeguimientoEspecialidadService seguimientoEspecialidadService;

    @GetMapping()
    public ResponseEntity<Object> getSeguimientosProximasFechasPorDias(
            @RequestParam (required = false) LocalDate fechaDesde,
            @RequestParam (required = false) LocalDate fechaHasta,
            @RequestParam (required = false) String q,
            @PageableDefault(size = 7) Pageable pageable
    ){
        Page<SeguimientosDto> seguimientosEDtoPage = seguimientoExamenService
                .getSeguimientosExamensPorFechaProxima(fechaDesde, fechaHasta, q, pageable);
        Page<SeguimientosDto> seguimientosMDtoPage = seguimientoMedicamentoService
                .getSeguimientosMedicamentosPorFechaProxima(fechaDesde, fechaHasta, q, pageable);
        Page<SeguimientosDto> seguimientosVDtoPage = seguimientoVacunaService
                .getSeguimientosVacunasPorFechaProxima(fechaDesde, fechaHasta, q, pageable);
        Page<SeguimientosDto> seguimientosEsDtoPage = seguimientoEspecialidadService
                .getSeguimientosEspecialidadesPorFechaProxima(fechaDesde, fechaHasta, q, pageable);

        Map<String, Object> response = PaginationResponseUtil.generatePaginationResponse(seguimientosEDtoPage, seguimientosMDtoPage, seguimientosVDtoPage, seguimientosEsDtoPage);
        return showOne(response);
    }

    @GetMapping("/modal")
    public ResponseEntity<Object> getSeguimientosFechasMasProximasLimit(){
        Page<SeguimientosDto> seguimientosDtoPage = seguimientoExamenService
                .getSeguimientosParaVentanaEmergente();
        Page<SeguimientosDto> seguimientosMDtoPage = seguimientoMedicamentoService
                .getSeguimientosParaVentanaEmergente();
        Page<SeguimientosDto> seguimientosVDtoPage = seguimientoVacunaService
                .getSeguimientosParaVentanaEmergente();
        Page<SeguimientosDto> seguimientosEsDtoPage = seguimientoEspecialidadService
                .getSeguimientosParaVentanaEmergente();
        Map<String, Object> response = PaginationResponseUtil.generatePaginationResponse(seguimientosDtoPage, seguimientosMDtoPage, seguimientosVDtoPage, seguimientosEsDtoPage);
        return showOne(response);
    }

    @GetMapping("/pre/notificar/se/{id}")
    public ResponseEntity<Object> getDataModalNotificarSeguimientoExamen(@PathVariable Long id) {
        return showOne(seguimientoExamenService.getDataNotificar(id));
    }

    @PutMapping("/notificar/se/{id}")
    public ResponseEntity<Object> notificarSeguimientoExamen(@PathVariable Long id, @RequestBody String respuesta) {
         seguimientoExamenService.notificarSeguimiento(id, respuesta);
         return savedSuccessfully();
    }

    @GetMapping("/pre/notificar/sm/{id}")
    public ResponseEntity<Object> getDataModalNotificarSeguimientoMedicamento(@PathVariable Long id) {
        return showOne(seguimientoMedicamentoService.getDataNotificar(id));
    }

    @PutMapping("/notificar/sm/{id}")
    public ResponseEntity<Object> notificarSeguimientoMedicamento(@PathVariable Long id, @RequestBody String respuesta) {
        seguimientoMedicamentoService.notificarSeguimiento(id, respuesta);
        return savedSuccessfully();
    }

    @GetMapping("/pre/notificar/sv/{id}")
    public ResponseEntity<Object> getDataModalNotificarSeguimientoVacuna(@PathVariable Long id) {
        return showOne(seguimientoVacunaService.getDataNotificar(id));
    }

    @PutMapping("/notificar/sv/{id}")
    public ResponseEntity<Object> notificarSeguimientoVacuna(@PathVariable Long id, @RequestBody String respuesta) {
        seguimientoVacunaService.notificarSeguimiento(id, respuesta);
        return savedSuccessfully();
    }

    @GetMapping("/pre/notificar/ses/{id}")
    public ResponseEntity<Object> getDataModalNotificarSeguimientoEspecialidad(@PathVariable Long id) {
        return showOne(seguimientoEspecialidadService.getDataNotificar(id));
    }

    @PutMapping("/notificar/ses/{id}")
    public ResponseEntity<Object> notificarSeguimientoEspecialidad(@PathVariable Long id, @RequestBody String respuesta) {
        seguimientoEspecialidadService.notificarSeguimiento(id, respuesta);
        return savedSuccessfully();
    }
}
