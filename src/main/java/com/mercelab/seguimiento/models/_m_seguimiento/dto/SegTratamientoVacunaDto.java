package com.mercelab.seguimiento.models._m_seguimiento.dto;

import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoVacuna;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SegTratamientoVacunaDto {

    private Long id;

    @NotNull(message = "{seg_tratamiento.tratamientoId.NotNull}")
    private Long tratamientoId;

    @NotNull(message = "{seg_tratamientoVacuna.vacunaId.NotNull}")
    private Long vacunaId;

    private String vacunaDescripcion;

    private Long estadoId;
    private LocalDateTime creadoEn;
    private LocalDateTime editadoEn;
    private Long creadoPor;
    private Long editadoPor;

    public SegTratamientoVacunaDto() {
    }

    public SegTratamientoVacunaDto(SegTratamientoVacuna tratamientoVacuna) {
        this.id = tratamientoVacuna.getId();
        this.tratamientoId = tratamientoVacuna.getTratamiento().getId();
        this.vacunaId = tratamientoVacuna.getVacuna().getId();
        this.vacunaDescripcion = tratamientoVacuna.getVacuna().getDescripcion();
        this.estadoId = tratamientoVacuna.getEstadoId();
        this.creadoEn = tratamientoVacuna.getCreadoEn();
        this.editadoEn = tratamientoVacuna.getEditadoEn();
        this.creadoPor = tratamientoVacuna.getCreadoPor();
        this.editadoPor = tratamientoVacuna.getEditadoPor();
    }
}
