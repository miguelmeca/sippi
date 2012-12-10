/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import modelo.*;
import util.FechaUtil;
import util.RecursosUtil;
import vista.reportes.ReportDesigner;

/**
 *
 * @author Administrador
 */
public class InformeDeEjecucionTareasARealizar extends InformeDeEjecucion{

    
    public InformeDeEjecucionTareasARealizar(Planificacion planificacion) {
        super(planificacion);
        
    }
  
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        makeCabecera(params);
        Paragraph pnl = new Paragraph(new Phrase("\n"));
        super.doc.add(pnl);
        
        List tareas = this.planificacion.getTareas();
        Iterator<TareaPlanificacion> itTareas = tareas.iterator();
        int orden = 0;
        
        PdfPTable tabla = new PdfPTable(2);
        PdfPCell celdaNombre = new PdfPCell(new Paragraph("Nombre",ReportDesigner.FUENTE_NORMAL_B));
        celdaNombre.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        celdaNombre.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaNombre);

        PdfPCell celdaEsfuerzo = new PdfPCell(new Paragraph("Esfuerzo",ReportDesigner.FUENTE_NORMAL_B));
        celdaEsfuerzo.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        celdaEsfuerzo.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaEsfuerzo);
        
        tabla.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tabla.setWidthPercentage(95);
        
        double total = 0;
        while(itTareas.hasNext())
        {    
            TareaPlanificacion tarea = itTareas.next();
            total += tarea.obtenerTotalDeHoras();
            String tabulacion = "> ";
            this.pintarTarea(tarea, orden, "Tarea", tabla, tabulacion);
            orden++;
        }
        
        celdaNombre = new PdfPCell(new Paragraph("Total",ReportDesigner.FUENTE_NORMAL_BK));
        celdaNombre.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        celdaNombre.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaNombre);
        
        String unidad = "";
        if(total == 1) unidad = " Hora";
        else unidad = " Hs.";
        celdaEsfuerzo = new PdfPCell(new Paragraph(total+unidad,ReportDesigner.FUENTE_NORMAL));
        celdaEsfuerzo.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        celdaEsfuerzo.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaEsfuerzo);
        
        
        super.doc.add(tabla);
    }
    
    private void pintarTarea(TareaPlanificacion tarea, int orden, String tipoTarea,PdfPTable tabla, String tabulacion)  throws DocumentException
    {
        PdfPCell celdaNombre = new PdfPCell(new Paragraph(tipoTarea+" "+(orden+1)+": "+tarea.getNombre(),ReportDesigner.FUENTE_NORMAL));
        celdaNombre.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        tabla.addCell(celdaNombre);
        
        double esfuerzo = tarea.obtenerTotalDeHoras();
        String unidad = "";
        if(esfuerzo == 1) unidad = "Hora";
        else unidad = "Hs.";
        PdfPCell celdaEsfuerzo = new PdfPCell(new Paragraph(tarea.obtenerTotalDeHoras()+unidad,ReportDesigner.FUENTE_NORMAL));
        celdaEsfuerzo.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        tabla.addCell(celdaEsfuerzo);
        
        // Subtareas
        Iterator<TareaPlanificacion> itSubTareas = tarea.getSubtareas().iterator();
        int orden1 = 0;
        while(itSubTareas.hasNext())
        {
            TareaPlanificacion subtarea = itSubTareas.next();
            this.pintarTarea(subtarea, orden1, tabulacion + "Subtarea", tabla, tabulacion+="> ");
            orden1++;
        }
    }
}
