package com.tienda.AdminActions;

import com.tienda.Clases.BaseDeDatos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ventana para visualizar la lista de productos en la tienda.
 * Muestra una tabla con detalles de los productos y permite actualizar la lista.
 */
public class ViewProductsWindow extends JFrame {

    private JTable table; // Tabla para mostrar los productos
    private DefaultTableModel tableModel; // Modelo de la tabla

    /**
     * Constructor que configura la ventana para ver productos.
     */
    public ViewProductsWindow() {
        setTitle("Ver Productos");
        setSize(900, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel para la tabla
        JPanel tablePanel = new JPanel(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);

        // Configurar la tabla
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Agregar columnas a la tabla
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Precio");
        tableModel.addColumn("Stock");
        tableModel.addColumn("Marca");
        tableModel.addColumn("Modelo");

        // Botón para actualizar datos
        JButton refreshButton = new JButton("Actualizar");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBackground(new Color(0, 122, 255)); // Azul
        refreshButton.setOpaque(true);
        refreshButton.setBorderPainted(false);
        refreshButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Cargar datos de productos
        loadProducts();

        // Acción para actualizar datos cuando se presiona el botón
        refreshButton.addActionListener(e -> loadProducts());
    }

    /**
     * Carga los datos de los productos desde la base de datos y actualiza la tabla.
     */
    private void loadProducts() {
        tableModel.setRowCount(0); // Limpiar la tabla antes de recargar los datos

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            connection = BaseDeDatos.getConnection();

            // Consulta SQL para obtener los productos junto con la marca
            String query = "SELECT p.id, p.nombre, p.precio, p.stock, m.nombre AS marca, p.modelo " +
                    "FROM productos p LEFT JOIN marcas m ON p.marca_id = m.id " +
                    "ORDER BY p.nombre";

            // Preparar y ejecutar la consulta
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

            // Procesar el resultado de la consulta y agregar filas a la tabla
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        rs.getString("marca"),
                        rs.getString("modelo")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Mostrar un mensaje de error si ocurre una excepción al cargar los productos
            JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Cerrar los recursos de base de datos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
