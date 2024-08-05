package com.tienda.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartWindow extends JFrame {
    public CartWindow() {
        setTitle("Carrito");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal del carrito
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel para la tabla del carrito
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Producto", "Cantidad", "Precio"};
        Object[][] data = {}; // Puedes cargar datos reales aquí
        JTable cartTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Panel para el total y botón de pago
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel totalLabel = new JLabel("Total: $0.00");
        bottomPanel.add(totalLabel);

        JButton checkoutButton = new JButton("Pagar");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para proceder al pago
                JOptionPane.showMessageDialog(null, "Procediendo al pago...");
            }
        });
        bottomPanel.add(checkoutButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
