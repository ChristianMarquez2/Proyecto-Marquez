package com.tienda;

import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.tienda.model.*;

public class Main {

    public static void main(String[] args) {
        try {
            // Inicializar DAO
            ProductoDAO productoDAO = new ProductoDAO();
            TransacciónDAO transacciónDAO = new TransacciónDAO();

            // Crear un nuevo producto
            Producto producto = new Producto(0, "Cargador", 150.0, 20, "cargador.jpg");
            productoDAO.agregarProducto(producto);

            // Obtener productos y mostrar
            List<Producto> productos = productoDAO.obtenerProductos();
            for (Producto p : productos) {
                System.out.println(p.getNombre() + " - $" + p.getPrecio());
            }

            // Crear una transacción de ejemplo
            Cajero cajero = new Cajero(1, "Juan");
            Transacción transacción = new Transacción(0, productos, 150.0, cajero);
            transacciónDAO.agregarTransacción(transacción);

            // Generar PDF
            GeneradorPDF.generarNotaVenta(transacción, "nota_venta.pdf");
            System.out.println("PDF generado correctamente.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            // Manejo específico para DocumentException
            e.printStackTrace();
        }
    }
}
