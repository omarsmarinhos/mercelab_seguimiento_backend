package com.mercelab.seguimiento.models._m_seguimiento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SegSeguimientoExamenDto {

    private Long id;

    @NotNull(message = "")
    private Long seguimientoId;

    @NotNull(message = "")
    private Long tratamientoExamenId;

    @NotNull(message = "")
    private Character tipoNotificacion;

    @NotNull(message = "")
    private LocalDate fechaProgramada;

    private LocalDateTime fechaNotificacion;
    private String observacion;
    private Long estadoId;
    private LocalDateTime creadoEn;
    private LocalDateTime editadoEn;
    private Long creadoPor;
    private Long editadoPor;

    private String nombreExamen;
    private String tipoExamen;


}
