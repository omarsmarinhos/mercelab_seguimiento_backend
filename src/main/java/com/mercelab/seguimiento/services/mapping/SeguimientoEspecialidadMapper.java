package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimientoEspecialidad;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoEspecialidadDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SeguimientoEspecialidadMapper {

    SegSeguimientoEspecialidad toEntity(SegSeguimientoEspecialidadDto segSeguimientoEspecialidadDto);

    @Mapping(source = "seguimiento.id", target = "seguimientoId")
    @Mapping(source = "tratamientoEspecialidad.id", target = "tratamientoEspecialidadId")
    SegSeguimientoEspecialidadDto toSegSeguimientoEspecialidadDto(SegSeguimientoEspecialidad seguimientoEspecialidad);

    @Named("toTraEspecialidadDTOWithExtras")
    default SegSeguimientoEspecialidadDto toSegSeguimientoEspecialidadDtoWithExtras(SegSeguimientoEspecialidad seguimientoEspecialidad) {
        SegSeguimientoEspecialidadDto segSeguimientoEspecialidadDto = toSegSeguimientoEspecialidadDto(seguimientoEspecialidad);
        segSeguimientoEspecialidadDto.setNombreEspecialidad(seguimientoEspecialidad.getTratamientoEspecialidad().getEspecialidad().getDescripcion());
        return segSeguimientoEspecialidadDto;
    }
}
