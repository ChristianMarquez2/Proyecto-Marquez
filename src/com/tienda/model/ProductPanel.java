package com.tienda.model;

import com.tienda.model.Producto;
import com.tienda.model.ProductoDAO;

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

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridLayout(2, 2));

        filterPanel.add(new JLabel("Marca:"));
        marcaComboBox = new JComboBox<>(new String[]{"Apple", "Samsung", "Sony", "Xiaomi", "Huawei"});
        marcaComboBox.addActionListener(new MarcaActionListener());
        filterPanel.add(marcaComboBox);

        filterPanel.add(new JLabel("Modelo:"));
        modeloComboBox = new JComboBox<>();
        modeloComboBox.addActionListener(new ModeloActionListener());
        filterPanel.add(modeloComboBox);

        add(filterPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Nombre", "Precio", "Stock", "Imagen"}, 0);
        productTable = new JTable(tableModel);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        JButton btnAddToCart = new JButton("Agregar al Carrito");
        JButton btnViewImages = new JButton("Ver Imágenes");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAddToCart);
        buttonPanel.add(btnViewImages);

        add(buttonPanel, BorderLayout.SOUTH);
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
            }
        }
    }

    private class ModeloActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String modeloSeleccionado = (String) modeloComboBox.getSelectedItem();
            tableModel.setRowCount(0); // Clear the table

            // Mostrar productos según el modelo seleccionado
            try {
                List<Producto> productos = productoDAO.obtenerProductosPorModelo(modeloSeleccionado);
                for (Producto producto : productos) {
                    tableModel.addRow(new Object[]{producto.getNombre(), producto.getPrecio(), producto.getStock(), producto.getImagenPath()});
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
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
                return -1;
        }
    }
}
