package org.example;

public class Videojuegos {
    private String nombre;
    private String genero;
    private String plataforma;
    private int jugadores;
    private String desarrolladora;
    private float precio;
    private int lanzamiento;

    public Videojuegos(String nombre, String genero, String plataforma, int jugadores, String desarrolladora, float precio, int lanzamiento) {
        this.nombre = nombre;
        this.genero = genero;
        this.plataforma = plataforma;
        this.jugadores = jugadores;
        this.desarrolladora = desarrolladora;
        this.precio = precio;
        this.lanzamiento = lanzamiento;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getPlataforma() {
        return plataforma;
    }
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
    public int getJugadores() {
        return jugadores;
    }
    public void setJugadores(int jugadores) {
        this.jugadores = jugadores;
    }
    public String getDesarrolladora() {
        return desarrolladora;
    }
    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }
    public float getPrecio() {
        return precio;
    }
    public void setPrecio(float precio) {
        this.precio = precio;
    }
    public int getLanzamiento() {
        return lanzamiento;
    }
    public void setLanzamiento(int lanzamiento) {
        this.lanzamiento = lanzamiento;
    }
    public String detalles() {
        return "Nombre: " + nombre + ", Género: " + genero + ", Plataforma: " + plataforma +
                ", Jugadores: " + jugadores + ", Desarrolladora: " + desarrolladora +
                ", Precio: " + precio + ", Lanzamiento: " + lanzamiento;
    }

    public void imprimirDetalles() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Género: " + genero);
        System.out.println("Plataforma: " + plataforma);
        System.out.println("Número máximo de jugadores: " + jugadores);
        System.out.println("Desarrolladora: " + desarrolladora);
        System.out.println("Precio: " + precio);
        System.out.println("Año de lanzamiento: " + lanzamiento);
        System.out.println();
    }
}
