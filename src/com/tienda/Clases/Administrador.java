package com.tienda.Clases;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Administrador extends Usuario {
    private static final Logger logger = LoggerFactory.getLogger(Administrador.class);

    public Administrador(int id, String nombre, String contraseña) {
        super(id, nombre, contraseña, "Administrador");
    }

    // Método para agregar un nuevo producto
    public void agregarProducto(Producto producto) {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            productoDAO.agregarProducto(producto);
            logger.info("Producto agregado: " + producto.getNombre());
        } catch (SQLException e) {
            logger.error("Error al agregar el producto.", e);
        }
    }

    // Método para actualizar un producto existente
    public void actualizarProducto(Producto producto) {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            productoDAO.actualizarProducto(producto);
            logger.info("Producto actualizado: " + producto.getNombre());
        } catch (SQLException e) {
            logger.error("Error al actualizar el producto.", e);
        }
    }

    // Método para eliminar un producto
    public void eliminarProducto(int productoId) {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            productoDAO.eliminarProducto(productoId);
            logger.info("Producto eliminado ID: " + productoId);
        } catch (SQLException e) {
            logger.error("Error al eliminar el producto.", e);
        }
    }

    // Método para ver todos los productos
    public void verProductos() {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            List<Producto> productos = productoDAO.obtenerProductos();
            for (Producto producto : productos) {
                logger.info(producto.toString());
            }
        } catch (SQLException e) {
            logger.error("Error al obtener los productos.", e);
        }
    }

    // Método para ver detalles de una transacción específica
    public void verDetalleTransacción(int transacciónId) {
        TransacciónDAO transacciónDAO = new TransacciónDAO();
        try {
            Transacción transacción = transacciónDAO.obtenerTransacciónPorId(transacciónId);
            logger.info("Transacción ID: " + transacción.getId());
            logger.info("Cajero: " + transacción.getCajero().getNombre());
            logger.info("Productos:");
            for (Producto producto : transacción.getProductos()) {
                logger.info(" - " + producto.getNombre() + ": " + producto.getPrecio());
            }
            logger.info("Total: " + transacción.getTotal());
        } catch (SQLException e) {
            logger.error("Error al obtener los detalles de la transacción.", e);
        }
    }

    // Método para agregar un nuevo usuario
    public void agregarUsuario(Usuario usuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.agregarUsuario(usuario);
            logger.info("Usuario agregado: " + usuario.getNombre());
        } catch (SQLException e) {
            logger.error("Error al agregar el usuario.", e);
        }
    }

    // Método para actualizar un usuario existente
    public void actualizarUsuario(Usuario usuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.actualizarUsuario(usuario);
            logger.info("Usuario actualizado: " + usuario.getNombre());
        } catch (SQLException e) {
            logger.error("Error al actualizar el usuario.", e);
        }
    }

    // Método para eliminar un usuario
    public void eliminarUsuario(int usuarioId) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.eliminarUsuario(usuarioId);
            logger.info("Usuario eliminado ID: " + usuarioId);
        } catch (SQLException e) {
            logger.error("Error al eliminar el usuario.", e);
        }
    }

    // Método para ver todos los usuarios
    public void verUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
            for (Usuario usuario : usuarios) {
                logger.info(usuario.toString());
            }
        } catch (SQLException e) {
            logger.error("Error al obtener los usuarios.", e);
        }
    }

    // Método para generar informe de inventario en PDF
    public void generarInformeInventario() {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            List<Producto> productos = productoDAO.obtenerProductos();
            String fileName = "Informe_Inventario.pdf";
            Document document = new Document();
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                PdfWriter.getInstance(document, fos);
                document.open();

                document.add(new Paragraph("Informe de Inventario"));
                PdfPTable table = new PdfPTable(3); // Número de columnas
                table.addCell("Nombre");
                table.addCell("Precio");
                table.addCell("Stock");

                for (Producto producto : productos) {
                    table.addCell(producto.getNombre());
                    table.addCell(String.valueOf(producto.getPrecio()));
                    table.addCell(String.valueOf(producto.getStock()));
                }

                document.add(table);
                document.close();
            }
            logger.info("Informe de inventario generado: " + fileName);
        } catch (SQLException | IOException | DocumentException e) {
            logger.error("Error al generar el informe de inventario.", e);
        }
    }

    // Método para generar un informe de ventas en PDF usando iText 5.x
    public void generarInformeVentas() {
        TransacciónDAO transacciónDAO = new TransacciónDAO();
        try {
            List<Transacción> transacciones = transacciónDAO.obtenerTransacciones();
            String fileName = "Informe_Ventas.pdf";
            Document document = new Document();
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                PdfWriter.getInstance(document, fos);
                document.open();

                document.add(new Paragraph("Informe de Ventas"));
                PdfPTable table = new PdfPTable(3); // Número de columnas
                table.addCell("ID Transacción");
                table.addCell("Cajero");
                table.addCell("Total");

                for (Transacción transacción : transacciones) {
                    table.addCell(String.valueOf(transacción.getId()));
                    table.addCell(transacción.getCajero().getNombre());
                    table.addCell(String.valueOf(transacción.getTotal()));
                }

                document.add(table);
                document.close();
            }
            logger.info("Informe de ventas generado: " + fileName);
        } catch (SQLException | IOException | DocumentException e) {
            logger.error("Error al generar el informe de ventas.", e);
        }
    }
}