package com.mercelab.seguimiento.models._m_seguimiento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SegSeguimientoVacunaDto {

    private Long id;

    @NotNull
    private Long seguimientoId;

    @NotNull
    private Long tratamientoVacunaId;

    @NotNull
    private Character tipoNotificacion;

    @NotNull
    private LocalDate fechaProgramada;

    private LocalDateTime fechaNotificacion;
    private String observacion;
    private Long estadoId;
    private LocalDateTime creadoEn;
    private LocalDateTime editadoEn;
    private Long creadoPor;
    private Long editadoPor;

    private String nombreVacuna;

}
