package com.mercelab.seguimiento.models._m_seguimiento.dto;

import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoMedicamento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SegTratamientoMedicamentoDto {

    private Long id;

    @NotNull(message = "{seg_tratamientoMedicamento.tratamientoId.NotNull}")
    private Long tratamientoId;

    @NotNull(message = "{seg_tratamientoMedicamento.medicamentoId.NotNull}")
    private Long medicamentoId;

    private Long estadoId;
    private LocalDateTime creadoEn;
    private LocalDateTime editadoEn;
    private Long creadoPor;
    private Long editadoPor;

    public SegTratamientoMedicamentoDto() {
    }

    public SegTratamientoMedicamentoDto(SegTratamientoMedicamento tratamientoMedicamento) {
        this.id = tratamientoMedicamento.getId();
        this.tratamientoId = tratamientoMedicamento.getTratamiento().getId();
        this.medicamentoId = tratamientoMedicamento.getMedicamentoId();
        this.estadoId = tratamientoMedicamento.getEstadoId();
        this.creadoEn = tratamientoMedicamento.getCreadoEn();
        this.editadoEn = tratamientoMedicamento.getEditadoEn();
        this.creadoPor = tratamientoMedicamento.getCreadoPor();
        this.editadoPor = tratamientoMedicamento.getEditadoPor();
    }
}
