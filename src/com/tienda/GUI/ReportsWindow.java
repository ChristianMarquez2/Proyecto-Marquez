package com.tienda.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsWindow extends JFrame {
    public ReportsWindow() {
        setTitle("Reportes");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel para los reportes
        JPanel reportsPanel = new JPanel();
        reportsPanel.setLayout(new BorderLayout());

        // Área de texto para mostrar reportes
        JTextArea reportsArea = new JTextArea();
        reportsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportsArea);
        reportsPanel.add(scrollPane, BorderLayout.CENTER);

        // Botón para generar un reporte
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton generateReportButton = new JButton("Generar Reporte");
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para generar un reporte
                reportsArea.setText("Reporte generado...\nAquí van los datos del reporte.");
            }
        });
        bottomPanel.add(generateReportButton);

        reportsPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(reportsPanel);
    }
}
