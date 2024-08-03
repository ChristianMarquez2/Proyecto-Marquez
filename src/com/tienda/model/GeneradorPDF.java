package com.tienda.model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class GeneradorPDF {

    public static void generarNotaVenta(Transacción transacción, String rutaArchivo) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
        document.open();
        document.add(new Paragraph("Nota de Venta"));
        document.add(new Paragraph("ID Transacción: " + transacción.getId()));
        document.add(new Paragraph("Cajero: " + transacción.getCajero().getNombre()));
        document.add(new Paragraph("Productos:"));
        for (Producto producto : transacción.getProductos()) {
            document.add(new Paragraph(" - " + producto.getNombre() + ": " + producto.getPrecio()));
        }
        document.add(new Paragraph("Total: " + transacción.getTotal()));
        document.close();
    }
}
