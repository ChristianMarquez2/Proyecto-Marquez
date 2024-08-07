package com.tienda.CajeroActions;

import com.tienda.Clases.GeneradorPDF;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InvoiceWindow extends JFrame {
    private JTable invoiceTable;
    private DefaultTableModel tableModel;
    private JTextField txtCustomerName, txtAddress, txtPhone, txtEmail, txtNitCi;
    private JLabel totalLabel;
    private AtomicInteger serieNumber;

    public InvoiceWindow(Object[][] cartData, String total) {
        setTitle("Generar Factura");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Inicializar número de serie
        serieNumber = new AtomicInteger(loadSerieNumber());

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

                    // Generar número de serie
                    int serie = serieNumber.getAndIncrement();
                    saveSerieNumber(serieNumber.get());

                    // Nombre del archivo PDF
                    String nombreArchivo = String.format("%s No %04d Comprobante", nombreCliente, serie);

                    // Ruta del archivo PDF
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Guardar Factura");
                    fileChooser.setSelectedFile(new File(nombreArchivo + ".pdf"));
                    int userSelection = fileChooser.showSaveDialog(InvoiceWindow.this);
                    if (userSelection != JFileChooser.APPROVE_OPTION) {
                        return; // Usuario canceló el diálogo
                    }
                    String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();

                    // Generar el PDF de la factura
                    GeneradorPDF.generarFactura(nombreCliente, direccion, telefono, email, nitCi, getCartData(), total, rutaArchivo);
                    JOptionPane.showMessageDialog(InvoiceWindow.this, "Factura generada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Guardar los datos de la factura en la base de datos y actualizar stock
                    guardarFacturaEnBaseDeDatos(nombreCliente, direccion, telefono, email, nitCi, getCartData(), total);

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

    private int loadSerieNumber() {
        try (BufferedReader reader = new BufferedReader(new FileReader("serie.txt"))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            return 1; // Valor por defecto si no se puede leer el archivo
        }
    }

    private void saveSerieNumber(int number) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("serie.txt"))) {
            writer.write(String.valueOf(number));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el número de serie.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarFacturaEnBaseDeDatos(String nombreCliente, String direccion, String telefono, String email, String nitCi, Object[][] productos, double total) {
        String url = "jdbc:mysql://localhost:3306/tienda";
        String usuario = "root";
        String contraseña = "zznk";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
            String productosString = convertirProductosAString(productos);
            String sql = "INSERT INTO facturas (nombre_cliente, direccion, telefono, email, nit_ci, productos, total, usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, nombreCliente);
                statement.setString(2, direccion);
                statement.setString(3, telefono);
                statement.setString(4, email);
                statement.setString(5, nitCi);
                statement.setString(6, productosString);
                statement.setDouble(7, total);
                statement.setString(8, "usuarioActual"); // Reemplaza con el nombre del usuario actual

                statement.executeUpdate();

                // Obtener la clave primaria generada para la factura
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long facturaId = generatedKeys.getLong(1);
                    // Actualizar stock de productos
                    actualizarStock(connection, productos);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la factura en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String convertirProductosAString(Object[][] productos) {
        StringBuilder sb = new StringBuilder();
        for (Object[] producto : productos) {
            sb.append(producto[0]).append(" - ").append(producto[1]).append(" - ").append(producto[2]).append("\n");
        }
        return sb.toString();
    }

    private void actualizarStock(Connection connection, Object[][] productos) throws SQLException {
        String sql = "UPDATE productos SET stock = stock - ? WHERE nombre = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Object[] producto : productos) {
                String nombreProducto = (String) producto[0]; // Suponiendo que nombreProducto es un String
                int cantidad = (Integer) producto[2]; // Suponiendo que cantidad es un Integer

                statement.setInt(1, cantidad);
                statement.setString(2, nombreProducto);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

}
