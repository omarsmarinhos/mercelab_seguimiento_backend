package com.mercelab.seguimiento.models._m_seguimiento.dto;

import com.mercelab.seguimiento.models._m_seguimiento.*;
import lombok.Data;

@Data
public class SegDiagnosticoDto {

    private Long enfermedadId;
    private Long pacienteId;
    private String paciente;
    private SegCie10 cie10;
    private String enfermedad;
    private Boolean esCronico;
    private Long tratamientoId;
    private Long estadoTratamientoId;
    private Long seguimientoId;
    private Long estadoSeguimientoId;

    public SegDiagnosticoDto(SegEnfermedad enfermedad, SegTratamiento tratamiento, SegSeguimiento seguimiento) {
        this.enfermedadId = enfermedad.getId();
        this.pacienteId = enfermedad.getPaciente().getId();
        this.cie10 = enfermedad.getCie10();
        this.paciente = enfermedad.getPaciente().getCodHistorialClinico().concat(" - ")
                .concat(enfermedad.getPaciente().getPersona().getApellidoPaterno()).concat(" ")
                .concat(enfermedad.getPaciente().getPersona().getApellidoMaterno()).concat(" ")
                .concat(enfermedad.getPaciente().getPersona().getNombres());
        this.enfermedad = enfermedad.getDescripcion();
        this.esCronico = enfermedad.getEsCronico();

        if (tratamiento != null) {
            this.tratamientoId = tratamiento.getId();
            this.estadoTratamientoId = tratamiento.getEstadoId();
        }

        if (seguimiento != null) {
            this.seguimientoId = seguimiento.getId();
            this.estadoSeguimientoId = seguimiento.getEstadoId();
        }

    }

}
