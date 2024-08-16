package com.mercelab.seguimiento.controllers._c_servicios;

import com.mercelab.seguimiento._setting._api.ApiResponse;
import com.mercelab.seguimiento.controllers.helpers.HttpClientResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/medicamento")
public class MedicamentoApiController implements ApiResponse {

    private final HttpClient httpClient;

    public MedicamentoApiController() {
        this.httpClient = HttpClient.newHttpClient();
    }

    @GetMapping("")
    public ResponseEntity<Object> getMedicamentos(
            @RequestParam String value,
            @RequestParam Integer page) {
        String url = String.format("http://localhost:4040/ipharmaexpress/medicamentos/buscar?value=%s&page=%d&size=10", value, page);
        return HttpClientResponseUtil.doGetResponse(httpClient, url);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMedicamentoPorId(
            @PathVariable Integer id) {
        String url = String.format("http://localhost:4040/ipharmaexpress/medicamentos/%d", id);
        return HttpClientResponseUtil.doGetResponse(httpClient, url);
    }



}
