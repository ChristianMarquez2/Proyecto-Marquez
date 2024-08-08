package org.example;

import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);

    Libro[] libros = new Libro[10];
    Videojuegos[] videojuegos = new Videojuegos[10];

    int opcion;

    public static void main(String[] args) {
        Main main = new Main();
        main.menu();
    }

    public void menu() {
        do {
            System.out.println("Menú:");
            System.out.println("1. Ingresar libro");
            System.out.println("2. Ingresar videojuego");
            System.out.println("3. Revisar libros");
            System.out.println("4. Revisar videojuegos");
            System.out.println("5. Modificar libro");
            System.out.println("6. Modificar videojuego");
            System.out.println("0. Salir");
            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    for (int i = 0; i < libros.length; i++) {
                        if (libros[i] == null) {
                            libros[i] = ingresarLibro();
                            break;
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < videojuegos.length; i++) {
                        if (videojuegos[i] == null) {
                            videojuegos[i] = ingresarVideojuego();
                            break;
                        }
                    }
                    break;
                case 3:
                    System.out.println("Libros:");
                    for (Libro libro : libros) {
                        if (libro != null) {
                            libro.imprimirDetalles();
                        }
                    }
                    break;
                case 4:
                    System.out.println("Videojuegos:");
                    for (Videojuegos juego : videojuegos) {
                        if (juego != null) {
                            juego.imprimirDetalles();
                        }
                    }
                    break;
                case 5:
                    System.out.print("Ingrese el título del libro que desea modificar: ");
                    String tituloLibro = scanner.next();
                    for (Libro libro : libros) {
                        if (libro != null && libro.getTitulo().equals(tituloLibro)) {
                            modificarLibro(libro);
                            break;
                        }
                    }
                    break;
                case 6:
                    System.out.print("Ingrese el nombre del videojuego que desea modificar: ");
                    String nombreJuego = scanner.next();
                    for (Videojuegos juego : videojuegos) {
                        if (juego != null && juego.getNombre().equals(nombreJuego)) {
                            modificarVideojuego(juego);
                            break;
                        }
                    }
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (opcion != 0);
    }

    public Libro ingresarLibro() {
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.next();
        System.out.print("Ingrese el autor del libro: ");
        String autor = scanner.next();
        System.out.print("Ingrese el número de páginas: ");
        int paginas = scanner.nextInt();
        System.out.print("Ingrese la editorial: ");
        String editorial = scanner.next();
        System.out.print("Ingrese el año de publicación: ");
        int anioPublicacion = scanner.nextInt();
        System.out.print("Ingrese el número de ISBN: ");
        String isbn = scanner.next();
        System.out.print("Ingrese el idioma: ");
        String idioma = scanner.next();
        System.out.print("El libro está disponible? (true/false): ");
        boolean disponible = scanner.nextBoolean();
        System.out.print("Ingrese el precio: ");
        float precio = scanner.nextFloat();
        System.out.print("Ingrese la categoría: ");
        String categoria = scanner.next();
        return new Libro(titulo, autor, paginas, editorial, anioPublicacion, isbn, idioma, disponible, precio, categoria);
    }

    public Videojuegos ingresarVideojuego() {
        System.out.print("Ingrese el nombre del videojuego: ");
        String nombre = scanner.next();
        System.out.print("Ingrese el género del videojuego: ");
        String genero = scanner.next();
        System.out.print("Ingrese la plataforma del videojuego: ");
        String plataforma = scanner.next();
        System.out.print("Ingrese el número máximo de jugadores: ");
        int jugadores = scanner.nextInt();
        System.out.print("Ingrese la desarrolladora del videojuego: ");
        String desarrolladora = scanner.next();
        System.out.print("Ingrese el precio del videojuego: ");
        float precio = scanner.nextFloat();
        System.out.print("Ingrese el año de lanzamiento del videojuego: ");
        int lanzamiento = scanner.nextInt();

        return new Videojuegos(nombre, genero, plataforma, jugadores, desarrolladora, precio, lanzamiento);
    }

    public void modificarLibro(Libro libro) {
        System.out.print("Ingrese el nuevo título del libro: ");
        String nuevoTitulo = scanner.next();
        libro.setTitulo(nuevoTitulo);
        System.out.print("Ingrese el nuevo autor del libro: ");
        String nuevoAutor = scanner.next();
        libro.setAutor(nuevoAutor);
        System.out.print("Ingrese el nuevo número de páginas: ");
        int nuevasPaginas = scanner.nextInt();
        libro.setPaginas(nuevasPaginas);
        System.out.print("Ingrese la nueva editorial del libro: ");
        String nuevaEditorial = scanner.next();
        libro.setEditorial(nuevaEditorial);
        System.out.print("Ingrese el nuevo año de publicación del libro: ");
        int nuevoAnioPublicacion = scanner.nextInt();
        libro.setAnioPublicacion(nuevoAnioPublicacion);
        System.out.print("Ingrese el nuevo número de ISBN del libro: ");
        String nuevoIsbn = scanner.next();
        libro.setIsbn(nuevoIsbn);
        System.out.print("Ingrese el nuevo idioma del libro: ");
        String nuevoIdioma = scanner.next();
        libro.setIdioma(nuevoIdioma);
        System.out.print("El libro está disponible? (true/false): ");
        boolean nuevaDisponibilidad = scanner.nextBoolean();
        libro.setDisponible(nuevaDisponibilidad);
        System.out.print("Ingrese el nuevo precio del libro: ");
        float nuevoPrecio = scanner.nextFloat();
        libro.setPrecio(nuevoPrecio);
        System.out.print("Ingrese la nueva categoría del libro: ");
        String nuevaCategoria = scanner.next();
        libro.setCategoria(nuevaCategoria);
        libro.imprimirDetalles();
    }

    public void modificarVideojuego(Videojuegos juego) {
        System.out.print("Ingrese el nuevo nombre del videojuego: ");
        String nuevoNombre = scanner.next();
        juego.setNombre(nuevoNombre);
        System.out.print("Ingrese el nuevo género del videojuego: ");
        String nuevoGenero = scanner.next();
        juego.setGenero(nuevoGenero);
        System.out.print("Ingrese la nueva plataforma del videojuego: ");
        String nuevaPlataforma = scanner.next();
        juego.setPlataforma(nuevaPlataforma);
        System.out.print("Ingrese el nuevo número máximo de jugadores: ");
        int nuevosJugadores = scanner.nextInt();
        juego.setJugadores(nuevosJugadores);
        System.out.print("Ingrese la nueva desarrolladora del videojuego: ");
        String nuevaDesarrolladora = scanner.next();
        juego.setDesarrolladora(nuevaDesarrolladora);
        System.out.print("Ingrese el nuevo precio del videojuego: ");
        float nuevoPrecio = scanner.nextFloat();
        juego.setPrecio(nuevoPrecio);
        System.out.print("Ingrese el nuevo año de lanzamiento del videojuego: ");
        int nuevoLanzamiento = scanner.nextInt();
        juego.setLanzamiento(nuevoLanzamiento);
        System.out.println(juego);
    }
}
