package com.mercelab.seguimiento.models._m_seguimiento.dto;

import lombok.Data;

@Data
public class SegReportePacienteDto {

    private String hc;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private Long pacienteId;

    public SegReportePacienteDto() {
    }

    public SegReportePacienteDto(String hc, String apellidoPaterno, String apellidoMaterno, String nombres,
                                 Long pacienteId) {
        this.hc = hc;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombres = nombres;
        this.pacienteId = pacienteId;
    }
}
