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

    private JTable table;
    private DefaultTableModel tableModel;

    public ViewSalesWindow() {
        setTitle("Ver Ventas");
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

        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre Cliente");
        tableModel.addColumn("Total");
        tableModel.addColumn("Fecha");
        tableModel.addColumn("Usuario");

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

        // Cargar datos de ventas
        loadSales();

        // Acción para actualizar datos
        refreshButton.addActionListener(e -> loadSales());
    }

    private void loadSales() {
        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

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
            JOptionPane.showMessageDialog(this, "Error al cargar las ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
