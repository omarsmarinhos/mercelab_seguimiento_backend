package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimientoVacuna;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoVacunaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SeguimientoVacunaMapper {

    SegSeguimientoVacuna toEntity(SegSeguimientoVacunaDto seguimientoVacunaDtoDto);

    @Mapping(source = "seguimiento.id", target = "seguimientoId")
    @Mapping(source = "tratamientoVacuna.id", target = "tratamientoVacunaId")
    SegSeguimientoVacunaDto toSegSeguimientoVacunaDto(SegSeguimientoVacuna seguimientoVacuna);

    @Named("toTraVacunaDTOWithExtras")
    default SegSeguimientoVacunaDto toSegSeguimientoVacunaDtoWithExtras(SegSeguimientoVacuna seguimientoVacuna) {
        SegSeguimientoVacunaDto seguimientoVacunaDto = toSegSeguimientoVacunaDto(seguimientoVacuna);
        seguimientoVacunaDto.setNombreVacuna(seguimientoVacuna.getTratamientoVacuna().getVacuna().getDescripcion());
        return seguimientoVacunaDto;
    }
}
