package com.mercelab.seguimiento.models._m_seguimiento.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SegUsuarioRequestDto {

    private Long id;

    private String password;

    @Valid
    private SegPersonaDto persona;

    @NotNull
    private Long rolId;

    private Long estadoId;
    private LocalDateTime creadoEn;
    private LocalDateTime editadoEn;
    private Long creadoPor;
    private Long editadoPor;

    private Integer edad;
    private String ubigeo;

}
