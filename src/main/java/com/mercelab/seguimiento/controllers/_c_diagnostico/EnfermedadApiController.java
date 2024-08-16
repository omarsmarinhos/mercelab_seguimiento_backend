package com.mercelab.seguimiento.controllers._c_diagnostico;

import com.mercelab.seguimiento._setting._api.ApiResponse;
import com.mercelab.seguimiento._setting._api.exceptions.NotValidException;
import com.mercelab.seguimiento.controllers.helpers.PaginationResponseUtil;
import com.mercelab.seguimiento.models._m_seguimiento.SegCie10;
import com.mercelab.seguimiento.models._m_seguimiento.dto.EnfermedadListWrapper;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegEnfermedadDto;
import com.mercelab.seguimiento.services.SegEnfermedadService;
import com.mercelab.seguimiento.validator.EnfermedadValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("enfermedad")
public class EnfermedadApiController implements ApiResponse {

    @Autowired
    private SegEnfermedadService enfermedadService;

    @Autowired
    private EnfermedadValidator enfermedadValidator;

    @GetMapping("/cie10")
    public ResponseEntity<Object> getAllCie10(
            @RequestParam(value = "q", required = false) String query
            , @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<SegCie10> segCie10s = enfermedadService.listarEnfermedadesCie10(query, pageable);
        Map<String, Object> response = PaginationResponseUtil.generatePaginationResponse(segCie10s);
        return showOne(response);
    }

    @PostMapping()
    public ResponseEntity<Object> agregarEnfermedadesAPaciente(
            @RequestBody EnfermedadListWrapper enfermedadListWrapper,
            BindingResult result) throws NotValidException {
        List<SegEnfermedadDto> segEnfermedadDtoList = enfermedadListWrapper.getEnfermedades();

        if (segEnfermedadDtoList.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        enfermedadValidator.validate(enfermedadListWrapper, result);

        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors());
        }

        enfermedadService.guardar(segEnfermedadDtoList);
        return savedSuccessfully();
    }

}
