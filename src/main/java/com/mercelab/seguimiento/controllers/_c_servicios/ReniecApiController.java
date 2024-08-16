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
@RequestMapping("/reniec")
public class ReniecApiController implements ApiResponse {

    private final HttpClient httpClient;

    public ReniecApiController() {
        this.httpClient = HttpClient.newHttpClient();
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Object> getPersonaPorId(
            @PathVariable Integer dni) {
        String url = String.format("http://localhost:4040/persona/dni?value=%d", dni);
        return HttpClientResponseUtil.doGetResponse(httpClient, url);
    }

}
