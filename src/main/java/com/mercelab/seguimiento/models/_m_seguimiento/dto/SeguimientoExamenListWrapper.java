package com.mercelab.seguimiento.models._m_seguimiento.dto;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class SeguimientoExamenListWrapper {

    @Valid
    private List<SegSeguimientoExamenDto> seguimientoExamenList;

}
