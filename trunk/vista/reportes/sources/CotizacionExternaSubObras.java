/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import modelo.SubObra;
import modelo.SubObraXAdicional;
import modelo.SubObraXAlquilerCompra;
import modelo.SubObraXHerramienta;
import modelo.SubObraXMaterial;
import modelo.SubObraXTarea;
import vista.reportes.ReportDesigner;
import java.io.File;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
/**
 *
 * @author Iuga
 */
public class CotizacionExternaSubObras extends ReportDesigner{

    private int cotizacion_id;
    
    public CotizacionExternaSubObras(int cot_id) throws FileNotFoundException, DocumentException
    {
       this.cotizacion_id = cot_id;
       /*super.out_file = new File("Reporte_Cotizacion-Externa-Items-Obra"+cotizacion_id+".pdf");
       PdfWriter.getInstance(super.doc, new FileOutputStream(out_file));
       doc.open();*/
    }

    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        // Cotización Nro
        Paragraph PNroCotizacion = new Paragraph();
            PNroCotizacion.setAlignment(Paragraph.ALIGN_RIGHT);
            Phrase nroCot = new Phrase("Presupuesto Nº: "+(String)params.get("COTIZACION_NRO"),new Font(Font.FontFamily.HELVETICA,11,Font.BOLD));
            PNroCotizacion.add(nroCot);    
        super.doc.add(PNroCotizacion);
        
        // Titulos e Introducción
        Paragraph PTitulo = new Paragraph();
            PTitulo.setAlignment(Paragraph.ALIGN_LEFT);
            Phrase nPre = new Phrase("Presupuesto",new Font(Font.FontFamily.HELVETICA,14,Font.BOLD));
            PTitulo.add(nPre);    
            
            Phrase nMD = new Phrase("\nMemoria Descriptiva:\n",new Font(Font.FontFamily.HELVETICA,12,Font.BOLDITALIC));
            PTitulo.add(nMD);    
            
            Phrase nMemDesc = new Phrase((String)params.get("COTIZACION_MEMDESC"),new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL));
            PTitulo.add(nMemDesc);    
            
            Phrase nIO = new Phrase("\nItems de la Obra:\n",new Font(Font.FontFamily.HELVETICA,12,Font.BOLDITALIC));
            PTitulo.add(nIO);       
            
            Phrase nIOIntro = new Phrase("A continuación se listan los items de la obra presupuestados con sus subtotales.\n\n",new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL));
            PTitulo.add(nIOIntro); 
        super.doc.add(PTitulo);
        
        // Sub Obras
        List listaSubObras = (List) params.get("LISTA_SUB_OBRAS");
        // TAbla
        PdfPTable tabla = new PdfPTable(4);
        tabla.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tabla.setWidthPercentage(95);
        for (int i = 0; i < listaSubObras.size(); i++) 
        {
            
            SubObra so = (SubObra) listaSubObras.get(i);
            
                
                    PdfPCell celdaNombre = new PdfPCell(new Paragraph((i+1)+"-"+so.getNombre(),new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL)));
                    celdaNombre.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    celdaNombre.setColspan(3);
                    tabla.addCell(celdaNombre);
                   
                    PdfPCell celdaST = new PdfPCell(new Paragraph("$"+so.calcularSubtotal(),new Font(Font.FontFamily.HELVETICA,10,Font.BOLD)));
                    celdaST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    celdaST.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
                    tabla.addCell(celdaST);
                    
                
        }
        super.doc.add(tabla);
                
        Paragraph pnl = new Paragraph(new Phrase("\n"));
        super.doc.add(pnl);
        
        // TOTAL
        PdfPTable tablaT = new PdfPTable(4);
        tablaT.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tablaT.setWidthPercentage(95);
            PdfPCell celdaNombre = new PdfPCell(new Paragraph("Precio Total Cotizado por la Obra:",new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL)));
            celdaNombre.setColspan(3);
            tablaT.addCell(celdaNombre);
            
            PdfPCell celdaTotal = new PdfPCell(new Paragraph((String)params.get("COTIZACION_TOTAL"),new Font(Font.FontFamily.HELVETICA,10,Font.BOLD)));
            celdaTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            celdaTotal.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
            celdaTotal.setBackgroundColor(new BaseColor(194,214,155));
            tablaT.addCell(celdaTotal); 
        super.doc.add(tablaT);
        
        //Paragraph pnl = new Paragraph(new Phrase("\n"));
        super.doc.add(pnl);
        
        String FORMA_DE_PAGO=(String)params.get("FORMA_DE_PAGO");
        String PLAZO_ENTREGA=(String)params.get("PLAZO_ENTREGA");
        String LUGAR_ENTREGA=(String)params.get("LUGAR_ENTREGA");;
        String VALIDEZ_OFERTA=(String)params.get("PLAZO_ENTREGA");;
        
        if(   (FORMA_DE_PAGO!=null && !FORMA_DE_PAGO.isEmpty()) || (PLAZO_ENTREGA!=null && !PLAZO_ENTREGA.isEmpty()) ||  (LUGAR_ENTREGA!=null && !LUGAR_ENTREGA.isEmpty()) ||   (VALIDEZ_OFERTA!=null && !VALIDEZ_OFERTA.isEmpty()) )
        {
         Paragraph datosGenerales = new Paragraph();
         datosGenerales.add(new Phrase("Datos Adicionales:\n", FUENTE_TITULO_2));
            if(FORMA_DE_PAGO!=null && !FORMA_DE_PAGO.isEmpty())
            {
                datosGenerales.add(new Phrase("Forma de pago: ", FUENTE_NORMAL_B));
                datosGenerales.add(new Phrase((String)params.get("FORMA_DE_PAGO"), FUENTE_NORMAL));
            }
            if(PLAZO_ENTREGA!=null && !PLAZO_ENTREGA.isEmpty())
            {
                datosGenerales.add(new Phrase("\nPlazo de entrega: ", FUENTE_NORMAL_B));
                datosGenerales.add(new Phrase((String)params.get("PLAZO_ENTREGA"), FUENTE_NORMAL));
            }
            if(LUGAR_ENTREGA!=null && !LUGAR_ENTREGA.isEmpty())
            {
                datosGenerales.add(new Phrase("\nLugar de entrega: ", FUENTE_NORMAL_B));
                datosGenerales.add(new Phrase((String)params.get("LUGAR_ENTREGA"), FUENTE_NORMAL)); 
            }
            if(VALIDEZ_OFERTA!=null && !VALIDEZ_OFERTA.isEmpty())
            {
                datosGenerales.add(new Phrase("\nValidez de la Oferta: ", FUENTE_NORMAL_B));
                datosGenerales.add(new Phrase((String)params.get("VALIDEZ_OFERTA"), FUENTE_NORMAL)); 
            }  
                           
         super.doc.add(datosGenerales);
    }   }
    
    
    
    
    
}
