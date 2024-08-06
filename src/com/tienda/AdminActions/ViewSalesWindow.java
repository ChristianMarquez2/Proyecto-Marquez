package com.tienda.AdminActions;

import com.tienda.Clases.BaseDeDatos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewSalesWindow extends JFrame {

    public ViewSalesWindow() {
        setTitle("Ver Ventas");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        // Tabla de ventas
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Configurar la tabla
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre Cliente");
        tableModel.addColumn("Total");
        tableModel.addColumn("Fecha");
        tableModel.addColumn("Usuario");

        // Cargar datos de ventas
        loadSales(tableModel);
    }

    private void loadSales(DefaultTableModel tableModel) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = BaseDeDatos.getConnection();
            String query = "SELECT * FROM facturas ORDER BY fecha DESC";
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre_cliente"),
                        rs.getDouble("total"),
                        rs.getTimestamp("fecha"),
                        rs.getString("usuario")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las ventas", "Error", JOptionPane.ERROR_MESSAGE);
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
