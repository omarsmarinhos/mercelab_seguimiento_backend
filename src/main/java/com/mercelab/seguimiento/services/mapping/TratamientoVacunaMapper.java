package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoVacuna;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoVacunaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TratamientoVacunaMapper {

    SegTratamientoVacuna toEntity(SegTratamientoVacunaDto tratamientoVacunaDto);

    @Mapping(source = "tratamiento.id", target = "tratamientoId")
    @Mapping(source = "vacuna.id", target = "vacunaId")
    SegTratamientoVacunaDto toSegTratamientoVacunaDto(SegTratamientoVacuna tratamientoVacuna);
}
