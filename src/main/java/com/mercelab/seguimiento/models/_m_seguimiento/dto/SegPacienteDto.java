package com.mercelab.seguimiento.models._m_seguimiento.dto;

import jakarta.validation.Valid;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SegPacienteDto {

    private Long id;

    private String codHistorialClinico;
    private Boolean tieneAlergia;

    @Valid
    private SegPersonaDto persona;

    private Long estadoId;
    private LocalDateTime creadoEn;
    private LocalDateTime editadoEn;
    private Long creadoPor;
    private Long editadoPor;

    private Integer edad;
    private String nombreCompleto;
    private String ubigeo;

}