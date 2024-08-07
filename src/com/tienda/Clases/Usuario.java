package com.tienda.Clases;

/**
 * La clase {@code Usuario} representa a un usuario del sistema con sus detalles esenciales,
 * incluyendo su identificador, nombre, contraseña y rol.
 */
public class Usuario {
    private int id;
    private String nombre;
    private String contraseña;
    private String rol;

    /**
     * Constructor que inicializa un usuario con todos los detalles.
     *
     * @param id El identificador único del usuario.
     * @param nombre El nombre del usuario.
     * @param contraseña La contraseña del usuario.
     * @param rol El rol del usuario (por ejemplo, 'Administrador' o 'Cajero').
     */
    public Usuario(int id, String nombre, String contraseña, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    /**
     * Constructor que inicializa un usuario sin contraseña.
     *
     * @param id El identificador único del usuario.
     * @param nombre El nombre del usuario.
     * @param rol El rol del usuario (por ejemplo, 'Administrador' o 'Cajero').
     */
    public Usuario(int id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.contraseña = "";
    }

    // Getters y Setters

    /**
     * Obtiene el identificador del usuario.
     *
     * @return El identificador del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario.
     *
     * @param id El nuevo identificador del usuario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre El nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param contraseña La nueva contraseña del usuario.
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return El rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol El nuevo rol del usuario.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Verifica si la contraseña proporcionada coincide con la contraseña del usuario.
     *
     * @param contraseña La contraseña a verificar.
     * @return {@code true} si la contraseña coincide, {@code false} en caso contrario.
     */
    public boolean verificarContraseña(String contraseña) {
        return this.contraseña.equals(contraseña);
    }
}
