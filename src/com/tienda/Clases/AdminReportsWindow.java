package com.tienda.Clases;

import com.tienda.Clases.BaseDeDatos;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminReportsWindow extends JFrame {
    private JTextArea reportsArea;

    public AdminReportsWindow() {
        setTitle("Reportes Administrativos");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel reportsPanel = new JPanel(new BorderLayout());

        reportsArea = new JTextArea();
        reportsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportsArea);
        reportsPanel.add(scrollPane, BorderLayout.CENTER);

        JButton loadReportsButton = new JButton("Cargar Reportes");
        loadReportsButton.addActionListener(e -> cargarReportes());
        reportsPanel.add(loadReportsButton, BorderLayout.SOUTH);

        add(reportsPanel);
    }

    private void cargarReportes() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM facturas ORDER BY fecha DESC";

        try (Connection connection = BaseDeDatos.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id")).append("\n");
                sb.append("Nombre del Cliente: ").append(rs.getString("nombre_cliente")).append("\n");
                sb.append("Dirección: ").append(rs.getString("direccion")).append("\n");
                sb.append("Teléfono: ").append(rs.getString("telefono")).append("\n");
                sb.append("Email: ").append(rs.getString("email")).append("\n");
                sb.append("NIT/CI: ").append(rs.getString("nit_ci")).append("\n");
                sb.append("Productos:\n").append(rs.getString("productos")).append("\n");
                sb.append("Total: $").append(rs.getDouble("total")).append("\n");
                sb.append("Fecha: ").append(rs.getTimestamp("fecha")).append("\n");
                sb.append("Usuario: ").append(rs.getString("usuario")).append("\n");
                sb.append("------------------------------------------------\n");
            }

            reportsArea.setText(sb.toString());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los reportes", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
