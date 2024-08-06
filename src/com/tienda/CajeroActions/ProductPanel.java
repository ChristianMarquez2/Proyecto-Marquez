package com.tienda.CajeroActions;

import com.tienda.Clases.Producto;
import com.tienda.Clases.ProductoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ProductPanel extends JPanel {
    private JComboBox<String> marcaComboBox;
    private JComboBox<String> modeloComboBox;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private ProductoDAO productoDAO;

    public ProductPanel() {
        setLayout(new BorderLayout());

        productoDAO = new ProductoDAO();

        // Panel de filtros
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridLayout(2, 2, 10, 10)); // Añadido espaciado entre componentes

        filterPanel.add(new JLabel("Marca:"));
        marcaComboBox = new JComboBox<>(new String[]{"Apple", "Samsung", "Sony", "Xiaomi", "Huawei"});
        marcaComboBox.addActionListener(new MarcaActionListener());
        filterPanel.add(marcaComboBox);

        filterPanel.add(new JLabel("Modelo:"));
        modeloComboBox = new JComboBox<>();
        modeloComboBox.addActionListener(new ModeloActionListener());
        filterPanel.add(modeloComboBox);

        add(filterPanel, BorderLayout.NORTH);

        // Tabla de productos
        tableModel = new DefaultTableModel(new Object[]{"Nombre", "Precio", "Stock", "Imagen"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evitar la edición de celdas
            }
        };
        productTable = new JTable(tableModel);
        productTable.setFillsViewportHeight(true);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Panel de botones
        JButton btnAddToCart = new JButton("Agregar al Carrito");
        JButton btnViewImages = new JButton("Ver Imágenes");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAddToCart);
        buttonPanel.add(btnViewImages);

        add(buttonPanel, BorderLayout.SOUTH);

        // Acción de los botones
        btnAddToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementar la lógica para agregar productos al carrito
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String productoNombre = (String) tableModel.getValueAt(selectedRow, 0);
                    // Aquí iría la lógica para agregar el producto al carrito
                    JOptionPane.showMessageDialog(ProductPanel.this, "Producto '" + productoNombre + "' agregado al carrito.");
                } else {
                    JOptionPane.showMessageDialog(ProductPanel.this, "Seleccione un producto para agregar al carrito.");
                }
            }
        });

        btnViewImages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementar la lógica para ver imágenes de productos
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String imagenPath = (String) tableModel.getValueAt(selectedRow, 3);
                    if (imagenPath != null && !imagenPath.isEmpty()) {
                        JFrame imageFrame = new JFrame("Imagen del Producto");
                        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        ImageIcon imageIcon = new ImageIcon(imagenPath);
                        JLabel imageLabel = new JLabel(imageIcon);
                        imageFrame.add(new JScrollPane(imageLabel));
                        imageFrame.pack();
                        imageFrame.setLocationRelativeTo(ProductPanel.this);
                        imageFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(ProductPanel.this, "No hay imagen disponible para este producto.");
                    }
                } else {
                    JOptionPane.showMessageDialog(ProductPanel.this, "Seleccione un producto para ver la imagen.");
                }
            }
        });
    }

    private class MarcaActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String marcaSeleccionada = (String) marcaComboBox.getSelectedItem();
            modeloComboBox.removeAllItems();

            // Añadir modelos según la marca seleccionada
            try {
                List<Producto> productos = productoDAO.obtenerProductosPorMarca(obtenerMarcaId(marcaSeleccionada));
                for (Producto producto : productos) {
                    modeloComboBox.addItem(producto.getModelo());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ProductPanel.this, "Error al obtener modelos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ModeloActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String modeloSeleccionado = (String) modeloComboBox.getSelectedItem();
            tableModel.setRowCount(0); // Limpiar la tabla

            // Mostrar productos según el modelo seleccionado
            try {
                List<Producto> productos = productoDAO.obtenerProductosPorModelo(modeloSeleccionado);
                for (Producto producto : productos) {
                    tableModel.addRow(new Object[]{producto.getNombre(), producto.getPrecio(), producto.getStock(), producto.getImagenPath()});
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ProductPanel.this, "Error al obtener productos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int obtenerMarcaId(String marcaNombre) {
        switch (marcaNombre) {
            case "Apple":
                return 1;
            case "Samsung":
                return 2;
            case "Sony":
                return 3;
            case "Xiaomi":
                return 4;
            case "Huawei":
                return 5;
            default:
                return -1; // Manejo por defecto para marcas no encontradas
        }
    }
}
