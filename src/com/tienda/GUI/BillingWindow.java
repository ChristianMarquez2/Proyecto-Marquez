package com.tienda.GUI;

import com.itextpdf.text.DocumentException;
import com.tienda.Clases.GeneradorPDF;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BillingWindow extends JFrame {
    private JTable productosTable;
    private DefaultTableModel productosTableModel;
    private JTextField nombreClienteField, direccionField, telefonoField, emailField, nitCiField;

    public BillingWindow(Object[][] cartData, String total) {
        setTitle("Facturación");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel de entrada de datos del cliente
        JPanel clientePanel = new JPanel(new GridLayout(6, 2, 5, 5)); // Ajustado a 6 filas y 2 columnas
        clientePanel.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

        clientePanel.add(new JLabel("Nombre del Cliente:"));
        nombreClienteField = new JTextField();
        clientePanel.add(nombreClienteField);

        clientePanel.add(new JLabel("Dirección:"));
        direccionField = new JTextField();
        clientePanel.add(direccionField);

        clientePanel.add(new JLabel("Teléfono:"));
        telefonoField = new JTextField();
        clientePanel.add(telefonoField);

        clientePanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        clientePanel.add(emailField);

        clientePanel.add(new JLabel("NIT/CI:"));
        nitCiField = new JTextField();
        clientePanel.add(nitCiField);

        mainPanel.add(clientePanel, BorderLayout.NORTH);

        // Panel de productos y precios
        JPanel productosPanel = new JPanel(new BorderLayout());
        productosPanel.setBorder(BorderFactory.createTitledBorder("Productos y Precios"));

        productosTableModel = new DefaultTableModel(new Object[]{"Producto", "Precio", "Cantidad"}, 0);
        productosTable = new JTable(productosTableModel);
        JScrollPane scrollPane = new JScrollPane(productosTable);
        productosPanel.add(scrollPane, BorderLayout.CENTER);

        // Agregar datos del carrito a la tabla de productos
        for (Object[] row : cartData) {
            productosTableModel.addRow(row);
        }

        mainPanel.add(productosPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout());

        JButton generarFacturaButton = new JButton("Generar Factura");
        botonesPanel.add(generarFacturaButton);

        JButton cancelarButton = new JButton("Cancelar");
        botonesPanel.add(cancelarButton);

        mainPanel.add(botonesPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Acción del botón "Generar Factura"
        generarFacturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Generar el PDF de la factura
                    String rutaArchivo = "Factura.pdf"; // Puedes cambiar la ruta si es necesario
                    GeneradorPDF.generarFactura(
                            nombreClienteField.getText(),
                            direccionField.getText(),
                            telefonoField.getText(),
                            emailField.getText(), // Agrega el email si es necesario
                            nitCiField.getText(), // Agrega el NIT/CI si es necesario
                            cartData,
                            Double.parseDouble(total.replace("Total: $", "").replace(",", ".")), // Reemplaza "," con "."
                            rutaArchivo
                    );
                    JOptionPane.showMessageDialog(BillingWindow.this, "Factura generada exitosamente en " + rutaArchivo, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | DocumentException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BillingWindow.this, "Error al generar la factura", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Acción del botón "Cancelar"
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana
            }
        });
    }

    public BillingWindow() {

    }
}
