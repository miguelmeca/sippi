/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import util.FechaUtil;

/**
 * Diseñador de Reportes con iText
 * @author Iuga
 */
public class ReportDesigner 
{
    private final String URL_DIR_REPORTES = "Reportes/";
    
    public static final int REPORTE_TIPO_OTROS         = 0;
    public static final int REPORTE_TIPO_COTIZACION    = 1;
    public static final int REPORTE_TIPO_PLANIFICACION = 2;
    public static final int REPORTE_TIPO_SEGUIMIENTO   = 3;
    public static final int REPORTE_TIPO_LISTADOS      = 4;
    public static final int REPORTE_TIPO_COMPRAS       = 5;
    public static final int REPORTE_TIPO_ORDENESTRABAJO= 6;
    public static final int REPORTE_TIPO_EJECUCION = 7;
    public static final int REPORTE_TIPO_CONTROL = 8;
    
    //public static final Font FUENTE_TITULO_1 = new Font(Font.FontFamily.HELVETICA,14,Font.BOLD);
    //public static final Font FUENTE_TITULO_2 = new Font(Font.FontFamily.HELVETICA,12,Font.BOLDITALIC);
    //public static final Font FUENTE_NORMAL   = new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL);
    //public static final Font FUENTE_NORMAL_B   = new Font(Font.FontFamily.HELVETICA,10,Font.BOLD);
    //public static final Font FUENTE_NORMAL_K   = new Font(Font.FontFamily.HELVETICA,10,Font.ITALIC);
    //public static final Font FUENTE_NORMAL_BK   = new Font(Font.FontFamily.HELVETICA,10,Font.BOLDITALIC);
    
    public static final Font FUENTE_TITULO_1 = FontFactory.getFont("Calibri",14,Font.BOLD);
    public static final Font FUENTE_TITULO_2 = FontFactory.getFont("Calibri",12,Font.BOLDITALIC);
    public static final Font FUENTE_NORMAL   = FontFactory.getFont("Calibri",10,Font.NORMAL);
    public static final Font FUENTE_NORMAL_CHICA   = FontFactory.getFont("Calibri",9,Font.NORMAL);
    public static final Font FUENTE_NORMAL_B   = FontFactory.getFont("Calibri",10,Font.BOLD);
    public static final Font FUENTE_NORMAL_K   = FontFactory.getFont("Calibri",10,Font.ITALIC);
    public static final Font FUENTE_NORMAL_BK   = FontFactory.getFont("Calibri",10,Font.BOLDITALIC);
    
    public static final BaseColor COLOR_HEADINGS = new BaseColor(219,229,241);
    
    public static final BaseColor TABLE_HEADER_BASE_COLOR = new BaseColor(230,230,230);
    public static final BaseColor TABLE_SUBHEADER_BASE_COLOR = new BaseColor(240,240,240);
    
    private String nombre;
    protected Document doc;
    private PdfWriter out;
    private File out_file;
    private int tipoReporte;
    
    public ReportDesigner()
    {
        this.nombre = "";
        this.tipoReporte = ReportDesigner.REPORTE_TIPO_OTROS;
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
     * Setea el nombre del archivo ( No necesariamente será el final )
     * @param nombre 
     */
    public void setNombreArchivo(String nombre, int tipoReporte)
    {
        this.tipoReporte = tipoReporte;
        this.out_file = generarNombre(nombre);
    }
    
   
    /**
     * Compila y muestra el reporte
     */
    public void makeAndShow(HashMap<String,Object> params)  throws DocumentException, FileNotFoundException
    {
        // Create Directory if this doesn't exist
        if(!verificarYCrearDirectorio())
            throw new FileNotFoundException("No se pudo crear el directorio del reporte");
        
        // Default Name
        if(this.out_file==null)
        {
            this.out_file = generarNombre("ReporteTest");
        }

        // PDF Settings
        doc = new Document(PageSize.A4, 36,36,36,108);
        doc.addCreationDate();
        doc.addAuthor("ACERO");
        PdfWriter.getInstance(this.doc, new FileOutputStream(this.out_file));
        
        // Open Doc
        this.doc.open();
        
        // Compile Report
        try
        {
            makeCabecera();
            makeCuerpo(params);
            makePie();
        }
        catch(Exception e)
        {
            System.err.println("No se pudo compilar el Reporte:");
            e.printStackTrace();
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

            Phrase locEmpresa = new Phrase("\nVilla  Mercedes (S.L.) - "+FechaUtil.getMaskedDate("dd/MM/yyyy",new Date()) +"\n\n",new Font(Font.FontFamily.HELVETICA,8,Font.BOLD));
            encabezado.add(locEmpresa);        
        this.doc.add(encabezado);
        
        insertarTitulo(this.nombre.toUpperCase());
        
    }
    
    private void makePie() throws DocumentException
    {

    }

    private File generarNombre(String nombre) 
    {
        File test;
        String fileName;
        int i = 1;
     
        do
        {
            fileName = getCarpetaDestino()+nombre +"-"+ FechaUtil.getMaskedDate("yyyyMMdd",new Date())+"-"+ i + ".pdf";
            test = new File(fileName);
            i++;
        }
        while(test.exists());
        
        return test;
    }
    
    private String getCarpetaDestino()
    {
        switch(this.tipoReporte)
        {               
            case ReportDesigner.REPORTE_TIPO_COTIZACION:
                return URL_DIR_REPORTES+"Cotizaciones/"; 
                
            case ReportDesigner.REPORTE_TIPO_PLANIFICACION:
                return URL_DIR_REPORTES+"Planificaciones/";     
                
            case ReportDesigner.REPORTE_TIPO_COMPRAS:
                return URL_DIR_REPORTES+"Compras/";                  
                
            case ReportDesigner.REPORTE_TIPO_SEGUIMIENTO:
                return URL_DIR_REPORTES+"Seguimientos/";    
               
            case ReportDesigner.REPORTE_TIPO_LISTADOS:
                return URL_DIR_REPORTES+"Listados/";                    

            case ReportDesigner.REPORTE_TIPO_ORDENESTRABAJO:
                return URL_DIR_REPORTES+"OrdenesDeTrabajo/";  
            
            case ReportDesigner.REPORTE_TIPO_EJECUCION:
                return URL_DIR_REPORTES+"Ejecucion/";  
                
            case ReportDesigner.REPORTE_TIPO_CONTROL:
                return URL_DIR_REPORTES+"Control/";  
                
            case ReportDesigner.REPORTE_TIPO_OTROS: 
                return URL_DIR_REPORTES+"Otros/";
                
            default:
                return URL_DIR_REPORTES;             
        }
    }

    private boolean verificarYCrearDirectorio() {
        String path = "";
        switch(this.tipoReporte)
        {               
            case ReportDesigner.REPORTE_TIPO_COTIZACION:
                path = URL_DIR_REPORTES+"Cotizaciones/";
                break;
                
            case ReportDesigner.REPORTE_TIPO_PLANIFICACION:
                path = URL_DIR_REPORTES+"Planificaciones/";   
                break;
                
            case ReportDesigner.REPORTE_TIPO_COMPRAS:
                path = URL_DIR_REPORTES+"Compras/";         
                break;

            case ReportDesigner.REPORTE_TIPO_ORDENESTRABAJO:
                path = URL_DIR_REPORTES+"OrdenesDeTrabajo/";         
                break;
                
            case ReportDesigner.REPORTE_TIPO_SEGUIMIENTO:
                path = URL_DIR_REPORTES+"Seguimientos/";    
                break;
               
            case ReportDesigner.REPORTE_TIPO_LISTADOS:
                path = URL_DIR_REPORTES+"Listados/";        
                break;
            case ReportDesigner.REPORTE_TIPO_EJECUCION:
                path = URL_DIR_REPORTES+"Ejecucion/";        
                break;
                
            case ReportDesigner.REPORTE_TIPO_CONTROL:
                path = URL_DIR_REPORTES+"Control/";        
                break;
                
            case ReportDesigner.REPORTE_TIPO_OTROS: 
                path = URL_DIR_REPORTES+"Otros/";
                break;
                
            default:
                path = URL_DIR_REPORTES; 
                break;
        }
        File file = new File(path);
        
        // Compruebo si existe el directorio
        if(!file.exists())
            return file.mkdirs();
        
        return true;
    }

    protected void insertarTitulo(String nombretitulo) throws DocumentException {
        // TITULO
        PdfPTable titulo = new PdfPTable(1);
            titulo.setWidthPercentage(100);
            PdfPCell celda = new PdfPCell(new Paragraph(nombretitulo,new Font(Font.FontFamily.HELVETICA,12,Font.BOLD)));
            celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            celda.setBorder(PdfPCell.NO_BORDER);
            titulo.addCell(celda);
        this.doc.add(titulo);
    }
    
    protected void insertarSubTitulo(String nombretitulo) throws DocumentException {
        // TITULO
        PdfPTable titulo = new PdfPTable(1);
            titulo.setWidthPercentage(100);
            titulo.setSpacingBefore(5f);
            PdfPCell celda = new PdfPCell(new Paragraph(nombretitulo,new Font(Font.FontFamily.HELVETICA,10,Font.BOLD)));
            celda.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            celda.setBackgroundColor(new BaseColor(200,200,200));
            celda.setBorder(PdfPCell.NO_BORDER);
            titulo.addCell(celda);
        this.doc.add(titulo);
    }
    
    protected void insertarCeldaHeaderEnTabla(PdfPTable tabla,String texto,Font fuente, int align) {
        Paragraph paMontoCotizado = new Paragraph();
        paMontoCotizado.add(new Phrase(texto,fuente));
        PdfPCell celdaMontoCotizado = new PdfPCell(paMontoCotizado);
        celdaMontoCotizado.setHorizontalAlignment(align);
        celdaMontoCotizado.setBackgroundColor(ReportDesigner.TABLE_HEADER_BASE_COLOR);
        tabla.addCell(celdaMontoCotizado);
    }
    
    protected void insertarCeldaEnTabla(PdfPTable tabla,String texto,Font fuente, int align) {
        Paragraph paMontoCotizado = new Paragraph();
        paMontoCotizado.add(new Phrase(texto,fuente));
        PdfPCell celdaMontoCotizado = new PdfPCell(paMontoCotizado);
        celdaMontoCotizado.setHorizontalAlignment(align);
        tabla.addCell(celdaMontoCotizado);
    }
    
}
