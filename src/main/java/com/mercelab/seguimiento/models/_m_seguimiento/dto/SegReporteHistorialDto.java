package com.mercelab.seguimiento.models._m_seguimiento.dto;

import com.mercelab.seguimiento.models._m_seguimiento.SegEnfermedad;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SegReporteHistorialDto {

    private String tipoSeguimiento;
    private Object nombreSeguimiento;
    private String observacion;
    private String respuesta;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String enfermedad;

    public SegReporteHistorialDto() {
    }

    public SegReporteHistorialDto(String tipoSeguimiento, Object nombreSeguimiento, String observacion
            , String respuesta, LocalDate fechaInicio, LocalDate fechaFin, SegEnfermedad enfermedad) {
        this.tipoSeguimiento = tipoSeguimiento;
        this.nombreSeguimiento = nombreSeguimiento;
        this.observacion = observacion;
        this.respuesta = respuesta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.enfermedad = enfermedad.getEsCronico() ? enfermedad.getCie10().getDescripcion() : enfermedad.getDescripcion();
    }

}
