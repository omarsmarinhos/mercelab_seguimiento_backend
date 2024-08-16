package com.mercelab.seguimiento.validator;

import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimientoVacuna;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoExamenDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoVacunaDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoExamenListWrapper;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoVacunaListWrapper;
import com.mercelab.seguimiento.services.SegSeguimientoExamenService;
import com.mercelab.seguimiento.services.SegSeguimientoVacunaService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class SeguimientoVacunasFechaUnicaValidator implements Validator {

    @Autowired
    private SegSeguimientoVacunaService seguimientoVacunaService;


    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return SeguimientoVacunaListWrapper.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        List<SegSeguimientoVacunaDto> list = ((SeguimientoVacunaListWrapper) target).getSeguimientoVacunaList();
        for (int i = 0; i < list.size(); i++) {
            if (seguimientoVacunaService.estaRegistradoFechaProgramada(list.get(i))) {
                errors.rejectValue("seguimientoVacunaList[" + i + "].fechaProgramada", "duplicate"
                        , "No se puede repetir las fechas programadas");
            }
        }
    }
}