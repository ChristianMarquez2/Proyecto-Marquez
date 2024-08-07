package com.tienda.Clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code ProductoDAO} proporciona métodos para interactuar con la tabla de productos
 * en la base de datos.
 */
public class ProductoDAO {

    /**
     * Obtiene una lista de productos basados en el identificador de la marca.
     *
     * @param marcaId El identificador de la marca.
     * @return Lista de productos asociados a la marca.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public List<Producto> obtenerProductosPorMarca(int marcaId) throws SQLException {
        String query = "SELECT * FROM productos WHERE marca_id = ?";
        return obtenerProductos(query, marcaId);
    }

    /**
     * Obtiene una lista de productos basados en el modelo del producto.
     *
     * @param modelo El modelo del producto.
     * @return Lista de productos con el modelo especificado.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public List<Producto> obtenerProductosPorModelo(String modelo) throws SQLException {
        String query = "SELECT * FROM productos WHERE modelo = ?";
        return obtenerProductos(query, modelo);
    }

    /**
     * Agrega un nuevo producto a la base de datos.
     *
     * @param producto El producto a agregar.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public void agregarProducto(Producto producto) throws SQLException {
        String query = "INSERT INTO productos (nombre, precio, stock, imagen, marca_id, modelo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            prepararStatement(stmt, producto);
            stmt.executeUpdate();
        }
    }

    /**
     * Actualiza un producto existente en la base de datos.
     *
     * @param producto El producto con los nuevos datos.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public void actualizarProducto(Producto producto) throws SQLException {
        String query = "UPDATE productos SET nombre = ?, precio = ?, stock = ?, imagen = ?, marca_id = ?, modelo = ? WHERE id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            prepararStatement(stmt, producto);
            stmt.setInt(7, producto.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Actualiza el stock de un producto específico.
     *
     * @param productoId El identificador del producto.
     * @param nuevoStock El nuevo valor de stock.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public void actualizarStock(int productoId, int nuevoStock) throws SQLException {
        String query = "UPDATE productos SET stock = ? WHERE id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, productoId);
            stmt.executeUpdate();
        }
    }

    /**
     * Elimina un producto de la base de datos basado en su identificador.
     *
     * @param productoId El identificador del producto a eliminar.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public void eliminarProducto(int productoId) throws SQLException {
        String query = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productoId);
            stmt.executeUpdate();
        }
    }

    /**
     * Obtiene una lista de productos basados en el nombre (con búsqueda parcial).
     *
     * @param nombre El nombre del producto.
     * @return Lista de productos con nombres que contienen el texto especificado.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public List<Producto> obtenerProductosPorNombre(String nombre) throws SQLException {
        String query = "SELECT * FROM productos WHERE nombre LIKE ?";
        return obtenerProductos(query, "%" + nombre + "%");
    }

    /**
     * Obtiene una lista de todos los productos en la base de datos.
     *
     * @return Lista de todos los productos.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public List<Producto> obtenerProductos() throws SQLException {
        String query = "SELECT * FROM productos";
        List<Producto> productos = new ArrayList<>();
        try (Connection conn = BaseDeDatos.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                productos.add(mapResultSetToProducto(rs));
            }
        }
        return productos;
    }

    // Métodos auxiliares

    /**
     * Obtiene una lista de productos basado en una consulta específica y parámetro.
     *
     * @param query La consulta SQL.
     * @param parametro El parámetro para la consulta.
     * @return Lista de productos resultantes de la consulta.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    private List<Producto> obtenerProductos(String query, Object parametro) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, parametro);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(mapResultSetToProducto(rs));
                }
            }
        }
        return productos;
    }

    /**
     * Prepara un {@code PreparedStatement} con los datos del producto.
     *
     * @param stmt El {@code PreparedStatement} a preparar.
     * @param producto El producto con los datos a establecer.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    private void prepararStatement(PreparedStatement stmt, Producto producto) throws SQLException {
        stmt.setString(1, producto.getNombre());
        stmt.setDouble(2, producto.getPrecio());
        stmt.setInt(3, producto.getStock());
        stmt.setString(4, producto.getImagenPath());
        stmt.setInt(5, producto.getMarcaId());
        stmt.setString(6, producto.getModelo());
    }

    /**
     * Mapea un {@code ResultSet} a un objeto {@code Producto}.
     *
     * @param rs El {@code ResultSet} con los datos del producto.
     * @return El objeto {@code Producto}.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    private Producto mapResultSetToProducto(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getInt("stock"),
                rs.getString("imagen"),
                rs.getInt("marca_id"),
                rs.getString("modelo")
        );
    }
}
