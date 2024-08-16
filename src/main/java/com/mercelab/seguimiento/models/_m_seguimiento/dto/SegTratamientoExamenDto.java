package com.mercelab.seguimiento.models._m_seguimiento.dto;

import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoExamen;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SegTratamientoExamenDto {

    private Long id;

    @NotNull(message = "{seg_tratamientoExamen.tratamientoId.NotNull}")
    private Long tratamientoId;

    @NotNull(message = "{seg_tratamientoExamen.examenId.NotNull}")
    private Long examenId;

    private String examenTipo;
    private String examenDescripcion;

    private Long estadoId;
    private LocalDateTime creadoEn;
    private LocalDateTime editadoEn;
    private Long creadoPor;
    private Long editadoPor;

    public SegTratamientoExamenDto() {
    }

    public SegTratamientoExamenDto(SegTratamientoExamen tratamientoExamen) {
        this.id = tratamientoExamen.getId();
        this.tratamientoId = tratamientoExamen.getTratamiento().getId();
        this.examenId = tratamientoExamen.getExamen().getId();
        this.examenTipo = tratamientoExamen.getExamen().getExamenTipo().getDescripcion();
        this.examenDescripcion = tratamientoExamen.getExamen().getDescripcion();
        this.estadoId = tratamientoExamen.getEstadoId();
        this.creadoEn = tratamientoExamen.getCreadoEn();
        this.editadoEn = tratamientoExamen.getEditadoEn();
        this.creadoPor = tratamientoExamen.getCreadoPor();
        this.editadoPor = tratamientoExamen.getEditadoPor();
    }
}
