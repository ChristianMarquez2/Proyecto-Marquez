package com.tienda.model;

import java.sql.SQLException;
import java.util.List;

public class Administrador extends Usuario {
    public Administrador(int id, String nombre, String contraseña) {
        super(id, nombre, contraseña, "Administrador");
    }
    // Métodos específicos del administrador

    // Método para agregar un nuevo producto
    public void agregarProducto(Producto producto) {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            productoDAO.agregarProducto(producto);
            System.out.println("Producto agregado: " + producto.getNombre());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al agregar el producto.");
        }
    }

    // Método para actualizar un producto existente
    public void actualizarProducto(Producto producto) {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            productoDAO.actualizarProducto(producto);
            System.out.println("Producto actualizado: " + producto.getNombre());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el producto.");
        }
    }

    // Método para eliminar un producto
    public void eliminarProducto(int productoId) {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            productoDAO.eliminarProducto(productoId);
            System.out.println("Producto eliminado ID: " + productoId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el producto.");
        }
    }

    // Método para ver todos los productos
    public void verProductos() {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            List<Producto> productos = productoDAO.obtenerProductos();
            for (Producto producto : productos) {
                System.out.println(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los productos.");
        }
    }

    // Método para ver detalles de una transacción específica
    public void verDetalleTransacción(int transacciónId) {
        TransacciónDAO transacciónDAO = new TransacciónDAO();
        try {
            Transacción transacción = transacciónDAO.obtenerTransacciónPorId(transacciónId);
            System.out.println("Transacción ID: " + transacción.getId());
            System.out.println("Cajero: " + transacción.getCajero().getNombre());
            System.out.println("Productos:");
            for (Producto producto : transacción.getProductos()) {
                System.out.println(" - " + producto.getNombre() + ": " + producto.getPrecio());
            }
            System.out.println("Total: " + transacción.getTotal());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los detalles de la transacción.");
        }
    }

    // Método para agregar un nuevo usuario
    public void agregarUsuario(Usuario usuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.agregarUsuario(usuario);
            System.out.println("Usuario agregado: " + usuario.getNombre());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al agregar el usuario.");
        }
    }

    // Método para actualizar un usuario existente
    public void actualizarUsuario(Usuario usuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.actualizarUsuario(usuario);
            System.out.println("Usuario actualizado: " + usuario.getNombre());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el usuario.");
        }
    }

    // Método para eliminar un usuario
    public void eliminarUsuario(int usuarioId) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.eliminarUsuario(usuarioId);
            System.out.println("Usuario eliminado ID: " + usuarioId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el usuario.");
        }
    }

    // Método para ver todos los usuarios
    public void verUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los usuarios.");
        }
    }

    // Método para generar informe de inventario
    public void generarInformeInventario() {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            List<Producto> productos = productoDAO.obtenerProductos();
            System.out.println("Generando informe de inventario...");
            // Aquí puedes agregar lógica para generar el informe, por ejemplo usando PDF o Excel
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al generar el informe de inventario.");
        }
    }

    // Método para generar un informe de ventas
    public void generarInformeVentas() {
        TransacciónDAO transacciónDAO = new TransacciónDAO();
        try {
            List<Transacción> transacciones = transacciónDAO.obtenerTransacciones();
            System.out.println("Generando informe de ventas...");
            // Aquí puedes agregar lógica para generar el informe, por ejemplo usando PDF o Excel
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al generar el informe de ventas.");
        }
    }
}
