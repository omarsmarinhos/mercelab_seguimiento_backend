package com.mercelab.seguimiento.models._m_seguimiento.dto;

import lombok.Data;

@Data
public class SegUbigeoDto {

    private Long id;
    private String distrito;
    private String provincia;
    private String departamento;

    public SegUbigeoDto(Long id, String distrito, String provincia, String departamento) {
        this.id = id;
        this.distrito = distrito;
        this.provincia = provincia;
        this.departamento = departamento;
    }
}
