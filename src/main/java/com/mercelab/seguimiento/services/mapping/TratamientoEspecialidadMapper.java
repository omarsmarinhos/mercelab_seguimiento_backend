package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoEspecialidad;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoEspecialidadDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TratamientoEspecialidadMapper {

    SegTratamientoEspecialidad toEntity(SegTratamientoEspecialidadDto tratamientoEspecialidadDto);

    @Mapping(source = "tratamiento.id", target = "tratamientoId")
    @Mapping(source = "especialidad.id", target = "especialidadId")
    SegTratamientoEspecialidadDto toSegTratamientoEspecialidadDto(SegTratamientoEspecialidad tratamientoEspecialidad);
}
