package com.tienda.GUI;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;




public class InvoiceWindow extends JFrame {
    private JTable invoiceTable;
    private DefaultTableModel tableModel;
    private JTextField txtTotal;

    public InvoiceWindow(Object[][] cartData, String total) {
        setTitle("Generar Factura");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout principal
        setLayout(new BorderLayout());

        // Panel de datos del cliente
        JPanel customerPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        customerPanel.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        customerPanel.add(new JLabel("Nombre del Cliente:"));
        JTextField txtCustomerName = new JTextField();
        customerPanel.add(txtCustomerName);

        customerPanel.add(new JLabel("Dirección:"));
        JTextField txtAddress = new JTextField();
        customerPanel.add(txtAddress);

        customerPanel.add(new JLabel("Teléfono:"));
        JTextField txtPhone = new JTextField();
        customerPanel.add(txtPhone);

        add(customerPanel, BorderLayout.NORTH);

        // Panel de tabla de factura
        tableModel = new DefaultTableModel(new Object[]{"Producto", "Precio Unitario", "Cantidad", "Total"}, 0);
        invoiceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(invoiceTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de totales
        JPanel totalsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        totalsPanel.setBorder(BorderFactory.createTitledBorder("Totales"));
        totalsPanel.add(new JLabel("Total:"));
        txtTotal = new JTextField();
        txtTotal.setEditable(false);
        txtTotal.setText(total); // Mostrar el total recibido
        totalsPanel.add(txtTotal);

        // Agregar panel de totales a la parte inferior
        add(totalsPanel, BorderLayout.SOUTH);

        // Panel de botones
        JButton btnGenerateInvoice = new JButton("Generar Factura");
        JButton btnCancel = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnGenerateInvoice);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);

        // Rellenar la tabla con los datos del carrito
        populateTable(cartData);

        // Acción del botón "Generar Factura"
        btnGenerateInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Generar el PDF de la factura
                    generatePDF(txtCustomerName.getText(), txtAddress.getText(), txtPhone.getText(), txtTotal.getText());
                    JOptionPane.showMessageDialog(InvoiceWindow.this, "Factura generada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(InvoiceWindow.this, "Error al generar la factura", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Acción del botón "Cancelar"
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
            }
        });
    }

    private void generatePDF(String customerName, String address, String phone, String total) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Factura.pdf"));
        document.open();

        // Agregar título
        document.add(new Paragraph("Factura", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD)));
        document.add(new Paragraph(" "));

        // Agregar datos del cliente
        document.add(new Paragraph("Nombre del Cliente: " + customerName));
        document.add(new Paragraph("Dirección: " + address));
        document.add(new Paragraph("Teléfono: " + phone));
        document.add(new Paragraph(" "));

        // Agregar tabla de productos
        PdfPTable table = new PdfPTable(4);
        table.addCell("Producto");
        table.addCell("Precio Unitario");
        table.addCell("Cantidad");
        table.addCell("Total");

        // Agregar filas de la tabla
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            table.addCell((String) tableModel.getValueAt(i, 0)); // Producto
            table.addCell(String.valueOf(tableModel.getValueAt(i, 1))); // Precio Unitario
            table.addCell(String.valueOf(tableModel.getValueAt(i, 2))); // Cantidad
            table.addCell(String.valueOf(tableModel.getValueAt(i, 3))); // Total
        }

        document.add(table);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Total: " + total));

        document.close();
    }

    // Método para actualizar la tabla con los datos del carrito
    private void populateTable(Object[][] cartData) {
        for (Object[] row : cartData) {
            tableModel.addRow(row);
        }
    }
}
