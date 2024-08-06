package com.tienda.CajeroActions;

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
    private ReportsWindow reportsWindow;

    public BillingWindow(Object[][] cartData, String total, ReportsWindow reportsWindow) {
        this.reportsWindow = reportsWindow;

        setTitle("Facturación");
        setSize(800, 600); // Aumentado para mayor espacio
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel de datos del cliente
        JPanel clientePanel = new JPanel(new GridLayout(6, 2, 5, 5));
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

        for (Object[] row : cartData) {
            productosTableModel.addRow(row);
        }

        mainPanel.add(productosPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton generarFacturaButton = new JButton("Generar Factura");
        botonesPanel.add(generarFacturaButton);

        JButton cancelarButton = new JButton("Cancelar");
        botonesPanel.add(cancelarButton);

        mainPanel.add(botonesPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Acciones de los botones
        generarFacturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String rutaArchivo = "Factura.pdf";
                    GeneradorPDF.generarFactura(
                            nombreClienteField.getText(),
                            direccionField.getText(),
                            telefonoField.getText(),
                            emailField.getText(),
                            nitCiField.getText(),
                            cartData,
                            Double.parseDouble(total.replace("Total: $", "").replace(",", ".")),
                            rutaArchivo
                    );

                    JOptionPane.showMessageDialog(BillingWindow.this, "Factura generada exitosamente en " + rutaArchivo, "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Enviar datos de la factura a ReportsWindow
                    if (reportsWindow != null) {
                        reportsWindow.setDatosFactura(
                                nombreClienteField.getText(),
                                direccionField.getText(),
                                telefonoField.getText(),
                                emailField.getText(),
                                nitCiField.getText(),
                                getProductosAsString(cartData),
                                Double.parseDouble(total.replace("Total: $", "").replace(",", ".")),
                                "Usuario Actual" // Aquí deberías obtener el usuario actual del sistema
                        );
                    }
                } catch (IOException | DocumentException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BillingWindow.this, "Error al generar la factura", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana de facturación
            }
        });
    }

    // Constructor vacío necesario para algunos usos
    public BillingWindow() {
    }

    // Convierte los datos del carrito a una cadena legible
    private String getProductosAsString(Object[][] cartData) {
        StringBuilder sb = new StringBuilder();
        for (Object[] row : cartData) {
            sb.append(row[0]).append(" - ").append(row[1]).append(" - Cantidad: ").append(row[2]).append("\n");
        }
        return sb.toString();
    }
}
