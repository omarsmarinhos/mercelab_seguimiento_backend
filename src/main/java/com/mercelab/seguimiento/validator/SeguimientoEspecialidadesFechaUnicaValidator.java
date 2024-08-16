package com.mercelab.seguimiento.validator;

import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoEspecialidadDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoEspecialidadListWrapper;
import com.mercelab.seguimiento.services.SegSeguimientoEspecialidadService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class SeguimientoEspecialidadesFechaUnicaValidator implements Validator {

    @Autowired
    private SegSeguimientoEspecialidadService seguimientoEspecialidadService;


    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return SeguimientoEspecialidadListWrapper.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        List<SegSeguimientoEspecialidadDto> list = ((SeguimientoEspecialidadListWrapper) target).getSeguimientoEspecialidadList();
        for (int i = 0; i < list.size(); i++) {
            if (seguimientoEspecialidadService.estaRegistradoFechaProgramada(list.get(i))) {
                errors.rejectValue("seguimientoEspecialidadList[" + i + "].fechaProgramada", "duplicate"
                        , "No se puede repetir las fechas programadas");
            }
        }
    }
}