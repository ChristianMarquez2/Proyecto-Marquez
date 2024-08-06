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
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Panel de datos del cliente
        JPanel customerPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        customerPanel.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        customerPanel.setBackground(Color.WHITE);

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
        invoiceTable.setRowHeight(30);
        invoiceTable.setFont(new Font("Arial", Font.PLAIN, 14));
        invoiceTable.setSelectionBackground(new Color(204, 229, 255));
        JScrollPane tableScrollPane = new JScrollPane(invoiceTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Detalle de la Factura"));
        add(tableScrollPane, BorderLayout.CENTER);

        // Agregar datos del carrito a la tabla de la factura
        for (Object[] row : cartData) {
            tableModel.addRow(row);
        }

        // Panel de totales
        JPanel totalsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        totalsPanel.setBackground(Color.WHITE);

        totalsPanel.add(new JLabel("Total:"));
        totalLabel = new JLabel(total);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalsPanel.add(totalLabel);

        add(totalsPanel, BorderLayout.SOUTH);

        // Panel de botones
        JButton btnGenerateInvoice = new JButton("Generar Factura");
        JButton btnCancel = new JButton("Cancelar");

        btnGenerateInvoice.setFont(new Font("Arial", Font.BOLD, 14));
        btnGenerateInvoice.setBackground(new Color(0, 123, 255));
        btnGenerateInvoice.setForeground(Color.WHITE);
        btnGenerateInvoice.setFocusPainted(false);

        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setBackground(new Color(255, 69, 58));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
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
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Guardar Factura");
                    int userSelection = fileChooser.showSaveDialog(InvoiceWindow.this);
                    if (userSelection != JFileChooser.APPROVE_OPTION) {
                        return; // Usuario canceló el diálogo
                    }
                    String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();

                    // Generar el PDF de la factura
                    GeneradorPDF.generarFactura(nombreCliente, direccion, telefono, email, nitCi, getCartData(), total, rutaArchivo);
                    JOptionPane.showMessageDialog(InvoiceWindow.this, "Factura generada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(InvoiceWindow.this, "Formato del total incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
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
