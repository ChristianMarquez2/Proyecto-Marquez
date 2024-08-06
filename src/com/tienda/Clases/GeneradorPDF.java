package com.tienda.Clases;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GeneradorPDF {

    public static void generarFactura(String nombreCliente, String direccion, String telefono, String email, String nitCi, Object[][] productos, double total, String rutaArchivo) throws IOException, DocumentException{
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font regularFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            document.add(new Paragraph("Factura", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Nombre del Cliente: " + nombreCliente, regularFont));
            document.add(new Paragraph("Dirección: " + direccion, regularFont));
            document.add(new Paragraph("Teléfono: " + telefono, regularFont));
            document.add(new Paragraph("Email: " + email, regularFont));
            document.add(new Paragraph("NIT/CI: " + nitCi, regularFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[]{3f, 1.5f, 1f});

            table.addCell(new PdfPCell(new Phrase("Producto", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Precio", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Cantidad", headerFont)));

            for (Object[] producto : productos) {
                table.addCell((String) producto[0]);
                table.addCell(String.format("$%.2f", producto[1]));
                table.addCell(String.valueOf(producto[2]));
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total: $" + String.format("%.2f", total), regularFont));

            // Guardar la factura en la base de datos
            //guardarFactura(nombreCliente, direccion, telefono, email, nitCi, productos, total, usuario);

        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    private static void guardarFactura(String nombreCliente, String direccion, String telefono, String email, String nitCi, Object[][] productos, double total, String usuario) throws SQLException {
        String productosString = "";
        for (Object[] producto : productos) {
            productosString += String.format("Producto: %s, Precio: %.2f, Cantidad: %d\n", producto[0], producto[1], producto[2]);
        }

        String sql = "INSERT INTO facturas (nombre_cliente, direccion, telefono, email, nit_ci, productos, total, usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = BaseDeDatos.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreCliente);
            stmt.setString(2, direccion);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setString(5, nitCi);
            stmt.setString(6, productosString);
            stmt.setDouble(7, total);
            stmt.setString(8, usuario);
            stmt.executeUpdate();
        }
    }
}
