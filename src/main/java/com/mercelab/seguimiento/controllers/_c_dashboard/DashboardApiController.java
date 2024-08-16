package com.mercelab.seguimiento.controllers._c_dashboard;

import com.mercelab.seguimiento._setting._api.ApiResponse;
import com.mercelab.seguimiento.controllers.helpers.GraficoResponseUtil;
import com.mercelab.seguimiento.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardApiController implements ApiResponse {

    @Autowired
    private SegPacienteService pacienteService;

    @Autowired
    private SegSeguimientoExamenService seguimientoExamenService;

    @Autowired
    private SegSeguimientoMedicamentoService seguimientoMedicamentoService;

    @Autowired
    private SegSeguimientoVacunaService seguimientoVacunaService;

    @Autowired
    private SegSeguimientoEspecialidadService seguimientoEspecialidadService;

    @GetMapping("")
    public ResponseEntity<Object> count() {
        Map<String, Object> response = new HashMap<>();
        Long seguimientosHoy = seguimientoExamenService.countSeguimientosParaHoy() +
                seguimientoMedicamentoService.countSeguimientosParaHoy() +
                seguimientoVacunaService.countSeguimientosParaHoy() +
                seguimientoEspecialidadService.countSeguimientosParaHoy();
        Long seguimientosAtrasados = seguimientoExamenService.countSeguimientosAtrasados() +
                seguimientoMedicamentoService.countSeguimientosAtrasados() +
                seguimientoVacunaService.countSeguimientosAtrasados() +
                seguimientoEspecialidadService.countSeguimientosAtrasados();
        Long seguimientoSemana = seguimientoExamenService.countSeguimientosProximosDias(6) +
                seguimientoMedicamentoService.countSeguimientosProximosDias(6) +
                seguimientoVacunaService.countSeguimientosProximosDias(6) +
                seguimientoEspecialidadService.countSeguimientosProximosDias(6);
        response.put("pacientes", pacienteService.countPacientes());
        response.put("seguimientosHoy", seguimientosHoy);
        response.put("seguimientosAtrasados", seguimientosAtrasados);
        response.put("seguimientosSemana", seguimientoSemana);
        response.put("seguimientosTotal", seguimientosHoy + seguimientosAtrasados + seguimientoSemana);
        return showOne(response);
    }

    @GetMapping("/grafico")
    public ResponseEntity<Object> getGrafico() {
        Map<String, Object> response = GraficoResponseUtil.graficoBarraResponse(
                seguimientoExamenService.getCantidadDeSeguimientosTerminadosPorMes(),
                seguimientoMedicamentoService.getCantidadDeSeguimientosTerminadosPorMes(),
                seguimientoVacunaService.getCantidadDeSeguimientosTerminadosPorMes(),
                seguimientoEspecialidadService.getCantidadDeSeguimientosTerminadosPorMes()
        );
        return showOne(response);
    }

    @GetMapping("/grafico/circular")
    public ResponseEntity<Object> getGraficoCircular() {
        Map<String, Object> response = GraficoResponseUtil.graficoCircularResponse(
                seguimientoExamenService.getSeguimientos(),
                seguimientoMedicamentoService.getSeguimientos(),
                seguimientoVacunaService.getSeguimientos(),
                seguimientoEspecialidadService.getSeguimientos()
        );
        return showOne(response);
    }


}
