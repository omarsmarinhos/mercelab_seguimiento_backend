package com.mercelab.seguimiento._setting._api;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class BuildNumber {
    private static final String FILE_NAME = "application.properties";
    private static final String VERSION_PROPERTY = "app.version";
    private static final String VERSION_DATE = "app.date";

    public static void main(String[] args){
        String filePath = getFilePath();
        if (filePath != null) {
            Properties properties = readPropertiesFromFile(filePath);

            if (properties != null) {
                String currentVersion = properties.getProperty(VERSION_PROPERTY);
                String updatedVersion = incrementVersion(currentVersion);
                properties.setProperty(VERSION_PROPERTY, updatedVersion);
                properties.setProperty(VERSION_DATE, getCurrentDateTime());
                writePropertiesToFile(properties, filePath);
            }
        }
    }

    private static String getFilePath() {
        String projectPath = System.getProperty("user.dir");
        return projectPath + File.separator + "src"+ File.separator +"main" +File.separator+ "resources" + File.separator + FILE_NAME;
    }

    private static Properties readPropertiesFromFile(String filePath) {
        try (InputStream input = new FileInputStream(filePath)) {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException e) {
            // Manejar el error de lectura del archivo
            e.printStackTrace();
            return null;
        }
    }

    private static String incrementVersion(String version) {
        String[] parts = version.split("\\.");
        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);
        int patch = Integer.parseInt(parts[2]);

        patch++; // Incrementar el número de parche

        return major + "." + minor + "." + patch;
    }

    private static void writePropertiesToFile(Properties properties, String filePath) {
        try (OutputStream output = new FileOutputStream(filePath)) {
            properties.store(output, null);
        } catch (IOException e) {
            // Manejar el error de escritura en el archivo
            e.printStackTrace();
        }
    }

    private static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy.HHmm");
        return now.format(formatter);
    }
}