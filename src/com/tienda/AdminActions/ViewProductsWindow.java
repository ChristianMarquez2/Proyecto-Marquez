package com.tienda.AdminActions;

import com.tienda.Clases.BaseDeDatos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewProductsWindow extends JFrame {

    public ViewProductsWindow() {
        setTitle("Ver Productos");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        // Tabla de productos
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Configurar la tabla
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Precio");
        tableModel.addColumn("Stock");
        tableModel.addColumn("Marca");
        tableModel.addColumn("Modelo");

        // Cargar datos de productos
        loadProducts(tableModel);
    }

    private void loadProducts(DefaultTableModel tableModel) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = BaseDeDatos.getConnection();
            String query = "SELECT p.id, p.nombre, p.precio, p.stock, m.nombre AS marca, p.modelo " +
                    "FROM productos p LEFT JOIN marcas m ON p.marca_id = m.id " +
                    "ORDER BY p.nombre";
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

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
            JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
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
