package com.tienda.Clases;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * La clase {@code ConfiguracionArchivo} se encarga de manejar la configuración de la aplicación.
 * Proporciona métodos para guardar la configuración en un archivo de propiedades.
 */
public class ConfiguracionArchivo {
    private static final String CONFIG_FILE = "config.properties";

    /**
     * Guarda la configuración en un archivo de propiedades.
     *
     * @param username El nombre de usuario a guardar.
     * @param password La contraseña a guardar.
     */
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
