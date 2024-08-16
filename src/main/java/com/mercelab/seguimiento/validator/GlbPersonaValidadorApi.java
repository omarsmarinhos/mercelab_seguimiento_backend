package com.mercelab.seguimiento.validator;



import com.mercelab.seguimiento.models._m_seguimiento.GlbPersona;
import com.mercelab.seguimiento.repositories._r_seguimiento.GlbPersonaRepository;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GlbPersonaValidadorApi implements Validator {

    @Autowired
    GlbPersonaRepository daoGlbPersona;

    @Override
    public boolean supports(Class<?> clazz) {
        return GlbPersona.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GlbPersona glbPersona = (GlbPersona)target;

        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(target);

        if (beanWrapper.isWritableProperty("persNdocumentoidentidad")) {
                if(daoGlbPersona.findByPersNdocumentoidentidad(glbPersona.getPersNdocumentoidentidad())!= null) {
                    errors.rejectValue("persNdocumentoidentidad", "glb_persona.persNdocumentoidentidad.Only","Error.");
                }
            }
        }

}
