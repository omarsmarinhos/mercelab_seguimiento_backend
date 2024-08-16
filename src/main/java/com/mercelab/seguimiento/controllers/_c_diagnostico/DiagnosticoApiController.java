package com.mercelab.seguimiento.controllers._c_diagnostico;


import com.mercelab.seguimiento._setting._api.ApiResponse;
import com.mercelab.seguimiento.controllers.helpers.PaginationResponseUtil;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegDiagnosticoDto;
import com.mercelab.seguimiento.services.SegDiagnosticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/diagnostico")
public class DiagnosticoApiController implements ApiResponse {

    @Autowired
    private SegDiagnosticoService diagnosticoService;

    @GetMapping
    public ResponseEntity<Object> getAllDiagnosticos(
            @RequestParam(value = "q", required = false) String query
            , @RequestParam(value = "esCronico", required = false) Boolean esCronico
            , @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SegDiagnosticoDto> pacientes = diagnosticoService.listarDiagnosticos(query, esCronico, pageable);
        Map<String, Object> response = PaginationResponseUtil.generatePaginationResponse(pacientes);
        return showOne(response);
    }

    @PutMapping("/anular/{id}")
    public ResponseEntity<Object> anular(@PathVariable Long id) {
        diagnosticoService.anular(id);
        return deleteSuccessfully();
    }

}
