/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes;

import com.adobe.acrobat.Viewer;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import config.SConfig;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Diseñador de Reportes con iText
 * @author Iuga
 */
public class ReportDesigner 
{
    private String nombre;
    protected Document doc;
    private PdfWriter out;
    private File out_file;
    
    public ReportDesigner() throws FileNotFoundException, DocumentException
    {
        this.nombre = "";
        this.out_file = new File("ReporteTest.pdf");
        
        doc = new Document(PageSize.A4, 36,36,36,108);
        PdfWriter.getInstance(this.doc, new FileOutputStream(out_file));
        this.doc.open();
    }

    /**
     * Setea el nombre que se va a mostrar en el Reporte
     * @param nombre 
     */
    public void setNombreReporte(String nombre) 
    {
        this.nombre = nombre;
    }
    
    /**
     * Compila y muestra el reporte
     */
    public void makeAndShow()  throws DocumentException
    {
        makeCabecera();
        makeCuerpo();
        makePie();
        
        // Close Doc
        this.doc.close(); 
       
    }
            
    private void makeCabecera()
    {
        Paragraph encabezado = new Paragraph();
        encabezado.setAlignment(Paragraph.ALIGN_CENTER);
        Phrase nombreEmpresa = new Phrase(SConfig.getInstance().getNombreEmpresa(),new Font(Font.FontFamily.HELVETICA,24,Font.BOLD));
        encabezado.add(nombreEmpresa);
        
    }
    
    protected void makeCuerpo() throws DocumentException
    {
        //Only To Override...
    }
    
    private void makePie()
    {
        
    }
    
    
}
