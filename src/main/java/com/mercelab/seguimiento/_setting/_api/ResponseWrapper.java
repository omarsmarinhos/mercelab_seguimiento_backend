package com.mercelab.seguimiento._setting._api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ResponseWrapper {




    protected  ResponseEntity<Object> successResponse(Map<String, Object> data, HttpStatusCode status) {
        return new ResponseEntity<>(data, status);
    }
    public  ResponseEntity<Object> error(String errorMessage, HttpStatusCode statusCode){
        return errorResponse(errorMessage,statusCode,null);
    }
    public  ResponseEntity<Object> error(String errorMessage, HttpStatus statusCode, Map<String, String> errors) {
        return errorResponse(errorMessage,statusCode,errors);
    }

    protected  ResponseEntity<Object> errorResponse(String message, HttpStatusCode status,Map<String, String> errors) {
        Map<String, Object> response = new HashMap<>();

        response.put("code", status);
        if (errors != null){
            response.put("error", errors);
        }else{
            response.put("error", message);
        }
        return new ResponseEntity<>(response, status);
    }

    private Object getMetaInfo() {

       Configurations configurations = new Configurations();
        Map<String, Object> data = new HashMap<>();
       // data.put("role","error");
        data.put("version", configurations.getAppVersion());
        return data;
    }

    public  ResponseEntity<Object> showAll(Collection<?> collection){
        Map<String, Object> response = new HashMap<>();
        response.put("meta", getMetaInfo());
        response.put("data", collection);
        if(collection == null || collection.isEmpty()){
            return successResponse(response,HttpStatus.NO_CONTENT);
        }else{
            return successResponse(response,HttpStatus.OK);
        }
    }



    public  ResponseEntity<Object> showOne(Object object) {
        Map<String, Object> response = new HashMap<>();
        response.put("meta", getMetaInfo());
        response.put("data", object);
        return successResponse(response,HttpStatus.OK);
    }

    public  ResponseEntity<Object> savedSuccessfully() {
        Map<String, Object> response = new HashMap<>();
        response.put("meta", getMetaInfo());
        return successResponse(response,HttpStatus.CREATED);
    }

    public  ResponseEntity<Object> savedSuccessfully(Object object) {
        Map<String, Object> response = new HashMap<>();
        response.put("meta", getMetaInfo());
        response.put("data", object);
        return successResponse(response,HttpStatus.CREATED);
    }

    public  ResponseEntity<Object> updateSuccessfully(Object object) {
        Map<String, Object> response = new HashMap<>();
        response.put("meta", getMetaInfo());
        response.put("data", object);
        return successResponse(response,HttpStatus.OK);
    }

    public  ResponseEntity<Object>
    deleteSuccessfully() {
        Map<String, Object> response = new HashMap<>();
        return successResponse(response,HttpStatus.NO_CONTENT);
    }
}
