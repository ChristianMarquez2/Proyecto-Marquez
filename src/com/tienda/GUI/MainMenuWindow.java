package com.tienda.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuWindow extends JFrame {
    private String userRole;

    public MainMenuWindow(String userRole) {
        this.userRole = userRole;
        setTitle("Menú Principal");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel de navegación
        JPanel navigationPanel = new JPanel(new GridLayout(5, 1));
        JButton productButton = new JButton("Productos");
        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductSelectionWindow().setVisible(true);
            }
        });
        navigationPanel.add(productButton);

        // Otros botones del menú (por ejemplo, facturación, etc.)
        JButton billingButton = new JButton("Facturación");
        billingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BillingWindow().setVisible(true);
            }
        });
        navigationPanel.add(billingButton);

        add(navigationPanel, BorderLayout.WEST);

        // Panel de información (vacío por ahora, se puede llenar según la selección)
        JPanel infoPanel = new JPanel();
        add(infoPanel, BorderLayout.CENTER);
    }
}
