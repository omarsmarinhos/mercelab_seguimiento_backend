package com.mercelab.seguimiento.models._m_seguimiento.dto;

import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoEspecialidad;
import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoVacuna;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SegTratamientoEspecialidadDto {

    private Long id;

    @NotNull(message = "{seg_tratamiento.tratamientoId.NotNull}")
    private Long tratamientoId;

    @NotNull(message = "{seg_tratamientoEspecialidad.especialidadId.NotNull}")
    private Long especialidadId;

    private String especialidadDescripcion;

    private Long estadoId;
    private LocalDateTime creadoEn;
    private LocalDateTime editadoEn;
    private Long creadoPor;
    private Long editadoPor;

    public SegTratamientoEspecialidadDto() {
    }

    public SegTratamientoEspecialidadDto(SegTratamientoEspecialidad tratamientoEspecialidad) {
        this.id = tratamientoEspecialidad.getId();
        this.tratamientoId = tratamientoEspecialidad.getTratamiento().getId();
        this.especialidadId = tratamientoEspecialidad.getEspecialidad().getId();
        this.especialidadDescripcion = tratamientoEspecialidad.getEspecialidad().getDescripcion();
        this.estadoId = tratamientoEspecialidad.getEstadoId();
        this.creadoEn = tratamientoEspecialidad.getCreadoEn();
        this.editadoEn = tratamientoEspecialidad.getEditadoEn();
        this.creadoPor = tratamientoEspecialidad.getCreadoPor();
        this.editadoPor = tratamientoEspecialidad.getEditadoPor();
    }
}
