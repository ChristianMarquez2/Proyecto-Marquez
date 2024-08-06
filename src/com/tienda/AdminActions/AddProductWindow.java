package com.tienda.AdminActions;

import com.tienda.Clases.BaseDeDatos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddProductWindow extends JFrame {

    private JTextField nameField, priceField, stockField, brandField, modelField, imageField;
    private JButton saveButton, cancelButton;

    public AddProductWindow() {
        setTitle("Agregar Producto");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título en la parte superior
        JLabel titleLabel = new JLabel("Agregar Nuevo Producto", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Etiquetas y campos
        String[] labels = {"Nombre:", "Precio:", "Stock:", "Marca:", "Modelo:", "Imagen:"};
        JTextField[] fields = {nameField, priceField, stockField, brandField, modelField, imageField};
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            formPanel.add(label, gbc);
            gbc.gridx = 1;
            fields[i] = new JTextField(20);
            formPanel.add(fields[i], gbc);
            gbc.gridx = 0;
            gbc.gridy++;
        }

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        saveButton = new JButton("Guardar");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(0, 122, 255)); // Azul
        saveButton.setOpaque(true);
        saveButton.setBorderPainted(false);
        saveButton.setFocusPainted(false);
        buttonPanel.add(saveButton);

        cancelButton = new JButton("Cancelar");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(0, 122, 255)); // Azul
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Acción para guardar producto
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProduct();
            }
        });

        // Acción para cancelar
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana
            }
        });
    }

    private void saveProduct() {
        Connection connection = null;
        PreparedStatement stmt = null;
        PreparedStatement stmtBrand = null;
        ResultSet rs = null;

        try {
            connection = BaseDeDatos.getConnection();

            // Obtener el id de la marca
            String queryBrand = "SELECT id FROM marcas WHERE nombre = ?";
            stmtBrand = connection.prepareStatement(queryBrand);
            stmtBrand.setString(1, brandField.getText());
            rs = stmtBrand.executeQuery();

            int brandId = 0;
            if (rs.next()) {
                brandId = rs.getInt("id");
            } else {
                JOptionPane.showMessageDialog(this, "Marca no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insertar el producto
            String query = "INSERT INTO productos (nombre, precio, stock, marca_id, modelo, imagen) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, nameField.getText());
            stmt.setDouble(2, Double.parseDouble(priceField.getText()));
            stmt.setInt(3, Integer.parseInt(stockField.getText()));
            stmt.setInt(4, brandId);
            stmt.setString(5, modelField.getText());
            stmt.setString(6, imageField.getText());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Producto agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmtBrand != null) stmtBrand.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
