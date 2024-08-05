package com.tienda.Clases;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfiguracionArchivo {
    private static final String CONFIG_FILE = "config.properties";

    public static void guardarConfiguracion(String username, String password) {
        Properties properties = new Properties();
        properties.setProperty("username", username);
        properties.setProperty("password", password);

        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, "Configuración");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
