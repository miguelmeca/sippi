package vista.reportes.sources;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.ArrayList;
import java.util.HashMap;
import modelo.AsistenciaTallerCapacitacion;
import util.NTupla;
import vista.reportes.ReportDesigner;

/**
 *
 * @author Iuga
 */
public class InformeAsistenciaTallerCapacitacion  extends ReportDesigner {
    
    public static final String KEY_TITULO_CURSO = "TITULO_CURSO";
    public static final String KEY_FECHA = "FECHA_CURSO";
    public static final String KEY_HORARIO = "HORARIO_CURSO";
    
    private ArrayList<NTupla> datos;

    public InformeAsistenciaTallerCapacitacion(ArrayList<NTupla> datos) {
        this.datos = datos;
    }
    
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        makeCabecera(params);
    }

    private void makeCabecera(HashMap<String, Object> params) throws DocumentException {
        // Cotización Nro
        Paragraph PNTaller = new Paragraph();
            PNTaller.setAlignment(Paragraph.ALIGN_RIGHT);
            Phrase nroCot = new Phrase("Registro de Asistencia al Taller de Capacitación");
            PNTaller.add(nroCot);    
        super.doc.add(PNTaller);
        
        // Titulos e Introducción
        Paragraph PTitulo = new Paragraph();
            PTitulo.setAlignment(Paragraph.ALIGN_LEFT);
            Phrase nPre = new Phrase("Datos del Taller",ReportDesigner.FUENTE_TITULO_2);
            PTitulo.add(nPre);           
        
            Phrase nEM = new Phrase("\nTítulo del Curso: ",ReportDesigner.FUENTE_NORMAL_B);
            PTitulo.add(nEM);  
                Phrase nEMD = new Phrase((String)params.get(InformeAsistenciaTallerCapacitacion.KEY_TITULO_CURSO),ReportDesigner.FUENTE_NORMAL);
                PTitulo.add(nEMD);  
            Phrase nEPP = new Phrase("\nFecha del Curso: ",ReportDesigner.FUENTE_NORMAL_B);
            PTitulo.add(nEPP);  
                Phrase nEPP2 = new Phrase((String)params.get(InformeAsistenciaTallerCapacitacion.KEY_FECHA),ReportDesigner.FUENTE_NORMAL);
                PTitulo.add(nEPP2);                     
            Phrase nEHOr = new Phrase("\nHorario del Curso: ",ReportDesigner.FUENTE_NORMAL_B);
            PTitulo.add(nEHOr);  
                Phrase nEPHor = new Phrase((String)params.get(InformeAsistenciaTallerCapacitacion.KEY_HORARIO)+" Hs.",ReportDesigner.FUENTE_NORMAL);
                PTitulo.add(nEPHor);
                
        // Espacio
        Paragraph pnl = new Paragraph(new Phrase("\n"));
                
        // Tabla
        PdfPTable tabla = new PdfPTable(2);
        PdfPCell celdaNombre = new PdfPCell(new Paragraph("Empleado",ReportDesigner.FUENTE_NORMAL_B));
        celdaNombre.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        celdaNombre.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaNombre);
        
        PdfPCell celdaEsfuerzo = new PdfPCell(new Paragraph("Asistencia",ReportDesigner.FUENTE_NORMAL_B));
        celdaEsfuerzo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaEsfuerzo.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaEsfuerzo);
        
        tabla.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tabla.setWidthPercentage(95);   
        
        int empleadosAsistieron = 0;
        int empleadosNoAsistieron = 0;
        
        for (int i = 0; i < this.datos.size(); i++) {
            NTupla fila = this.datos.get(i);
            
            PdfPCell filaCeldaNombre = new PdfPCell(new Paragraph(fila.getNombre(),ReportDesigner.FUENTE_NORMAL));
            filaCeldaNombre.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            tabla.addCell(filaCeldaNombre);
            
            boolean asistio = (Boolean)fila.getData();
            if(asistio){
                PdfPCell filaCeldaAsitio = new PdfPCell(new Paragraph("Si",ReportDesigner.FUENTE_NORMAL));
                filaCeldaAsitio.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tabla.addCell(filaCeldaAsitio);
                empleadosAsistieron++;
            }else{
                PdfPCell filaCeldaAsitio = new PdfPCell(new Paragraph("No",ReportDesigner.FUENTE_NORMAL));
                filaCeldaAsitio.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tabla.addCell(filaCeldaAsitio);
                empleadosNoAsistieron++;
            }
        }
        
        Paragraph PResumen = new Paragraph();
        PResumen.setAlignment(Paragraph.ALIGN_LEFT);   
        Phrase nCEA = new Phrase("\nTotal: "+empleadosAsistieron+" empleados asistieron al taller, pero "+empleadosNoAsistieron+" no asistieron.",ReportDesigner.FUENTE_NORMAL);
        PResumen.add(nCEA);  
          
        
        super.doc.add(PTitulo);  
        super.doc.add(pnl);
        super.doc.add(tabla);
        super.doc.add(PResumen);  
    }
    
}
