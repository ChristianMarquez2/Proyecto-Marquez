package com.tienda.Clases;



import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * La clase Administrador representa a un usuario con privilegios de administrador en la tienda.
 * <p>
 * Un administrador puede agregar, actualizar y eliminar productos y usuarios,
 * así como generar informes de inventario y ventas.
 * </p>
 */
public class Administrador extends Usuario {
    private static final Logger logger = LoggerFactory.getLogger(Administrador.class);

    /**
     * Constructor parametrizado para crear un nuevo administrador.
     *
     * @param id          el ID del administrador
     * @param nombre      el nombre del administrador
     * @param contraseña  la contraseña del administrador
     */
    public Administrador(int id, String nombre, String contraseña) {
        super(id, nombre, contraseña, "Administrador");
    }

    /**
     * Constructor por defecto que crea un administrador con valores predeterminados.
     */
    public Administrador() {
        super(0, "Default", "default_password", "Administrador");
    }

    /**
     * Agrega un nuevo producto a la base de datos.
     *
     * @param producto el producto a agregar
     */
    public void agregarProducto(Producto producto) {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            productoDAO.agregarProducto(producto);
            logger.info("Producto agregado: " + producto.getNombre());
        } catch (SQLException e) {
            logger.error("Error al agregar el producto.", e);
        }
    }

    /**
     * Actualiza un producto existente en la base de datos.
     *
     * @param producto el producto a actualizar
     */
    public void actualizarProducto(Producto producto) {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            productoDAO.actualizarProducto(producto);
            logger.info("Producto actualizado: " + producto.getNombre());
        } catch (SQLException e) {
            logger.error("Error al actualizar el producto.", e);
        }
    }

    /**
     * Elimina un producto de la base de datos por su ID.
     *
     * @param productoId el ID del producto a eliminar
     */
    public void eliminarProducto(int productoId) {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            productoDAO.eliminarProducto(productoId);
            logger.info("Producto eliminado ID: " + productoId);
        } catch (SQLException e) {
            logger.error("Error al eliminar el producto.", e);
        }
    }

    /**
     * Muestra todos los productos en la base de datos.
     */
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

    /**
     * Muestra los detalles de una transacción por su ID.
     *
     * @param transacciónId el ID de la transacción a mostrar
     */
    public void verDetalleTransacción(int transacciónId) {
        TransaccionDAO transacciónDAO = new TransaccionDAO();
        try {
            Transaccion transacción = transacciónDAO.obtenerTransacciónPorId(transacciónId);
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

    /**
     * Agrega un nuevo usuario a la base de datos.
     *
     * @param usuario el usuario a agregar
     */
    public void agregarUsuario(Usuario usuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.agregarUsuario(usuario);
            logger.info("Usuario agregado: " + usuario.getNombre());
        } catch (SQLException e) {
            logger.error("Error al agregar el usuario.", e);
        }
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     *
     * @param usuario el usuario a actualizar
     */
    public void actualizarUsuario(Usuario usuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.actualizarUsuario(usuario);
            logger.info("Usuario actualizado: " + usuario.getNombre());
        } catch (SQLException e) {
            logger.error("Error al actualizar el usuario.", e);
        }
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
     *
     * @param usuarioId el ID del usuario a eliminar
     */
    public void eliminarUsuario(int usuarioId) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.eliminarUsuario(usuarioId);
            logger.info("Usuario eliminado ID: " + usuarioId);
        } catch (SQLException e) {
            logger.error("Error al eliminar el usuario.", e);
        }
    }

    /**
     * Muestra todos los usuarios en la base de datos.
     */
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

    /**
     * Genera un informe en PDF del inventario de productos.
     */
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

    /**
     * Genera un informe en PDF de las ventas.
     */
    public void generarInformeVentas() {
        TransaccionDAO transacciónDAO = new TransaccionDAO();
        try {
            List<Transaccion> transacciones = transacciónDAO.obtenerTransacciones();
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

                for (Transaccion transacción : transacciones) {
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
