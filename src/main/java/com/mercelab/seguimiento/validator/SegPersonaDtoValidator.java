package com.mercelab.seguimiento.validator;

import com.mercelab.seguimiento.models._m_seguimiento.dto.SegPersonaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

@Component
public class SegPersonaDtoValidator implements Validator {

    public static final int DNI = 1;
    public static final int CARNET_EXTRANJERIA = 2;
    public static final int RUC = 3;
    public static final int PASAPORTE = 4;
    public static final int PARTIDA_NACIMIENTO = 5;

    @Override
    public boolean supports(Class<?> clazz) {
        return SegPersonaDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SegPersonaDto personaDto = (SegPersonaDto) target;
        Integer tipoDocumento = personaDto.getTipoDocumento();
        String nroDocumento = personaDto.getNumeroDocumento();

        if (tipoDocumento != null) {
            switch (tipoDocumento) {
                case DNI -> {
                    if (nroDocumento.length() != 8) {
                        registrarError(errors, "seg_persona.numeroDocumento.Dni");
                    }
                }
                case CARNET_EXTRANJERIA -> {
                    if (nroDocumento.length() > 12) {
                        registrarError(errors, "seg_persona.numeroDocumento.Carnet");
                    }
                }
                case RUC -> {
                    if (nroDocumento.length() != 11) {
                        registrarError(errors, "seg_persona.numeroDocumento.RUC");
                    }
                }
                case PASAPORTE -> {
                    if (nroDocumento.length() > 12) {
                        registrarError(errors, "seg_persona.numeroDocumento.Pasaporte");
                    }
                }
                case PARTIDA_NACIMIENTO -> {
                    if (nroDocumento.length() > 15) {
                        registrarError(errors, "seg_persona.numeroDocumento.PNac");
                    }
                }
            }
        }


    }

    private void registrarError(Errors errors, String mensajeError) {
        System.out.println(mensajeError);
        errors.rejectValue("persona.numeroDocumento", mensajeError);
    }
}

