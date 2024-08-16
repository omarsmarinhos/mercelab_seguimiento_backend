package com.mercelab.seguimiento.models._m_seguimiento.dto;

import lombok.Data;

@Data
public class Permiso {

    private Boolean leer;
    private Boolean crear;
    private Boolean editar;
    private Boolean eliminar;
}
