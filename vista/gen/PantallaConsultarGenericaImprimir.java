/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.gen;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.FileNotFoundException;
import java.util.HashMap;
import vista.reportes.ReportDesigner;

/**
 *
 * @author Iuga
 */
public class PantallaConsultarGenericaImprimir extends ReportDesigner{
   
    public PantallaConsultarGenericaImprimir() throws FileNotFoundException, DocumentException
    {

    }

    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {   
        
        String[][] datos = (String[][]) params.get("DATOS");
        
        String[] columnas = (String[]) params.get("NOMBRE_COLUMNAS");
        
        Paragraph pnl = new Paragraph(new Phrase("\n"));
        super.doc.add(pnl);        
        
        // TABLA
        PdfPTable tabla = new PdfPTable(columnas.length);
        tabla.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tabla.setWidthPercentage(95);
        
        // NOMBRE DE LAS COLUMNAS
        for (int i = 0; i < columnas.length; i++) {
            String nombreColumna = columnas[i];
            PdfPCell celdaNombre = new PdfPCell(new Paragraph(nombreColumna,ReportDesigner.FUENTE_NORMAL));
            celdaNombre.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            celdaNombre.setBackgroundColor(new BaseColor(219,229,241));
            tabla.addCell(celdaNombre);
        }
        
        
        
        for (int i = 0; i < datos.length; i++) {
            String[] fila = datos[i];
            for (int j = 0; j < fila.length; j++) {
                String celda = fila[j];
                PdfPCell celdaNombre = new PdfPCell(new Paragraph(celda,ReportDesigner.FUENTE_NORMAL));
                    celdaNombre.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    celdaNombre.setBackgroundColor(new BaseColor(255,255,255));
                    tabla.addCell(celdaNombre);
            }
        }
        
        super.doc.add(tabla);

    }
    
    
    
    
    
}
