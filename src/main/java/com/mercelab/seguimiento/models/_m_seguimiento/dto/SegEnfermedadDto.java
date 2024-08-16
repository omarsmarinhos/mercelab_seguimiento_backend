package com.mercelab.seguimiento.models._m_seguimiento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SegEnfermedadDto {

    private Long id;

    //@NotNull(message = "{seg_enfermedad.pacienteId.NotNull}")
    private Long pacienteId;

    //@NotNull(message = "{seg_enfermedad.fechaDiagnostico.NotNull}")
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaDiagnostico;

    private Long cie10Id;
    private Boolean esCronico;
    private String descripcion;

    private String observacion;
    private Long estadoId;

    private LocalDateTime creadoEn;
    private LocalDateTime editadoEn;
    private Long creadoPor;
    private Long editadoPor;

}
