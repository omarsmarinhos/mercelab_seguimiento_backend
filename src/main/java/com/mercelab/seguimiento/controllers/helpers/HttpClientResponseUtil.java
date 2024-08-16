package com.mercelab.seguimiento.controllers.helpers;

import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientResponseUtil {

    public static ResponseEntity<Object> doGetResponse(HttpClient httpClient, String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "")
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();
            return ResponseEntity.status(statusCode).body(responseBody);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en la solicitud a la API");
        }
    }

}
