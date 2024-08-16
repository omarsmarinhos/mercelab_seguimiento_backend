package com.mercelab.seguimiento.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegReporteHistorialDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegReportePacienteDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegHistorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class SegReporteService {

    @Autowired
    private SegHistorialRepository historialRepository;

    public Page<SegReportePacienteDto> getPacientesConSeguimientoConcluido(String termino, Pageable pageable) {
        return historialRepository.getPacientesConSeguimientoConcluido(termino, pageable);
    }

    public List<SegReporteHistorialDto> getSeguimientosConcluidosPorPaciente(Long pacienteId) {
        List<SegReporteHistorialDto> examenes = historialRepository.getSeguimientosDeExamenesConcluidos(pacienteId);
        List<SegReporteHistorialDto> medicamentos = historialRepository.getSeguimientosDeMedicamentosConcluidos(pacienteId);
        List<SegReporteHistorialDto> vacunas = historialRepository.getSeguimientosDeVacunasConcluidos(pacienteId);
        List<SegReporteHistorialDto> especialidades = historialRepository.getSeguimientosDeEspecialidadesConcluidos(pacienteId);

        obtenerNombresMedicamentosDesdeAPI(medicamentos);

        List<SegReporteHistorialDto> seguimientosConcluidos = new ArrayList<>();
        seguimientosConcluidos.addAll(examenes);
        seguimientosConcluidos.addAll(medicamentos);
        seguimientosConcluidos.addAll(vacunas);
        seguimientosConcluidos.addAll(especialidades);

        seguimientosConcluidos.sort(Comparator.comparing(SegReporteHistorialDto::getFechaInicio));
        return seguimientosConcluidos;
    }

    private void obtenerNombresMedicamentosDesdeAPI(List<SegReporteHistorialDto> medicamentos) {
        HttpClient httpClient = HttpClient.newHttpClient();

        for (SegReporteHistorialDto medicamento : medicamentos) {
            Object id = medicamento.getNombreSeguimiento();
            HttpRequest request = HttpRequest.newBuilder()

                    .uri(URI.create("http://localhost:4040/ipharmaexpress/medicamentos/" + id))
                    .header("Authorization", "")
                    .build();

            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String nombreMedicamento = jsonNode
                        .path("data")
                        .path("nombre")
                        .asText();
                medicamento.setNombreSeguimiento(nombreMedicamento);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
