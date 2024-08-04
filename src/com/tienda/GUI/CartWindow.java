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

        // Panel para los elementos del carrito
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout());

        // Tabla para mostrar los productos en el carrito
        String[] columnNames = {"Producto", "Cantidad", "Precio"};
        Object[][] data = {}; // Puedes cargar datos reales aquí
        JTable cartTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        cartPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel para el total y botón de pago
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

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

        cartPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(cartPanel);
    }
}
