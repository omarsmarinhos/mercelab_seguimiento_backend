package com.mercelab.seguimiento.models._m_seguimiento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SegTratamientoDto {

    private Long id;

    @NotNull(message = "{seg_tratamiento.enfermedadId.MotNull}")
    private Long enfermedadId;

    private String observacion;
    private Long estadoId;
    private LocalDateTime creadoEn;
    private LocalDateTime editadoEn;
    private Long creadoPor;
    private Long editadoPor;

}
