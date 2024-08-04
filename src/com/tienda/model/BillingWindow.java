package com.tienda.model;

import javax.swing.*;
import java.awt.*;

public class BillingWindow extends JFrame {
    public BillingWindow() {
        setTitle("Facturación");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Panel de entrada de datos del cliente
        JPanel clientePanel = new JPanel(new GridLayout(5, 2));
        clientePanel.add(new JLabel("Nombre del Cliente:"));
        JTextField nombreClienteField = new JTextField();
        clientePanel.add(nombreClienteField);

        clientePanel.add(new JLabel("Dirección:"));
        JTextField direccionField = new JTextField();
        clientePanel.add(direccionField);

        clientePanel.add(new JLabel("Teléfono:"));
        JTextField telefonoField = new JTextField();
        clientePanel.add(telefonoField);

        clientePanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        clientePanel.add(emailField);

        clientePanel.add(new JLabel("NIT/CI:"));
        JTextField nitCiField = new JTextField();
        clientePanel.add(nitCiField);

        panel.add(clientePanel, BorderLayout.NORTH);

        // Panel de productos y precios
        JPanel productosPanel = new JPanel(new BorderLayout());
        JTextArea productosArea = new JTextArea();
        productosArea.setEditable(false);
        productosPanel.add(new JScrollPane(productosArea), BorderLayout.CENTER);

        panel.add(productosPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        JButton generarFacturaButton = new JButton("Generar Factura");
        botonesPanel.add(generarFacturaButton);

        panel.add(botonesPanel, BorderLayout.SOUTH);

        add(panel);

        // Acción del botón "Generar Factura"
        generarFacturaButton.addActionListener(e -> {
            // Aquí puedes implementar la lógica para generar la factura
            JOptionPane.showMessageDialog(this, "Factura generada exitosamente", "Facturación", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
