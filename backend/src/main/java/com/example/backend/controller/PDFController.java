package com.example.backend.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "PDF", description = "Endpoints para la generación de PDFs")
@RestController
@RequestMapping("/pdf")
public class PDFController {

    @Operation(summary = "Generar PDF de Evaluación", description = "Genera y descarga un PDF con los datos de evaluación enviados")
    @PostMapping("/generar")
    public void generarPdf(@RequestBody EvaluacionRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Evaluacion.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Título del PDF
        document.add(new Paragraph("Reporte de Evaluación")
                .setBold()
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph(" "));

        // Datos del Participante y la prueba
        document.add(new Paragraph("Participante: " + request.getParticipante() + " " + request.getApellidos()));
        document.add(new Paragraph("Prueba: " + request.getPrueba()));
        document.add(new Paragraph("Nota Final: " + request.getNotaFinal()));

        // Espaciado
        document.add(new Paragraph(" "));

        // Tabla con los ítems, peso y valoración
        float[] columnWidths = {200F, 100F, 100F}; // Definimos el ancho de las columnas: Descripción, Peso, Valoración
        Table table = new Table(columnWidths);
        table.addCell("Descripción");
        table.addCell("Peso");
        table.addCell("Valoración");

        // Añadimos los ítems
        for (ItemRequest item : request.getItems()) {
            table.addCell(item.getDescripcion());
            table.addCell(item.getPeso());
            table.addCell(item.getValoracion());
        }

        // Agregamos la tabla al documento
        document.add(table);

        // Cerramos el documento
        document.close();
    }

    // Clase interna para recibir el JSON correctamente
    public static class EvaluacionRequest {
        private String participante;
        private String apellidos;
        private String prueba;
        private String notaFinal;
        private List<ItemRequest> items;

        // Getters y setters
        public String getParticipante() { return participante; }
        public void setParticipante(String participante) { this.participante = participante; }

        public String getApellidos() { return apellidos; }
        public void setApellidos(String apellidos) { this.apellidos = apellidos; }

        public String getPrueba() { return prueba; }
        public void setPrueba(String prueba) { this.prueba = prueba; }

        public String getNotaFinal() { return notaFinal; }
        public void setNotaFinal(String notaFinal) { this.notaFinal = notaFinal; }

        public List<ItemRequest> getItems() { return items; }
        public void setItems(List<ItemRequest> items) { this.items = items; }
    }

    // Clase para representar un ítem en la evaluación
    public static class ItemRequest {
        private String descripcion;
        private String peso;
        private String valoracion;

        // Getters y setters
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

        public String getPeso() { return peso; }
        public void setPeso(String peso) { this.peso = peso; }

        public String getValoracion() { return valoracion; }
        public void setValoracion(String valoracion) { this.valoracion = valoracion; }
    }
}
