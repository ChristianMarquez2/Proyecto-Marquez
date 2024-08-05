package com.tienda.Clases;

public class Usuario {
    private int id;
    private String nombre;
    private String contraseña; // Añadido campo contraseña
    private String rol;

    // Constructor completo
    public Usuario(int id, String nombre, String contraseña, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Constructor sin contraseña
    public Usuario(int id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.contraseña = ""; // Contraseña vacía o null
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // Método para verificar si la contraseña es válida
    public boolean verificarContraseña(String contraseña) {
        // Aquí puedes implementar lógica de verificación si es necesario
        return this.contraseña.equals(contraseña);
    }
}
