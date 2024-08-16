package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegTratamiento;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TratamientoMapper {

    SegTratamiento toEntity(SegTratamientoDto tratamientoDto);

    SegTratamientoDto toTratamientoDto(SegTratamiento tratamiento);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "enfermedadId", ignore = true)
    })
    void actualizarTratamientoDesdeDto(@MappingTarget SegTratamiento tratamiento, SegTratamientoDto tratamientoDto);

}
