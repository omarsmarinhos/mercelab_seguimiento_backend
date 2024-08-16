package com.mercelab.seguimiento.validator;

import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoExamenDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoExamenListWrapper;
import com.mercelab.seguimiento.services.SegSeguimientoExamenService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class SeguimientoExamenesFechaUnicaValidator implements Validator {

    @Autowired
    private SegSeguimientoExamenService seguimientoExamenService;


    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return SeguimientoExamenListWrapper.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        List<SegSeguimientoExamenDto> list = ((SeguimientoExamenListWrapper) target).getSeguimientoExamenList();
        for (int i = 0; i < list.size(); i++) {
            if (seguimientoExamenService.estaRegistradoFechaProgramada(list.get(i))) {
                errors.rejectValue("seguimientoExamenList[" + i + "].fechaProgramada", "duplicate"
                        , "No se puede repetir las fechas programadas");
            }
        }
    }
}