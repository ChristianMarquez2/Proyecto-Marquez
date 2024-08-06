package com.tienda.GUI;

import com.tienda.Clases.BaseDeDatos; // Asegúrate de que el nombre de la clase es correcto

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportsWindow extends JFrame {
    private JTextArea reportsArea;

    public ReportsWindow() {
        setTitle("Reportes");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel reportsPanel = new JPanel(new BorderLayout());

        reportsArea = new JTextArea();
        reportsArea.setEditable(false);
        reportsArea.setLineWrap(true);
        reportsArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(reportsArea);
        reportsPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton loadReportsButton = new JButton("Cargar Reportes");
        loadReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarReportes();
            }
        });

        bottomPanel.add(loadReportsButton);

        reportsPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(reportsPanel);
    }

    private void cargarReportes() {
        StringBuilder sb = new StringBuilder();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = BaseDeDatos.getConnection();
            String query = "SELECT * FROM facturas"; // Cambia esto si la tabla se llama `reportes`
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nombreUsuario = rs.getString("usuario"); // Ajusta el nombre del campo si es necesario
                String contenido = "Nombre del Cliente: " + rs.getString("nombre_cliente") + "\n" +
                        "Dirección: " + rs.getString("direccion") + "\n" +
                        "Teléfono: " + rs.getString("telefono") + "\n" +
                        "Email: " + rs.getString("email") + "\n" +
                        "NIT/CI: " + rs.getString("nit_ci") + "\n" +
                        "Productos:\n" + rs.getString("productos") + "\n" +
                        "Total: $" + rs.getDouble("total") + "\n" +
                        "Fecha: " + rs.getTimestamp("fecha") + "\n";

                sb.append("Fecha: ").append(rs.getTimestamp("fecha")).append("\n")
                        .append("Usuario: ").append(nombreUsuario).append("\n")
                        .append("Contenido:\n").append(contenido).append("\n\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los reportes", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Cerrar recursos en el bloque finally
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        reportsArea.setText(sb.toString());
    }
}
