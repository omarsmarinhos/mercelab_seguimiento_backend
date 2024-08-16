package com.mercelab.seguimiento.models._m_seguimiento.dto;

import lombok.Data;

@Data
public class Menu {

    private Long id;
    private Integer estado;
    private Boolean accesoTotal;
    private Permiso permisos;

}
