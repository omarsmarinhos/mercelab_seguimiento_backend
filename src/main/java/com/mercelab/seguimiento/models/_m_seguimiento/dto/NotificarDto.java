package com.mercelab.seguimiento.models._m_seguimiento.dto;

import lombok.Data;

@Data
public class NotificarDto {

    private String paciente;
    private String telefono;
    private String observacion;

    public NotificarDto(String hc, String apePaterno, String apeMaterno, String nombres, String telefono,
                        String observacion) {
        this.paciente = hc.concat(" - ").concat(apePaterno).concat(" ").concat(apeMaterno).concat(" ").concat(nombres);
        this.telefono = telefono;
        this.observacion = observacion;
    }

}
