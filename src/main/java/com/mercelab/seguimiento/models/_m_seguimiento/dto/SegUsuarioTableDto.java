package com.mercelab.seguimiento.models._m_seguimiento.dto;

import lombok.Data;

@Data
public class SegUsuarioTableDto {

    private Long id;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String email;
    private String rol;

    public SegUsuarioTableDto(Long id, String apellidoPaterno, String apellidoMaterno, String nombres, String email, String rol) {
        this.id = id;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombres = nombres;
        this.email = email;
        this.rol = rol;
    }
}
