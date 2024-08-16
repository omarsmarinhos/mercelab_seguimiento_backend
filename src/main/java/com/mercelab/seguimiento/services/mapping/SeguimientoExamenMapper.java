package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegPaciente;
import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimientoExamen;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegPacienteDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoExamenDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SeguimientoExamenMapper {

    SegSeguimientoExamen toEntity(SegSeguimientoExamenDto seguimientoExamenDto);

    @Mapping(source = "seguimiento.id", target = "seguimientoId")
    @Mapping(source = "tratamientoExamen.id", target = "tratamientoExamenId")
    SegSeguimientoExamenDto toSegSeguimientoExamenDto(SegSeguimientoExamen seguimientoExamen);

    @Named("toPacienteDTOWithExtras")
    default SegSeguimientoExamenDto toSegSeguimientoExamenDtoWithExtras(SegSeguimientoExamen seguimientoExamen) {
        SegSeguimientoExamenDto seguimientoExamenDto = toSegSeguimientoExamenDto(seguimientoExamen);
        seguimientoExamenDto.setNombreExamen(seguimientoExamen.getTratamientoExamen().getExamen().getDescripcion());
        seguimientoExamenDto.setTipoExamen(seguimientoExamen.getTratamientoExamen().getExamen().getExamenTipo().getDescripcion());
        return seguimientoExamenDto;
    }
}
