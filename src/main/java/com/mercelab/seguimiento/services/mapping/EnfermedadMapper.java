package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegEnfermedad;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegEnfermedadDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnfermedadMapper {

    SegEnfermedad toEntity(SegEnfermedadDto segEnfermedadDto);

    SegEnfermedadDto toEnfermedadDto(SegEnfermedad segEnfermedad);

}
