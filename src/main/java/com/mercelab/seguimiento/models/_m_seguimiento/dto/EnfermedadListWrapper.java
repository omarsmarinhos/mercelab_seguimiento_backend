package com.mercelab.seguimiento.models._m_seguimiento.dto;

import com.mercelab.seguimiento.models._m_seguimiento.dto.SegEnfermedadDto;
import lombok.Data;

import java.util.List;

@Data
public class EnfermedadListWrapper {

    private List<SegEnfermedadDto> enfermedades;

}