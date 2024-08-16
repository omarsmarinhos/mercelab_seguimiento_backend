package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoExamen;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoExamenDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TratamientoExamenMapper {

    SegTratamientoExamen toEntity(SegTratamientoExamenDto tratamientoExamenDto);

    @Mapping(source = "tratamiento.id", target = "tratamientoId")
    @Mapping(source = "examen.id", target = "examenId")
    SegTratamientoExamenDto toSegTratamientoExamenDto(SegTratamientoExamen tratamientoExamen);
}
