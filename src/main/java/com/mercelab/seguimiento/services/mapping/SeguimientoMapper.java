package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimiento;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SeguimientoMapper {

    SegSeguimiento toEntity(SegSeguimientoDto seguimientoDto);

    SegSeguimientoDto toSegSeguimientoDto(SegSeguimiento seguimiento);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "tratamientoId", ignore = true),
            @Mapping(target = "pacienteId", ignore = true),
            @Mapping(target = "creadoEn", ignore = true),
            @Mapping(target = "editadoEn", ignore = true),
            @Mapping(target = "creadoPor", ignore = true),
            @Mapping(target = "editadoPor", ignore = true)
    })
    void actualizarSeguimientoDesdeDto(@MappingTarget SegSeguimiento seguimiento, SegSeguimientoDto seguimientoDto);
}
