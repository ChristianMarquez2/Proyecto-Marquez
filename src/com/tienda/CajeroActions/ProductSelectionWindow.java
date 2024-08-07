package com.tienda.CajeroActions;

import com.tienda.Clases.Producto;
import com.tienda.Clases.ProductoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * La clase {@code ProductSelectionWindow} representa una ventana en la interfaz de usuario para la selección de productos.
 * Permite al usuario seleccionar una marca y un modelo de producto para visualizar los productos disponibles y añadirlos al carrito.
 * Utiliza un {@link JComboBox} para la selección de marcas y modelos, una {@link JTable} para mostrar los productos,
 * y un {@link JLabel} para mostrar la imagen del producto seleccionado.
 * Además, proporciona la funcionalidad de añadir productos al carrito de compras.
 */
public class ProductSelectionWindow extends JFrame {

    private JComboBox<String> marcaComboBox;
    private JComboBox<String> modeloComboBox;
    private JTable productoTable;
    private DefaultTableModel tableModel;
    private JLabel imageLabel;
    private ProductoDAO productoDAO;
    private CartWindow cartWindow;

    /**
     * Crea una nueva instancia de {@code ProductSelectionWindow}.
     *
     * @param cartWindow La ventana del carrito de compras donde se añadirán los productos seleccionados.
     */
    public ProductSelectionWindow(CartWindow cartWindow) {
        setTitle("Selección de Productos");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.cartWindow = cartWindow;
        productoDAO = new ProductoDAO();

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de selección
        JPanel seleccionPanel = new JPanel();
        seleccionPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // ComboBox de marcas
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        seleccionPanel.add(new JLabel("Marca:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        marcaComboBox = new JComboBox<>(new String[]{"Samsung", "Apple", "Xiaomi", "Huawei", "Sony"});
        marcaComboBox.addActionListener(e -> actualizarModelos());
        seleccionPanel.add(marcaComboBox, gbc);

        // ComboBox de modelos
        gbc.gridx = 0;
        gbc.gridy = 1;
        seleccionPanel.add(new JLabel("Modelo:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        modeloComboBox = new JComboBox<>();
        modeloComboBox.addActionListener(e -> mostrarProductos());
        seleccionPanel.add(modeloComboBox, gbc);

        mainPanel.add(seleccionPanel, BorderLayout.NORTH);

        // Tabla para mostrar productos
        tableModel = new DefaultTableModel(new Object[]{"Nombre", "Modelo", "Precio", "Stock"}, 0);
        productoTable = new JTable(tableModel);
        productoTable.setFillsViewportHeight(true);
        productoTable.setPreferredScrollableViewportSize(new Dimension(750, 300));
        mainPanel.add(new JScrollPane(productoTable), BorderLayout.CENTER);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(imageLabel, BorderLayout.SOUTH);

        // Botón de añadir al carrito
        JButton addToCartButton = new JButton("Añadir al Carrito");
        addToCartButton.setBackground(new Color(0, 123, 255));
        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.setFocusPainted(false);
        addToCartButton.setPreferredSize(new Dimension(200, 40));
        addToCartButton.addActionListener(e -> addProductToCart());
        mainPanel.add(addToCartButton, BorderLayout.SOUTH);

        add(mainPanel);

        actualizarModelos();
    }

    /**
     * Actualiza el contenido del {@code modeloComboBox} con los modelos disponibles para la marca seleccionada.
     * Consulta la base de datos a través del {@link ProductoDAO} para obtener los modelos correspondientes.
     * En caso de error, muestra un mensaje de advertencia al usuario.
     */
    private void actualizarModelos() {
        String marcaSeleccionada = (String) marcaComboBox.getSelectedItem();
        modeloComboBox.removeAllItems();

        try {
            List<Producto> productos = productoDAO.obtenerProductosPorMarca(obtenerMarcaId(marcaSeleccionada));
            for (Producto producto : productos) {
                modeloComboBox.addItem(producto.getModelo());
            }
            if (modeloComboBox.getItemCount() == 0) {
                modeloComboBox.addItem("No hay modelos disponibles");
            }
            mostrarProductos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener modelos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra los productos disponibles en la {@code productoTable} según el modelo seleccionado.
     * Si no se selecciona un modelo específico, se muestran los productos para la marca seleccionada.
     * En caso de error, muestra un mensaje de advertencia al usuario.
     */
    private void mostrarProductos() {
        String modeloSeleccionado = (String) modeloComboBox.getSelectedItem();
        tableModel.setRowCount(0);
        imageLabel.setIcon(null);

        try {
            List<Producto> productos;
            if (modeloSeleccionado != null && !modeloSeleccionado.equals("No hay modelos disponibles")) {
                productos = productoDAO.obtenerProductosPorModelo(modeloSeleccionado);
            } else {
                productos = productoDAO.obtenerProductosPorMarca(obtenerMarcaId((String) marcaComboBox.getSelectedItem()));
            }

            if (productos.isEmpty()) {
                tableModel.addRow(new Object[]{"No hay productos disponibles", "", "", ""});
            } else {
                for (Producto producto : productos) {
                    tableModel.addRow(new Object[]{
                            producto.getNombre(),
                            producto.getModelo(),
                            "$" + producto.getPrecio(),
                            producto.getStock()
                    });
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener productos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Añade el producto seleccionado en la {@code productoTable} al carrito de compras.
     * El producto se añade con una cantidad de 1 unidad.
     * Si no se selecciona ningún producto, muestra un mensaje de advertencia al usuario.
     */
    private void addProductToCart() {
        int selectedRow = productoTable.getSelectedRow();
        if (selectedRow >= 0) {
            String nombre = (String) tableModel.getValueAt(selectedRow, 0);
            String modelo = (String) tableModel.getValueAt(selectedRow, 1);
            double precio = Double.parseDouble(((String) tableModel.getValueAt(selectedRow, 2)).replace("$", ""));
            cartWindow.addProductToCart(nombre, modelo, precio, 1);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para añadir al carrito.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Devuelve el identificador numérico de la marca según su nombre.
     *
     * @param marcaNombre El nombre de la marca.
     * @return El identificador numérico de la marca.
     */
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
