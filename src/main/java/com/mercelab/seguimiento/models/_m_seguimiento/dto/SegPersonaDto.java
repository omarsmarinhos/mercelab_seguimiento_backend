package com.mercelab.seguimiento.models._m_seguimiento.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class SegPersonaDto {

    private Long id;

    @NotNull(message = "{seg_persona.tipoDocumento.NotNull}")
    private Integer tipoDocumento;

    @NotEmpty(message = "{seg_persona.numeroDocumento.NotEmpty}")
    private String numeroDocumento;

    @NotEmpty(message = "{seg_persona.apellidoPaterno.NotEmpty}")
    private String apellidoPaterno;

    @NotEmpty(message = "{seg_persona.apellidoMaterno.NotEmpty}")
    private String apellidoMaterno;

    @NotEmpty(message = "{seg_persona.nombres.NotEmpty}")
    private String nombres;

    private String telefono;

    private Long distritoId;

    private String direccion;
    private String email;

    @NotEmpty(message = "{seg_persona.sexo.NotEmpty}")
    private String sexo;

    @NotNull(message = "{seg_persona.fechaNacimiento.NotNull}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
}
