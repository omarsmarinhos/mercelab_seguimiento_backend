package com.mercelab.seguimiento.models;



import com.mercelab.seguimiento.services.CapitalizeFirstLetter;

import java.lang.reflect.Field;

public abstract class GlobalEntity {
    public void toLowerCase() {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                try {
                    field.setAccessible(true);
                    String value = (String) field.get(this);
                    if (value != null) {
                        field.set(this, value.toLowerCase());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void capitalizeFields() {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getType() == String.class && field.isAnnotationPresent(CapitalizeFirstLetter.class)) {
                try {
                    field.setAccessible(true);
                    String value = (String) field.get(this);
                    if (value != null) {
                        field.set(this, value.substring(0, 1).toUpperCase() + value.substring(1));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
