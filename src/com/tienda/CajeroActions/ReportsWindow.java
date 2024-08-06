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
import java.util.Date;

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

        JButton saveReportsButton = new JButton("Guardar Reportes");
        saveReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarReportes();
            }
        });

        bottomPanel.add(loadReportsButton);
        bottomPanel.add(saveReportsButton);

        reportsPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(reportsPanel);
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