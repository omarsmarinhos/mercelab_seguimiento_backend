package com.mercelab.seguimiento.validator;

import com.mercelab.seguimiento.models._m_seguimiento.dto.EnfermedadListWrapper;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegEnfermedadDto;
import com.mercelab.seguimiento.services.SegEnfermedadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Component
public class EnfermedadValidator implements Validator {

    @Autowired
    private SegEnfermedadService enfermedadService;

    @Override
    public boolean supports(Class<?> clazz) {
        return EnfermedadListWrapper.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        List<SegEnfermedadDto> enfermedades = ((EnfermedadListWrapper) target).getEnfermedades();

        for (int i = 0; i < enfermedades.size(); i++) {
            SegEnfermedadDto enfermedad = enfermedades.get(i);

            if (enfermedad.getPacienteId() == null) {
                errors.rejectValue("enfermedades[" + i + "].pacienteId", "required",
                        "El paciente es requerido");
            }

            if (enfermedad.getFechaDiagnostico() == null) {
                errors.rejectValue("enfermedades["+ i + "].fechaDiagnostico", "required",
                        "La fecha es requerida");
            }

            if (enfermedad.getCie10Id() != null) {
                if (enfermedadService.estaRegistradaEnfermedad(enfermedad)) {
                    errors.rejectValue("enfermedades[" + i + "].cie10Id", "duplicate",
                            "La enfermedad Cie10 ya se encuentra registrada al paciente");
                }
            }
        }

    }

}

