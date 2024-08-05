package com.tienda.Clases;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class GeneradorPDF {

    // Método estático para generar una factura en formato PDF
    public static void generarFactura(String nombreCliente, String direccion, String telefono, String email, String nitCi, Object[][] productos, double total, String rutaArchivo) throws DocumentException, IOException {
        // Crear un nuevo documento PDF
        Document document = new Document();

        try {
            // Obtener una instancia de PdfWriter para el documento
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));

            // Abrir el documento para la escritura
            document.open();

            // Agregar título
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font regularFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            document.add(new Paragraph("Factura", titleFont));
            document.add(new Paragraph(" "));

            // Agregar datos del cliente
            document.add(new Paragraph("Nombre del Cliente: " + nombreCliente, regularFont));
            document.add(new Paragraph("Dirección: " + direccion, regularFont));
            document.add(new Paragraph("Teléfono: " + telefono, regularFont));
            document.add(new Paragraph("Email: " + email, regularFont));
            document.add(new Paragraph("NIT/CI: " + nitCi, regularFont));
            document.add(new Paragraph(" "));

            // Agregar tabla de productos
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[]{3f, 1.5f, 1f});

            // Encabezados de la tabla
            table.addCell(new PdfPCell(new Phrase("Producto", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Precio", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Cantidad", headerFont)));

            // Agregar filas de la tabla
            for (Object[] producto : productos) {
                table.addCell((String) producto[0]); // Producto
                table.addCell(String.format("$%.2f", producto[1])); // Precio
                table.addCell(String.valueOf(producto[2])); // Cantidad
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total: $" + String.format("%.2f", total), regularFont));

        } finally {
            // Asegurarse de cerrar el documento incluso si ocurre una excepción
            if (document.isOpen()) {
                document.close();
            }
        }
    }
}
