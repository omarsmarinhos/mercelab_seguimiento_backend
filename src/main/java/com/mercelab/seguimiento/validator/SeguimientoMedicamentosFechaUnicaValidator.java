package com.mercelab.seguimiento.validator;

import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoMedicamentoDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoMedicamentoListWrapper;
import com.mercelab.seguimiento.services.SegSeguimientoMedicamentoService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class SeguimientoMedicamentosFechaUnicaValidator implements Validator {

    @Autowired
    private SegSeguimientoMedicamentoService segSeguimientoMedicamentoService;


    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return SeguimientoMedicamentoListWrapper.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        List<SegSeguimientoMedicamentoDto> list = ((SeguimientoMedicamentoListWrapper) target).getSeguimientoMedicamentoList();
        list.forEach(System.out::println);
        for (int i = 0; i < list.size(); i++) {
            if (segSeguimientoMedicamentoService.estaRegistradoFechaProgramada(list.get(i))) {
                errors.rejectValue("seguimientoMedicamentoList[" + i + "].fechaProgramada", "duplicate"
                        , "No se puede repetir las fechas programadas");
            }
        }
    }
}