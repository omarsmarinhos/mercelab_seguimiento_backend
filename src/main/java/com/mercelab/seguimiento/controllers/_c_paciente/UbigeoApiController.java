package com.mercelab.seguimiento.controllers._c_paciente;

import com.mercelab.seguimiento._setting._api.ApiResponse;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegUbigeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ubigeo")
public class UbigeoApiController implements ApiResponse {

    @Autowired
    private SegUbigeoRepository ubigeoRepository;

    @GetMapping()
    public ResponseEntity<Object> listarDistritos(@RequestParam(value = "q", required = false) String query) {
        return showAll(ubigeoRepository.getDistritos(query, PageRequest.of(0, 10)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPorId(@PathVariable(name = "id") Long id) {
        return showOne(ubigeoRepository.getPorId(id));
    }

}
