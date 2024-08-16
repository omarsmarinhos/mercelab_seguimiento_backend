package com.mercelab.seguimiento.controllers._c_reporte;

import com.mercelab.seguimiento._setting._api.ApiResponse;
import com.mercelab.seguimiento.controllers.helpers.PaginationResponseUtil;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegReporteHistorialDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegReportePacienteDto;
import com.mercelab.seguimiento.services.SegReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reporte")
public class ReporteApiController implements ApiResponse {

    @Autowired
    private SegReporteService reporteService;

    @GetMapping("/paciente")
    public ResponseEntity<Object> getPacientesConSeguimientoConcluido(@RequestParam(value = "q", required = false) String query,
                                      @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SegReportePacienteDto> pacientes = reporteService.getPacientesConSeguimientoConcluido(query, pageable);
        Map<String, Object> response = PaginationResponseUtil.generatePaginationResponse(pacientes);
        return showOne(response);
    }

    @GetMapping("/historial/{pacienteId}")
    public ResponseEntity<Object> getHistorialDePaciente(@PathVariable Long pacienteId,
            @PageableDefault(size = 20) Pageable pageable) {
        List<SegReporteHistorialDto> historial = reporteService.getSeguimientosConcluidosPorPaciente(pacienteId);
        return showAll(historial);
    }

}
