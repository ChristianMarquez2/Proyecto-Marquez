package com.tienda.Clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ventana para mostrar y gestionar los reportes administrativos.
 */
public class AdminReportsWindow extends JFrame {
    private JTextArea reportsArea;

    /**
     * Constructor para inicializar la ventana de reportes administrativos.
     */
    public AdminReportsWindow() {
        setTitle("Reportes Administrativos");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder(BorderFactory.createTitledBorder("Contenido de Reportes"));

        reportsArea = new JTextArea();
        reportsArea.setEditable(false);
        reportsArea.setLineWrap(true);
        reportsArea.setWrapStyleWord(true);
        reportsArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        reportsArea.setBackground(new Color(250, 250, 250));
        JScrollPane scrollPane = new JScrollPane(reportsArea);
        textPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(textPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton loadReportsButton = new JButton("Cargar Reportes");
        loadReportsButton.setBackground(new Color(0, 123, 255));
        loadReportsButton.setForeground(Color.WHITE); // Color de texto
        loadReportsButton.setFocusPainted(false);
        loadReportsButton.setPreferredSize(new Dimension(150, 40));
        loadReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarReportes();
            }
        });

        JButton saveReportsButton = new JButton("Guardar Reportes");
        saveReportsButton.setBackground(new Color(0, 123, 255));
        saveReportsButton.setForeground(Color.WHITE);
        saveReportsButton.setFocusPainted(false);
        saveReportsButton.setPreferredSize(new Dimension(150, 40));
        saveReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarReportes();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(loadReportsButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(saveReportsButton, gbc);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Carga los reportes desde la base de datos y los muestra en el área de texto.
     */
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

    /**
     * Muestra un mensaje indicando que la funcionalidad para guardar reportes no está implementada.
     */
    private void guardarReportes() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de guardar reportes no implementada", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
