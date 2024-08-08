package org.example;

public class Libro {
    private String titulo;
    private String autor;
    private int paginas;
    private String editorial;
    private int anioPublicacion;
    private String isbn;
    private String idioma;
    private boolean disponible;
    private float precio;
    private String categoria;

    public Libro(String titulo, String autor, int paginas, String editorial, int anioPublicacion, String isbn, String idioma, boolean disponible, float precio, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.isbn = isbn;
        this.idioma = idioma;
        this.disponible = disponible;
        this.precio = precio;
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public int getPaginas() {
        return paginas;
    }
    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }
    public String getEditorial() {
        return editorial;
    }
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    public int getAnioPublicacion() {
        return anioPublicacion;
    }
    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getIdioma() {
        return idioma;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    public boolean isDisponible() {
        return disponible;
    }
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    public float getPrecio() {
        return precio;
    }
    public void setPrecio(float precio) {
        this.precio = precio;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void prestar() {
        if (disponible) {
            disponible = false;
            System.out.println("El libro ha sido prestado.");
        } else {
            System.out.println("El libro no está disponible para ser prestado.");
        }
    }
    public void devolver() {
        if (!disponible) {
            disponible = true;
            System.out.println("El libro ha sido devuelto.");
        } else {
            System.out.println("El libro ya está disponible.");
        }
    }
    public void imprimirDetalles() {
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Páginas: " + paginas);
        System.out.println("Editorial: " + editorial);
        System.out.println("Año de publicación: " + anioPublicacion);
        System.out.println("ISBN: " + isbn);
        System.out.println("Idioma: " + idioma);
        System.out.println("Disponible: " + (disponible ? "Sí" : "No"));
        System.out.println("Precio: " + precio);
        System.out.println("Categoría: " + categoria);
    }
}
