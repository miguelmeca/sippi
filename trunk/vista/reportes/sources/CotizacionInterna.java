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

/**
 *
 * @author Iuga
 */
public class CotizacionInterna extends ReportDesigner{

    private int cotizacion_id;
    
    public CotizacionInterna(int cot_id) throws FileNotFoundException, DocumentException
    {
       this.cotizacion_id = cot_id;
    }

    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        // Cotización Nro
        Paragraph PNroCotizacion = new Paragraph();
            PNroCotizacion.setAlignment(Paragraph.ALIGN_RIGHT);
            Phrase nroCot = new Phrase("Cotización Nº: "+(String)params.get("COTIZACION_NRO"),new Font(Font.FontFamily.HELVETICA,11,Font.BOLD));
            PNroCotizacion.add(nroCot);    
        super.doc.add(PNroCotizacion);
        
        // Titulos e Introducción
        Paragraph PTitulo = new Paragraph();
            PTitulo.setAlignment(Paragraph.ALIGN_LEFT);
            Phrase nPre = new Phrase("Cotización",ReportDesigner.FUENTE_TITULO_1);
            PTitulo.add(nPre);    
            
            Phrase nMD = new Phrase("\nMemoria Descriptiva:\n",ReportDesigner.FUENTE_TITULO_2);
            PTitulo.add(nMD);    
            
            Phrase nMemDesc = new Phrase((String)params.get("COTIZACION_MEMDESC"),ReportDesigner.FUENTE_NORMAL);
            PTitulo.add(nMemDesc);    
            
            Phrase nIO = new Phrase("\nItems de la Obra:\n",ReportDesigner.FUENTE_NORMAL_BK);
            PTitulo.add(nIO);       
            
            Phrase nIOIntro = new Phrase("A continuación se listan en detalle los items de la obra cotizados con sus subtotales.\n\n",ReportDesigner.FUENTE_NORMAL);
            PTitulo.add(nIOIntro); 
        super.doc.add(PTitulo);
        
        // Sub Obras
        List listaSubObras = (List) params.get("LISTA_SUB_OBRAS");
        for (int i = 0; i < listaSubObras.size(); i++) 
        {
            SubObra so = (SubObra) listaSubObras.get(i);
            
                // TAblas
                PdfPTable tabla = new PdfPTable(3);
                tabla.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
                tabla.setWidthPercentage(95);
                    PdfPCell celdaNombre = new PdfPCell(new Paragraph((i+1)+"-"+so.getNombre(),ReportDesigner.FUENTE_NORMAL));
                    celdaNombre.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    celdaNombre.setBackgroundColor(new BaseColor(219,229,241));
                    celdaNombre.setColspan(3);
                    tabla.addCell(celdaNombre);
                    
                    PdfPCell celdaDesc = new PdfPCell(new Paragraph(so.getDescripcion(),ReportDesigner.FUENTE_NORMAL));
                    celdaDesc.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    celdaDesc.setColspan(3);
                    tabla.addCell(celdaDesc);
                    
                    // Mano de Obra
                    PdfPCell celdaMOT = new PdfPCell(new Paragraph("Mano de Obra",ReportDesigner.FUENTE_NORMAL_BK));
                    tabla.addCell(celdaMOT);
                        String detalleMO = new String();
                        String subtotalmo = new String();
                        for (int j = 0; j < so.getTareas().size(); j++) 
                        {
                            SubObraXTarea som = so.getTareas().get(j);
                            detalleMO += som.getNombre()+"\n";
                            subtotalmo += "$"+som.calcularSubtotal()+"\n";
                        }                      
                    PdfPCell celdaMOD = new PdfPCell(new Paragraph(detalleMO,ReportDesigner.FUENTE_NORMAL));
                    tabla.addCell(celdaMOD);
                    PdfPCell celdaMOST = new PdfPCell(new Paragraph(subtotalmo,ReportDesigner.FUENTE_NORMAL_B));
                    celdaMOST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    tabla.addCell(celdaMOST);
                    
                    // Materiales
                    PdfPCell celdaMatT = new PdfPCell(new Paragraph("Materiales",ReportDesigner.FUENTE_NORMAL_BK));
                    tabla.addCell(celdaMatT);
                        String detalle = new String();
                        String subtotalmat = new String();
                        for (int j = 0; j < so.getMateriales().size(); j++) 
                        {
                            SubObraXMaterial som = so.getMateriales().get(j);
                            detalle += som.getDescripcion()+"\n";
                            subtotalmat += "$"+som.calcularSubtotal()+"\n";
                        }                    
                    PdfPCell celdaMatD = new PdfPCell(new Paragraph(detalle,ReportDesigner.FUENTE_NORMAL_K));
                    tabla.addCell(celdaMatD);
                    PdfPCell celdaMatST = new PdfPCell(new Paragraph(subtotalmat,ReportDesigner.FUENTE_NORMAL_B));
                    celdaMatST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    tabla.addCell(celdaMatST);   
                    
                    // Herramientas
                    PdfPCell celdaHerr = new PdfPCell(new Paragraph("Herramientas",ReportDesigner.FUENTE_NORMAL_BK));
                    tabla.addCell(celdaHerr);
                        String detalleHerr = new String();
                        String subtotalherr = new String();
                        for (int j = 0; j < so.getHerramientas().size(); j++) 
                        {
                            SubObraXHerramienta som = so.getHerramientas().get(j);
                            detalleHerr += som.getHerramienta().getNroSerie()+"\n";
                            subtotalherr += "$"+som.calcularSubtotal()+"\n";
                        }                       
                    PdfPCell celdaHerrD = new PdfPCell(new Paragraph(detalleHerr,ReportDesigner.FUENTE_NORMAL));
                    tabla.addCell(celdaHerrD);
                    PdfPCell celdaHerrST = new PdfPCell(new Paragraph(subtotalherr,ReportDesigner.FUENTE_NORMAL_B));
                    celdaHerrST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    tabla.addCell(celdaHerrST);  
                    
                    // Alquileres Compras
                    PdfPCell celdaComp = new PdfPCell(new Paragraph("Alquileres / Compras",ReportDesigner.FUENTE_NORMAL_BK));
                    tabla.addCell(celdaComp);
                        String detalleComp = new String();
                        String subtotalcomp= new String();
                        for (int j = 0; j < so.getAlquileresCompras().size(); j++) 
                        {
                            SubObraXAlquilerCompra som = so.getAlquileresCompras().get(j);
                            detalleComp += som.getTipoAlquilerCompra().getNombre()+"\n";
                            subtotalcomp += "$"+som.calcularSubtotal()+"\n";
                        }                       
                    PdfPCell celdaCompD = new PdfPCell(new Paragraph(detalleComp,ReportDesigner.FUENTE_NORMAL));
                    tabla.addCell(celdaCompD);
                    PdfPCell celdaCompST = new PdfPCell(new Paragraph(subtotalcomp,ReportDesigner.FUENTE_NORMAL_B));
                    celdaCompST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    tabla.addCell(celdaCompST);      
                    
                    // Adicionales
                    PdfPCell celdaAdic = new PdfPCell(new Paragraph("Adicionales",ReportDesigner.FUENTE_NORMAL_BK));
                    tabla.addCell(celdaAdic);
                        String detalleAdic = new String();
                        String subtotalAdic= new String();
                        for (int j = 0; j < so.getAdicionales().size(); j++) 
                        {
                            SubObraXAdicional som = so.getAdicionales().get(j);
                            detalleAdic += som.getTipoAdicional().getNombre()+"\n";
                            subtotalAdic += "$"+som.calcularSubtotal()+"\n";
                        }                       
                    PdfPCell celdaAdicD = new PdfPCell(new Paragraph(detalleAdic,ReportDesigner.FUENTE_NORMAL));
                    tabla.addCell(celdaAdicD);
                    PdfPCell celdaAdicST = new PdfPCell(new Paragraph(subtotalAdic,ReportDesigner.FUENTE_NORMAL_B));
                    celdaAdicST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    tabla.addCell(celdaAdicST);                      

                    // Beneficios
                    PdfPCell celdaBen = new PdfPCell(new Paragraph("Beneficios",ReportDesigner.FUENTE_NORMAL_BK));
                    tabla.addCell(celdaBen);
                        String detalleBen = new String();
                        String subtotalBen= new String();
                        if(so.getGananciaPorcentaje()!=0)
                        {
                            detalleBen = so.getGananciaPorcentaje()+"%";
                        }
                        subtotalBen ="$"+so.getGananciaMonto();

                    PdfPCell celdaBenD = new PdfPCell(new Paragraph(detalleBen,ReportDesigner.FUENTE_NORMAL));
                    tabla.addCell(celdaBenD);
                    PdfPCell celdaBenST = new PdfPCell(new Paragraph(subtotalBen,ReportDesigner.FUENTE_NORMAL_B));
                    celdaBenST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    tabla.addCell(celdaBenST); 
                    
                    // Sub Total
                    tabla.addCell("");
                    tabla.addCell("");
                    PdfPCell celdaST = new PdfPCell(new Paragraph("$"+so.calcularSubtotal(),ReportDesigner.FUENTE_NORMAL_B));
                    celdaST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    celdaST.setBackgroundColor(new BaseColor(214,227,188));
                    tabla.addCell(celdaST);
                    
                super.doc.add(tabla);
                
                Paragraph pnl = new Paragraph(new Phrase("\n"));
                super.doc.add(pnl);
        }
        
        // TOTAL
        PdfPTable tabla = new PdfPTable(2);
        tabla.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tabla.setWidthPercentage(95);
            PdfPCell celdaNombre = new PdfPCell(new Paragraph("Precio Total Cotizado por la Obra:",ReportDesigner.FUENTE_NORMAL));
            tabla.addCell(celdaNombre);
            
            PdfPCell celdaTotal = new PdfPCell(new Paragraph((String)params.get("COTIZACION_TOTAL"),ReportDesigner.FUENTE_NORMAL_B));
            celdaTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            celdaTotal.setBackgroundColor(new BaseColor(194,214,155));
            tabla.addCell(celdaTotal); 
        super.doc.add(tabla);
        
        Paragraph pnl = new Paragraph(new Phrase("\n"));
        super.doc.add(pnl);   
        
        /*
         * Datos Adicionales:
            Forma de pago: A convenir.
            Plazo de Entrega: A convenir.
            Lugar de entrega: Planta Bagley Villa Mercedes (San Luis).
            Validez de la Oferta: 7 días (Desde el 11/06/2011)

         * 
         */
        
        Paragraph datosGenerales = new Paragraph();
            datosGenerales.add(new Phrase("Datos Adicionales:\n", FUENTE_TITULO_2));
            datosGenerales.add(new Phrase("Forma de pago: ", FUENTE_NORMAL_B));
            datosGenerales.add(new Phrase((String)params.get("FORMA_DE_PAGO"), FUENTE_NORMAL));
            datosGenerales.add(new Phrase("\nPlazo de entrega: ", FUENTE_NORMAL_B));
            datosGenerales.add(new Phrase((String)params.get("PLAZO_ENTREGA"), FUENTE_NORMAL));
            datosGenerales.add(new Phrase("\nLugar de entrega: ", FUENTE_NORMAL_B));
            datosGenerales.add(new Phrase((String)params.get("LUGAR_ENTREGA"), FUENTE_NORMAL));    
            datosGenerales.add(new Phrase("\nValidez de la Oferta: ", FUENTE_NORMAL_B));
            datosGenerales.add(new Phrase((String)params.get("VALIDEZ_OFERTA"), FUENTE_NORMAL));                
        super.doc.add(datosGenerales);
        
    }
    
    
    
    
    
}
