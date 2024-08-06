package com.tienda.CajeroActions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartWindow extends JFrame {
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;

    public CartWindow() {
        setTitle("Carrito");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal del carrito
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel para la tabla del carrito
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Producto", "Modelo", "Cantidad", "Precio", "Total", "Eliminar"};
        tableModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Panel para el total y botón de pago
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total: $0.00");
        bottomPanel.add(totalLabel);

        JButton checkoutButton = new JButton("Pagar");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para proceder al pago
                openInvoiceWindow();
            }
        });
        bottomPanel.add(checkoutButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Configurar el botón de eliminar y el editor de cantidad
        setupDeleteButton();
        setupQuantityEditor();

        // Actualizar el total al cambiar los datos de la tabla
        tableModel.addTableModelListener(e -> updateTotal());
    }

    // Método para añadir productos al carrito
    public void addProductToCart(String nombre, String modelo, double precio, int cantidad) {
        double total = precio * cantidad;
        tableModel.addRow(new Object[]{nombre, modelo, cantidad, precio, total, "Eliminar"});
        updateTotal();
    }

    // Método para permitir cambiar la cantidad de productos
    private void setupQuantityEditor() {
        TableColumn quantityColumn = cartTable.getColumnModel().getColumn(2);
        quantityColumn.setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public Object getCellEditorValue() {
                int row = cartTable.getEditingRow();
                int newQuantity = Integer.parseInt((String) super.getCellEditorValue());
                double unitPrice = (Double) tableModel.getValueAt(row, 3);
                tableModel.setValueAt(newQuantity, row, 2);
                tableModel.setValueAt(newQuantity * unitPrice, row, 4);
                return newQuantity;
            }
        });
    }

    // Método para actualizar el total
    private void updateTotal() {
        double total = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            total += (Double) tableModel.getValueAt(i, 4);
        }
        totalLabel.setText("Total: $" + String.format("%.2f", total));
    }

    // Método para configurar el botón de eliminar
    private void setupDeleteButton() {
        cartTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
    }

    // Método para abrir la ventana de facturación
    private void openInvoiceWindow() {
        InvoiceWindow invoiceWindow = new InvoiceWindow(getCartData(), totalLabel.getText());
        invoiceWindow.setVisible(true);
    }

    // Método para obtener los datos del carrito
    private Object[][] getCartData() {
        int rowCount = tableModel.getRowCount();
        Object[][] data = new Object[rowCount][3];
        for (int i = 0; i < rowCount; i++) {
            data[i][0] = tableModel.getValueAt(i, 0); // Producto
            data[i][1] = tableModel.getValueAt(i, 3); // Precio
            data[i][2] = tableModel.getValueAt(i, 2); // Cantidad
        }
        return data;
    }

    // Clase para el botón de eliminar
    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Eliminar");
            addActionListener(e -> {
                // Obtener la fila que fue clickeada
                int row = cartTable.rowAtPoint(SwingUtilities.convertPoint(ButtonRenderer.this,
                        new Point(0, 0), cartTable));
                if (row >= 0) {
                    tableModel.removeRow(row);
                    updateTotal();
                }
            });
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CartWindow cartWindow = new CartWindow();
            cartWindow.setVisible(true);
        });
    }
}
