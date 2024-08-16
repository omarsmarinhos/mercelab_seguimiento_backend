package com.mercelab.seguimiento.validator;

import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoExamenDto;
import com.mercelab.seguimiento.services.SegTratamientoExamenService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TratamientoExamenUnicoValidator implements Validator {

    @Autowired
    private SegTratamientoExamenService tratamientoExamenService;

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return SegTratamientoExamenDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        SegTratamientoExamenDto tratamientoExamen = (SegTratamientoExamenDto) target;
        if (tratamientoExamenService.estaRegistradoTratamientoExamen(tratamientoExamen)) {
            errors.rejectValue("examenId", "duplicate"
                    , "El examen ya se encuentra registrado al tratamiento");
        }
    }
}
