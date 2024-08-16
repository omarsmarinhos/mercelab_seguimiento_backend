package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimientoMedicamento;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoMedicamentoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SeguimientoMedicamentoMapper {

    SegSeguimientoMedicamento toEntity(SegSeguimientoMedicamentoDto seguimientoMedicamentoDto);

    @Mapping(source = "seguimiento.id", target = "seguimientoId")
    @Mapping(source = "tratamientoMedicamento.id", target = "tratamientoMedicamentoId")
    @Mapping(source = "tratamientoMedicamento.medicamentoId", target = "medicamentoId")
    SegSeguimientoMedicamentoDto toSegSeguimientoExamenDto(SegSeguimientoMedicamento seguimientoMedicamento);

}
