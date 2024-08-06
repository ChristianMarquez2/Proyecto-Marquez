package com.tienda.CajeroActions;

import com.tienda.Clases.BaseDeDatos;

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
    private String nombreCliente;
    private String direccion;
    private String telefono;
    private String email;
    private String nitCi;
    private String productos;
    private double total;
    private String usuario;

    public ReportsWindow() {
        setTitle("Reportes");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadido margen

        // Panel para el área de texto
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder(BorderFactory.createTitledBorder("Contenido de Reportes")); // Añadido título

        reportsArea = new JTextArea();
        reportsArea.setEditable(false);
        reportsArea.setLineWrap(true);
        reportsArea.setWrapStyleWord(true);
        reportsArea.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Añadido borde
        reportsArea.setBackground(new Color(250, 250, 250)); // Añadido color de fondo
        JScrollPane scrollPane = new JScrollPane(reportsArea);
        textPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(textPanel, BorderLayout.CENTER);

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre botones

        JButton loadReportsButton = new JButton("Cargar Reportes");
        loadReportsButton.setBackground(new Color(0, 123, 255)); // Color de fondo
        loadReportsButton.setForeground(Color.WHITE); // Color de texto
        loadReportsButton.setFocusPainted(false);
        loadReportsButton.setPreferredSize(new Dimension(150, 40)); // Tamaño preferido
        loadReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarReportes();
            }
        });

        JButton saveReportsButton = new JButton("Guardar Reportes");
        saveReportsButton.setBackground(new Color(0, 123, 255)); // Color de fondo
        saveReportsButton.setForeground(Color.WHITE); // Color de texto
        saveReportsButton.setFocusPainted(false);
        saveReportsButton.setPreferredSize(new Dimension(150, 40)); // Tamaño preferido
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

    public void setDatosFactura(String nombreCliente, String direccion, String telefono, String email, String nitCi, String productos, double total, String usuario) {
        this.nombreCliente = nombreCliente;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.nitCi = nitCi;
        this.productos = productos;
        this.total = total;
        this.usuario = usuario;
    }

    private void cargarReportes() {
        StringBuilder sb = new StringBuilder();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = BaseDeDatos.getConnection();
            String query = "SELECT * FROM facturas";
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nombreUsuario = rs.getString("usuario");
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

    private void guardarReportes() {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = BaseDeDatos.getConnection();
            String query = "INSERT INTO facturas (nombre_cliente, direccion, telefono, email, nit_ci, productos, total, fecha, usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(query);

            stmt.setString(1, nombreCliente);
            stmt.setString(2, direccion);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setString(5, nitCi);
            stmt.setString(6, productos);
            stmt.setDouble(7, total);
            stmt.setTimestamp(8, new java.sql.Timestamp(new java.util.Date().getTime()));
            stmt.setString(9, usuario);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Reporte guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el reporte", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
