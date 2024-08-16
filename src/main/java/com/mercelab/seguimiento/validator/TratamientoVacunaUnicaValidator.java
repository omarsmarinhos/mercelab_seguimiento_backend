package com.mercelab.seguimiento.validator;

import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoVacunaDto;
import com.mercelab.seguimiento.services.SegTratamientoVacunaService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TratamientoVacunaUnicaValidator implements Validator {

    @Autowired
    private SegTratamientoVacunaService tratamientoVacunaService;

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return SegTratamientoVacunaDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        SegTratamientoVacunaDto tratamientoVacuna = (SegTratamientoVacunaDto) target;
        if (tratamientoVacunaService.estaRegistradoTratamientoVacuna(tratamientoVacuna)) {
            errors.rejectValue("vacunaId", "duplicate"
                    , "La vacuna ya se encuentra registrada al tratamiento");
        }
    }
}
