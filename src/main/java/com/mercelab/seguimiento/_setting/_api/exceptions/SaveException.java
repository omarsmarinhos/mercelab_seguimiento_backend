package com.mercelab.seguimiento._setting._api.exceptions;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;

public class SaveException extends BindException {
    public SaveException(Exception errores) {
        super(new BeanPropertyBindingResult(new Object(), "ApiSaveException"));

    }
}
