package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegPersona;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegPersonaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PersonaMapper {
    SegPersonaDto toPersonaDTO(SegPersonaDto persona);

    @Mapping(target = "distrito", ignore = true)
    SegPersona toEntity(SegPersonaDto personaDTO);

    @Mappings({
            @Mapping(target = "id", ignore = true),
    })
    void actualizarPersonaDesdeDto(@MappingTarget SegPersona personaExistente, SegPersonaDto personaDto);

}
