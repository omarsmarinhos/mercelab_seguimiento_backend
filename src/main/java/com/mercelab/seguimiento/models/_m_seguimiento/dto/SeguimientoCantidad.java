package com.mercelab.seguimiento.models._m_seguimiento.dto;

import lombok.Data;

@Data
public class SeguimientoCantidad {

    private String label;
    private Long cantidad;

    public SeguimientoCantidad(String label, Long cantidad) {
        this.label = label;
        this.cantidad = cantidad;
    }
}
