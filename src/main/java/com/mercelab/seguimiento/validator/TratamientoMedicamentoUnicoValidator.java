package com.mercelab.seguimiento.validator;

import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoMedicamentoDto;
import com.mercelab.seguimiento.services.SegTratamientoMedicamentoService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TratamientoMedicamentoUnicoValidator implements Validator {

    @Autowired
    private SegTratamientoMedicamentoService tratamientoMedicamentoService;

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return SegTratamientoMedicamentoDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        SegTratamientoMedicamentoDto tratamientoMedicamentoDto = (SegTratamientoMedicamentoDto) target;
        if (tratamientoMedicamentoService.estaRegistradoTratamientoMedicamento(tratamientoMedicamentoDto)) {
            errors.rejectValue("medicamentoId", "duplicate"
                    , "El medicamento ya se encuentra registrado al tratamiento");
        }
    }
}
