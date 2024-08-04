package com.tienda.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvoiceWindow extends JFrame {
    private JTable invoiceTable;
    private DefaultTableModel tableModel;

    public InvoiceWindow() {
        setTitle("Generar Factura");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new GridLayout(3, 2));

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

        tableModel = new DefaultTableModel(new Object[]{"Producto", "Precio", "Cantidad"}, 0);
        invoiceTable = new JTable(tableModel);

        add(new JScrollPane(invoiceTable), BorderLayout.CENTER);

        JPanel totalsPanel = new JPanel();
        totalsPanel.setLayout(new GridLayout(2, 2));

        totalsPanel.add(new JLabel("Total:"));
        JTextField txtTotal = new JTextField();
        totalsPanel.add(txtTotal);

        totalsPanel.add(new JLabel("IVA:"));
        JTextField txtVAT = new JTextField();
        totalsPanel.add(txtVAT);

        add(totalsPanel, BorderLayout.SOUTH);

        JButton btnGenerateInvoice = new JButton("Generar Factura");
        JButton btnCancel = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnGenerateInvoice);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);

        // Aquí agregarías la lógica para manejar la generación de la factura
    }
}
