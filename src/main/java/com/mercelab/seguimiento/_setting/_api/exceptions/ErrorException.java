package com.mercelab.seguimiento._setting._api.exceptions;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;

public class ErrorException extends BindException {
    public ErrorException(Exception errores) {
        super(new BeanPropertyBindingResult(new Object(), "ApiErrorException"));
    }
}
