package com.mercelab.seguimiento._setting._api.exceptions;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;


import java.util.List;

import static ch.qos.logback.classic.util.StatusViaSLF4JLoggerFactory.addError;
public class NotValidException extends BindException {
    public NotValidException(List<ObjectError> errores) {
        super(new BeanPropertyBindingResult(new Object(), "ApiNotValidException"));
        for (ObjectError error : errores) {
            addError(error);
        }
    }
}
