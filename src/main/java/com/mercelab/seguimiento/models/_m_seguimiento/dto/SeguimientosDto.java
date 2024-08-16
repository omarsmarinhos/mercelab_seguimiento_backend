package com.mercelab.seguimiento.models._m_seguimiento.dto;

import com.mercelab.seguimiento.models._m_seguimiento.SegEnfermedad;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SeguimientosDto {

    private Long seguimientoId;
    private String tipo;
    private String enfermedad;
    private Long tipoSeguimientoId;
    private Object elementoSeguimiento;
    private String paciente;
    private Character tipoNotificacion;
    private Long tipoTratamientoId;
    private LocalDate fechaProgramada;
    private Long estadoId;

    public SeguimientosDto(String hc, String apePaterno, String apeMaterno, String nombres, Long seguimientoId,
                           Long tipoSeguimientoId, Object elementoSeguimiento, Long tipoTratamientoId, Character tipoNotificacion,
                           Long estadoId, LocalDate fechaProgramada, String tipo, SegEnfermedad enfermedad) {
        this.tipo = tipo;
        this.seguimientoId = seguimientoId;
        this.tipoSeguimientoId = tipoSeguimientoId;
        this.elementoSeguimiento = elementoSeguimiento;
        this.paciente = hc.concat(" - ").concat(apePaterno).concat(" ").concat(apeMaterno).concat(" ").concat(nombres);
        this.tipoNotificacion = tipoNotificacion;
        this.tipoTratamientoId = tipoTratamientoId;
        this.fechaProgramada = fechaProgramada;
        this.estadoId = estadoId;
        this.enfermedad = enfermedad.getEsCronico() ? enfermedad.getCie10().getDescripcion() : enfermedad.getDescripcion();
    }

}
