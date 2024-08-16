package com.mercelab.seguimiento._setting._api;


import com.mercelab.seguimiento._setting._api.exceptions.DeleteException;
import com.mercelab.seguimiento._setting._api.exceptions.NotValidException;
import com.mercelab.seguimiento._setting._api.exceptions.SaveException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.*;


@ControllerAdvice()
public class ApiExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({Exception.class,SQLException.class, DataAccessException.class})
    @ResponseBody
    public ResponseEntity<Object> error(Exception ex) {

        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = ex.getMessage();

        if (ex instanceof DataIntegrityViolationException){
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            errorMessage = ex.getMessage();
        }else if(ex instanceof NotValidException globalPersonalizado) {
            List<ObjectError> ValidErrores = globalPersonalizado.getBindingResult().getAllErrors();
            Map<String, String> errors = new HashMap<>();
            for (ObjectError error : ValidErrores) {
                if (error instanceof FieldError) {
                    String fieldName = ((FieldError) error).getField();
                    String fielError = getMessageSource(error);
                    errors.put(fieldName, fielError);
                } else {
                    errors.put(error.getCode(), error.getDefaultMessage());
                }
            }
            statusCode = HttpStatus.BAD_REQUEST;
            errorMessage = "Error de validación";
            return new ResponseWrapper().error(errorMessage,statusCode,errors);
        }else if (ex instanceof SaveException){
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            errorMessage = "Error,no se puede guardar este registro";
        }else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ValidErrores = (MethodArgumentNotValidException)ex;
            Map<String, String> errors = new HashMap<>();
            ValidErrores.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String fielError = error.getDefaultMessage();
                errors.put(fieldName, fielError);
            });
            statusCode = HttpStatus.BAD_REQUEST;
            errorMessage = "Error de validación";
        }else if(ex instanceof DeleteException) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            errorMessage = "Error,no se puede eliminar este registro";
        }else if(ex instanceof  EntityNotFoundException){
            statusCode = HttpStatus.NOT_FOUND;
            errorMessage = ex.getMessage();
        }else {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            errorMessage = "Error interno del servidor";
        }
        //errorMessage = ex.getMessage();
        return new ResponseWrapper().error(errorMessage,statusCode);
    }

    private String getMessageSource(ObjectError error) {
        String sourceError = error.getCode();
        String messageError = null;
        try {
            messageError = messageSource.getMessage(sourceError, null, Locale.getDefault());
            return messageError;
        } catch (NoSuchMessageException ex) {
            sourceError= ((FieldError) error).getObjectName() +"." +
                    ((FieldError) error).getField() +"."+
                    error.getCode();
            messageError = messageSource.getMessage(sourceError, null, error.getDefaultMessage(), Locale.getDefault());
            System.out.println(sourceError);
            return messageError;
        }
    }


}
