/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes;

import com.adobe.acrobat.Viewer;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import config.SConfig;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.Object;
import java.util.HashMap;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
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
    public void makeAndShow(HashMap<String,Object> params)  throws DocumentException
    {
        // Compile Report
        try
        {
            makeCabecera();
            makeCuerpo(params);
            makePie();
        }
        catch(Exception e)
        {
            System.err.println("No se pudo compilar el Reporte");
        }
        // Close Doc
        this.doc.close(); 
       
        // Show
        showReport();
    }

    protected void makeCuerpo(HashMap<String, Object> parametros) throws DocumentException
    {
        //Only To Override...
    } 
    
    private void showReport()
    {
        try
        {
            IAppPdf viewer = new CrossPlatafformAppPdf();
            viewer.open(out_file);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(new JInternalFrame(),"No se pudo mostrar el reporte\nMotivo:"+e.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
    }    
    
    private void makeCabecera() throws DocumentException
    {
        // LOGO
        try
        {
            Image imgLogo = Image.getInstance(getClass().getResource("/vista/reportes/logoMetar.png"));
            imgLogo.setAlignment(Image.LEFT | Image.UNDERLYING);
            this.doc.add(imgLogo);
        }
        catch(Exception e){e.printStackTrace();}
        
        // INFO DE LA EMPRESA
        Paragraph encabezado = new Paragraph();
            encabezado.setAlignment(Paragraph.ALIGN_RIGHT);
            Phrase nombreEmpresa = new Phrase("CONSTRUCCIÓN, ESTRUCTURAS Y MONTAJES",new Font(Font.FontFamily.HELVETICA,11,Font.BOLD));
            encabezado.add(nombreEmpresa);

            Phrase dirEmpresa = new Phrase("\nDR. DOMINGUEZ 283 - TEL. (02657)431599 / ALDOROMERO@SPEEDY.COM.AR ",new Font(Font.FontFamily.HELVETICA,8,Font.BOLD));
            encabezado.add(dirEmpresa);

            Phrase locEmpresa = new Phrase("\nVilla  Mercedes (S.L.) - 30 de Marzo de 2011\n\n",new Font(Font.FontFamily.HELVETICA,8,Font.BOLD));
            encabezado.add(locEmpresa);        
        this.doc.add(encabezado);
        
        // TITULO
        PdfPTable titulo = new PdfPTable(1);
            titulo.setWidthPercentage(100);
            PdfPCell celda = new PdfPCell(new Paragraph(this.nombre.toUpperCase()));
            celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            celda.setBorder(PdfPCell.NO_BORDER);
            titulo.addCell(celda);
        this.doc.add(titulo);
        
    }
    
    private void makePie()
    {
        
    }
    
    
}
