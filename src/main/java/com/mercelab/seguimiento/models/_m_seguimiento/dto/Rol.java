package com.mercelab.seguimiento.models._m_seguimiento.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class Rol {

    @NotEmpty
    private String nombre;
    private Long id;
    @NotNull
    private Boolean esAdmin;
    private List<Menu> menus;
}
