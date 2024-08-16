package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoMedicamento;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoMedicamentoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TratamientoMedicamentoMapper {

    SegTratamientoMedicamento toEntity(SegTratamientoMedicamentoDto tratamientoMedicamentoDto);

    @Mapping(source = "tratamiento.id", target = "tratamientoId")
    SegTratamientoMedicamentoDto toSegTratamientoMedicamentoDto(SegTratamientoMedicamento tratamientoMedicamento);
}
