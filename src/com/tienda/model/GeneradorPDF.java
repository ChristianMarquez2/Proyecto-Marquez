package com.tienda.model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class GeneradorPDF {

    // Método estático para generar una nota de venta en formato PDF
    public static void generarNotaVenta(Transacción transacción, String rutaArchivo) throws DocumentException, IOException {
        // Crear un nuevo documento PDF
        Document document = new Document();

        try {
            // Obtener una instancia de PdfWriter para el documento
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));

            // Abrir el documento para la escritura
            document.open();

            // Agregar contenido al documento
            document.add(new Paragraph("Nota de Venta"));
            document.add(new Paragraph("ID Transacción: " + transacción.getId()));
            document.add(new Paragraph("Cajero: " + transacción.getCajero().getNombre()));
            document.add(new Paragraph("Productos:"));

            // Agregar cada producto a la nota de venta
            for (Producto producto : transacción.getProductos()) {
                document.add(new Paragraph(" - " + producto.getNombre() + ": $" + producto.getPrecio()));
            }

            // Agregar el total de la transacción
            document.add(new Paragraph("Total: $" + transacción.getTotal()));
        } finally {
            // Asegurarse de cerrar el documento incluso si ocurre una excepción
            document.close();
        }
    }
}
