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
        setTitle("Carrito de Compras");
        setSize(900, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel principal del carrito
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel para la tabla del carrito
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Productos en el Carrito"));

        String[] columnNames = {"Producto", "Modelo", "Cantidad", "Precio", "Total", "Eliminar"};
        tableModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(tableModel);
        cartTable.setRowHeight(30);
        cartTable.setFont(new Font("Arial", Font.PLAIN, 14));
        cartTable.setSelectionBackground(new Color(204, 229, 255));

        JScrollPane scrollPane = new JScrollPane(cartTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Panel para el total y botón de pago
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        JButton checkoutButton = new JButton("Pagar");
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        checkoutButton.setBackground(new Color(0, 123, 255));
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setFocusPainted(false);
        checkoutButton.addActionListener(e -> openInvoiceWindow());
        bottomPanel.add(checkoutButton, BorderLayout.EAST);

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
        cartTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));
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
            setFont(new Font("Arial", Font.PLAIN, 14));
            setBackground(new Color(255, 69, 58));
            setForeground(Color.WHITE);
            setFocusPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Clase para el editor del botón de eliminar
    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.setBackground(new Color(255, 69, 58));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setBackground(table.getSelectionForeground());
            }
            label = (value == null) ? "Eliminar" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int row = cartTable.convertRowIndexToModel(cartTable.getEditingRow());
                if (row >= 0 && row < tableModel.getRowCount()) {
                    tableModel.removeRow(row);
                    updateTotal();
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }


}
