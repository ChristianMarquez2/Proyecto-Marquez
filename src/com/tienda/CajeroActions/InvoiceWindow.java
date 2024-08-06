package com.tienda.CajeroActions;

import com.tienda.Clases.GeneradorPDF;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvoiceWindow extends JFrame {
    private JTable invoiceTable;
    private DefaultTableModel tableModel;
    private JTextField txtCustomerName, txtAddress, txtPhone, txtEmail, txtNitCi;
    private JLabel totalLabel;

    public InvoiceWindow(Object[][] cartData, String total) {
        setTitle("Generar Factura");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de datos del cliente
        JPanel customerPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        customerPanel.add(new JLabel("Nombre del Cliente:"));
        txtCustomerName = new JTextField();
        customerPanel.add(txtCustomerName);

        customerPanel.add(new JLabel("Dirección:"));
        txtAddress = new JTextField();
        customerPanel.add(txtAddress);

        customerPanel.add(new JLabel("Teléfono:"));
        txtPhone = new JTextField();
        customerPanel.add(txtPhone);

        customerPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        customerPanel.add(txtEmail);

        customerPanel.add(new JLabel("NIT/CI:"));
        txtNitCi = new JTextField();
        customerPanel.add(txtNitCi);

        add(customerPanel, BorderLayout.NORTH);

        // Panel de tabla de factura
        tableModel = new DefaultTableModel(new Object[]{"Producto", "Precio", "Cantidad"}, 0);
        invoiceTable = new JTable(tableModel);
        add(new JScrollPane(invoiceTable), BorderLayout.CENTER);

        // Agregar datos del carrito a la tabla de la factura
        for (Object[] row : cartData) {
            tableModel.addRow(row);
        }

        // Panel de totales
        JPanel totalsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        totalsPanel.add(new JLabel("Total:"));
        totalLabel = new JLabel(total);
        totalsPanel.add(totalLabel);

        add(totalsPanel, BorderLayout.SOUTH);

        // Panel de botones
        JButton btnGenerateInvoice = new JButton("Generar Factura");
        JButton btnCancel = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnGenerateInvoice);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);

        // Acción del botón "Generar Factura"
        btnGenerateInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener los datos del cliente
                    String nombreCliente = txtCustomerName.getText();
                    String direccion = txtAddress.getText();
                    String telefono = txtPhone.getText();
                    String email = txtEmail.getText();
                    String nitCi = txtNitCi.getText();

                    // Obtener el total y eliminar el texto "Total: "
                    String totalText = totalLabel.getText();
                    double total = Double.parseDouble(totalText.replace("Total: $", "").replace(",", ".").trim());

                    // Ruta del archivo PDF
                    String rutaArchivo = "Factura.pdf";

                    // Generar el PDF de la factura
                    GeneradorPDF.generarFactura(nombreCliente, direccion, telefono, email, nitCi, getCartData(), total, rutaArchivo);
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

    private Object[][] getCartData() {
        // Convertir los datos de la tabla a un formato adecuado para generar el PDF
        int rowCount = tableModel.getRowCount();
        int columnCount = tableModel.getColumnCount();
        Object[][] data = new Object[rowCount][columnCount];

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                data[row][col] = tableModel.getValueAt(row, col);
            }
        }
        return data;
    }
}
