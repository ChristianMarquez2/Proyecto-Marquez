package com.tienda.AdminActions;

import com.tienda.Clases.BaseDeDatos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddProductWindow extends JFrame {

    private JTextField nameField, priceField, stockField, brandField, modelField, imageField;

    public AddProductWindow() {
        setTitle("Agregar Producto");
        setSize(400, 350);  // Ajustado el tamaño para acomodar el nuevo campo
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 5, 5));  // Ajustado el diseño para el nuevo campo

        // Campos de entrada
        add(new JLabel("Nombre:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Precio:"));
        priceField = new JTextField();
        add(priceField);

        add(new JLabel("Stock:"));
        stockField = new JTextField();
        add(stockField);

        add(new JLabel("Marca:"));
        brandField = new JTextField();
        add(brandField);

        add(new JLabel("Modelo:"));
        modelField = new JTextField();
        add(modelField);

        add(new JLabel("Imagen:"));
        imageField = new JTextField();  // Nuevo campo para la imagen
        add(imageField);

        JButton saveButton = new JButton("Guardar");
        add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProduct();
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
            stmt.setString(6, imageField.getText());  // Establecer el valor de la imagen

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
