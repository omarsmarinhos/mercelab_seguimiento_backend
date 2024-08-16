package com.mercelab.seguimiento.validator;

import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoEspecialidadDto;
import com.mercelab.seguimiento.services.SegTratamientoEspecialidadService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TratamientoEspecialidadUnicaValidator implements Validator {

    @Autowired
    private SegTratamientoEspecialidadService tratamientoEspecialidadService;

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return SegTratamientoEspecialidadDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        SegTratamientoEspecialidadDto tratamientoEspecialidad = (SegTratamientoEspecialidadDto) target;
        if (tratamientoEspecialidadService.estaRegistradoTratamientoEspecialidad(tratamientoEspecialidad)) {
            errors.rejectValue("especialidadId", "duplicate"
                    , "La especialidad ya se encuentra registrada al tratamiento");
        }
    }
}
