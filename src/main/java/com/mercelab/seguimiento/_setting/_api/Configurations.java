package com.mercelab.seguimiento._setting._api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;


@Component
@EnableConfigurationProperties
public class Configurations {

    public String getAppVersion() {
        String appVersion = readProperties().getProperty("app.version");
        if (!appVersion.isEmpty()){
            return appVersion + "." + getAppDate();
        }else{
            return "0.0.0.0";
        }
    }

    public String getAppDate() {
        String appVersion = readProperties().getProperty("app.date");
        if (!appVersion.isEmpty()){
            return appVersion;
        }else{
            return "0.0.0";
        }
    }

    private Properties readProperties() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
