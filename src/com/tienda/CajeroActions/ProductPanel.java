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

/**
 * Panel de interfaz gráfica para la selección y visualización de productos.
 * Permite filtrar productos por marca y modelo, y muestra los detalles del producto en una tabla.
 * Incluye opciones para agregar productos al carrito y ver imágenes de los productos.
 */
public class ProductPanel extends JPanel {
    private JComboBox<String> marcaComboBox;
    private JComboBox<String> modeloComboBox;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private ProductoDAO productoDAO;

    /**
     * Constructor que inicializa el panel y configura los componentes.
     * Configura el diseño, agrega los filtros de marca y modelo, y configura la tabla y los botones.
     */
    public ProductPanel() {
        setLayout(new BorderLayout());

        productoDAO = new ProductoDAO();

        // Panel de filtros
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridLayout(2, 2, 10, 10));

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
                return false;
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
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String productoNombre = (String) tableModel.getValueAt(selectedRow, 0);
                    JOptionPane.showMessageDialog(ProductPanel.this, "Producto '" + productoNombre + "' agregado al carrito.");
                } else {
                    JOptionPane.showMessageDialog(ProductPanel.this, "Seleccione un producto para agregar al carrito.");
                }
            }
        });

        btnViewImages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

    /**
     * Listener para el cambio de selección en el JComboBox de marcas.
     * Actualiza el JComboBox de modelos con los modelos disponibles para la marca seleccionada.
     */
    private class MarcaActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String marcaSeleccionada = (String) marcaComboBox.getSelectedItem();
            modeloComboBox.removeAllItems();

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

    /**
     * Listener para el cambio de selección en el JComboBox de modelos.
     * Actualiza la tabla de productos con los productos disponibles para el modelo seleccionado.
     */
    private class ModeloActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String modeloSeleccionado = (String) modeloComboBox.getSelectedItem();
            tableModel.setRowCount(0); // Limpiar la tabla

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

    /**
     * Convierte el nombre de la marca en un ID para consultar la base de datos.
     *
     * @param marcaNombre Nombre de la marca.
     * @return ID de la marca correspondiente, o -1 si la marca no es reconocida.
     */
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
