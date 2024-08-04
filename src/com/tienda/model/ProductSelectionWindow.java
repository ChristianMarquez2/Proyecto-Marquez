package com.tienda.model;

import com.tienda.model.Producto;
import com.tienda.model.ProductoDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ProductSelectionWindow extends JFrame {
    private JComboBox<String> marcaComboBox;
    private JComboBox<String> modeloComboBox;
    private JTextArea productoArea;
    private ProductoDAO productoDAO;

    public ProductSelectionWindow() {
        setTitle("Selección de Productos");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        productoDAO = new ProductoDAO();

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel de selección
        JPanel seleccionPanel = new JPanel();
        seleccionPanel.setLayout(new FlowLayout());

        // ComboBox de marcas
        marcaComboBox = new JComboBox<>(new String[]{"Apple", "Samsung", "Sony", "Xiaomi", "Huawei"});
        marcaComboBox.addActionListener(e -> actualizarModelos());
        seleccionPanel.add(new JLabel("Marca:"));
        seleccionPanel.add(marcaComboBox);

        // ComboBox de modelos
        modeloComboBox = new JComboBox<>();
        modeloComboBox.addActionListener(e -> mostrarProductos());
        seleccionPanel.add(new JLabel("Modelo:"));
        seleccionPanel.add(modeloComboBox);

        panel.add(seleccionPanel, BorderLayout.NORTH);

        // Área de texto para mostrar productos
        productoArea = new JTextArea();
        productoArea.setEditable(false);
        panel.add(new JScrollPane(productoArea), BorderLayout.CENTER);

        add(panel);

        actualizarModelos();
    }

    private void actualizarModelos() {
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

    private void mostrarProductos() {
        String modeloSeleccionado = (String) modeloComboBox.getSelectedItem();
        productoArea.setText("");

        // Mostrar productos según el modelo seleccionado
        try {
            List<Producto> productos = productoDAO.obtenerProductosPorModelo(modeloSeleccionado);
            for (Producto producto : productos) {
                productoArea.append(producto.getNombre() + " - $" + producto.getPrecio() + "\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
