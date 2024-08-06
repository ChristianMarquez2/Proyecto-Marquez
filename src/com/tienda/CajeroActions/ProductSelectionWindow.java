package com.tienda.CajeroActions;

import com.tienda.Clases.Producto;
import com.tienda.Clases.ProductoDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ProductSelectionWindow extends JFrame {
    private JComboBox<String> marcaComboBox;
    private JComboBox<String> modeloComboBox;
    private JTextArea productoArea;
    private ProductoDAO productoDAO;
    private CartWindow cartWindow; // Añadido

    public ProductSelectionWindow(CartWindow cartWindow) { // Modificado
        setTitle("Selección de Productos");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.cartWindow = cartWindow; // Inicializa la instancia de CartWindow

        productoDAO = new ProductoDAO();

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel de selección
        JPanel seleccionPanel = new JPanel();
        seleccionPanel.setLayout(new FlowLayout());

        // ComboBox de marcas
        marcaComboBox = new JComboBox<>(new String[]{"Samsung", "Apple", "Xiaomi", "Huawei", "Sony"});
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
        productoArea.setLineWrap(true); // Ajustar texto a la línea
        productoArea.setWrapStyleWord(true); // Ajustar palabras al final de línea
        panel.add(new JScrollPane(productoArea), BorderLayout.CENTER);

        // Botón de añadir al carrito
        JButton addToCartButton = new JButton("Añadir al Carrito");
        addToCartButton.addActionListener(e -> addProductToCart());
        panel.add(addToCartButton, BorderLayout.SOUTH);

        add(panel);

        // Cargar modelos al iniciar
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
            if (modeloComboBox.getItemCount() == 0) {
                modeloComboBox.addItem("No hay modelos disponibles");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener modelos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarProductos() {
        String modeloSeleccionado = (String) modeloComboBox.getSelectedItem();
        productoArea.setText("");

        // Mostrar productos según el modelo seleccionado
        try {
            List<Producto> productos = productoDAO.obtenerProductosPorModelo(modeloSeleccionado);
            if (productos.isEmpty()) {
                productoArea.setText("No hay productos disponibles para el modelo seleccionado.");
            } else {
                for (Producto producto : productos) {
                    productoArea.append(producto.getNombre() + " - $" + producto.getPrecio() + " - Stock: " + producto.getStock() + "\n");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener productos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProductToCart() {
        String modeloSeleccionado = (String) modeloComboBox.getSelectedItem();
        // Suponiendo que seleccionaste un producto y quieres agregarlo al carrito
        try {
            List<Producto> productos = productoDAO.obtenerProductosPorModelo(modeloSeleccionado);
            if (!productos.isEmpty()) {
                Producto producto = productos.get(0); // Selecciona el primer producto como ejemplo
                cartWindow.addProductToCart(producto.getNombre(), producto.getModelo(), producto.getPrecio(), 1); // Agrega al carrito con cantidad 1
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar producto al carrito.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int obtenerMarcaId(String marcaNombre) {
        switch (marcaNombre) {
            case "Samsung":
                return 1;
            case "Apple":
                return 2;
            case "Xiaomi":
                return 3;
            case "Huawei":
                return 4;
            case "Sony":
                return 5;
            default:
                return -1;
        }
    }
}
