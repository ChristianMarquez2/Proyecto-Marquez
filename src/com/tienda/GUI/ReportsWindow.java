package com.tienda.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsWindow extends JFrame {
    private JTextArea reportsArea;

    public ReportsWindow() {
        setTitle("Reportes");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel para los reportes
        JPanel reportsPanel = new JPanel(new BorderLayout());

        // Área de texto para mostrar reportes
        reportsArea = new JTextArea();
        reportsArea.setEditable(false);
        reportsArea.setLineWrap(true);
        reportsArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(reportsArea);
        reportsPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton generateReportButton = new JButton("Generar Reporte");
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para generar un reporte
                generarReporte();
            }
        });

        JButton exportButton = new JButton("Exportar Reporte");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para exportar el reporte (puedes implementar la lógica aquí)
                exportarReporte();
            }
        });

        bottomPanel.add(generateReportButton);
        bottomPanel.add(exportButton);

        reportsPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(reportsPanel);
    }

    private void generarReporte() {
        // Aquí deberías reemplazar estos datos simulados con datos reales obtenidos de tu base de datos
        String reportTitle = "Reporte de Ventas y Productos";
        String totalSales = "Ventas Totales: $5000";
        String mostSoldProducts = "Productos Más Vendidos:\n" +
                "1. Smartphone XYZ - 150 unidades\n" +
                "2. Tablet ABC - 100 unidades";
        String transactionSummary = "Cantidad de Transacciones: 75";

        // Crear el contenido del reporte
        String reportContent = String.join("\n\n",
                reportTitle,
                totalSales,
                mostSoldProducts,
                transactionSummary
        );

        // Establecer el contenido del reporte en el área de texto
        reportsArea.setText(reportContent);

        // Para mejorar el formato, puedes usar un StringBuilder para construir el contenido
        StringBuilder sb = new StringBuilder();
        sb.append(reportTitle).append("\n\n");
        sb.append(totalSales).append("\n\n");
        sb.append(mostSoldProducts).append("\n\n");
        sb.append(transactionSummary);

        reportsArea.setText(sb.toString());
    }

    private void exportarReporte() {
        // Implementar la lógica para exportar el reporte, por ejemplo, a un archivo de texto o PDF
        JOptionPane.showMessageDialog(this, "Reporte exportado exitosamente.", "Exportar Reporte", JOptionPane.INFORMATION_MESSAGE);
    }
}
