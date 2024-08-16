package com.mercelab.seguimiento.models._m_seguimiento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SegSeguimientoDto {

    private Long id;
    @NotNull(message = "seg_seguimiento.tratamientoId.NotNull")
    private Long tratamientoId;
    @NotNull(message = "seg_seguimiento.pacienteId.NotNull")
    private Long pacienteId;
    private String observacion;
    private Long estadoId;
    private LocalDateTime creadoEn;
    private LocalDateTime editadoEn;
    private Long creadoPor;
    private Long editadoPor;

}
