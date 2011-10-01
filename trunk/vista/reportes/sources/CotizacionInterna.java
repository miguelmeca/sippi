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
            
            Phrase nIOIntro = new Phrase("A continuación se listan en detalle los items de la obra presupuestados con sus subtotales.\n\n",new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL));
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
                    PdfPCell celdaNombre = new PdfPCell(new Paragraph((i+1)+"-"+so.getNombre(),new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL)));
                    celdaNombre.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    celdaNombre.setBackgroundColor(new BaseColor(219,229,241));
                    celdaNombre.setColspan(3);
                    tabla.addCell(celdaNombre);
                    
                    PdfPCell celdaDesc = new PdfPCell(new Paragraph(so.getDescripcion(),new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL)));
                    celdaDesc.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    celdaDesc.setColspan(3);
                    tabla.addCell(celdaDesc);
                    
                    // Mano de Obra
                    PdfPCell celdaMOT = new PdfPCell(new Paragraph("Mano de Obra",new Font(Font.FontFamily.HELVETICA,10,Font.BOLDITALIC)));
                    tabla.addCell(celdaMOT);
                        String detalleMO = new String();
                        String subtotalmo = new String();
                        for (int j = 0; j < so.getTareas().size(); j++) 
                        {
                            SubObraXTarea som = so.getTareas().get(j);
                            detalleMO += som.getTipoTarea().getNombre()+"\n";
                            subtotalmo += "$"+som.calcularSubtotal()+"\n";
                        }                      
                    PdfPCell celdaMOD = new PdfPCell(new Paragraph(detalleMO,new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL)));
                    tabla.addCell(celdaMOD);
                    PdfPCell celdaMOST = new PdfPCell(new Paragraph(subtotalmo,new Font(Font.FontFamily.HELVETICA,10,Font.BOLD)));
                    celdaMOST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    tabla.addCell(celdaMOST);
                    
                    // Materiales
                    PdfPCell celdaMatT = new PdfPCell(new Paragraph("Materiales",new Font(Font.FontFamily.HELVETICA,10,Font.BOLDITALIC)));
                    tabla.addCell(celdaMatT);
                        String detalle = new String();
                        String subtotalmat = new String();
                        for (int j = 0; j < so.getMateriales().size(); j++) 
                        {
                            SubObraXMaterial som = so.getMateriales().get(j);
                            detalle += som.getDescripcion()+"\n";
                            subtotalmat += "$"+som.calcularSubtotal()+"\n";
                        }                    
                    PdfPCell celdaMatD = new PdfPCell(new Paragraph(detalle,new Font(Font.FontFamily.HELVETICA,10,Font.ITALIC)));
                    tabla.addCell(celdaMatD);
                    PdfPCell celdaMatST = new PdfPCell(new Paragraph(subtotalmat,new Font(Font.FontFamily.HELVETICA,10,Font.BOLD)));
                    celdaMatST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    tabla.addCell(celdaMatST);   
                    
                    // Herramientas
                    PdfPCell celdaHerr = new PdfPCell(new Paragraph("Herramientas",new Font(Font.FontFamily.HELVETICA,10,Font.BOLDITALIC)));
                    tabla.addCell(celdaHerr);
                        String detalleHerr = new String();
                        String subtotalherr = new String();
                        for (int j = 0; j < so.getHerramientas().size(); j++) 
                        {
                            SubObraXHerramienta som = so.getHerramientas().get(j);
                            detalleHerr += som.getHerramienta().getNroSerie()+"\n";
                            subtotalherr += "$"+som.calcularSubtotal()+"\n";
                        }                       
                    PdfPCell celdaHerrD = new PdfPCell(new Paragraph(detalleHerr,new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL)));
                    tabla.addCell(celdaHerrD);
                    PdfPCell celdaHerrST = new PdfPCell(new Paragraph(subtotalherr,new Font(Font.FontFamily.HELVETICA,10,Font.BOLD)));
                    celdaHerrST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    tabla.addCell(celdaHerrST);  
                    
                    // Alquileres Compras
                    PdfPCell celdaComp = new PdfPCell(new Paragraph("Alquileres / Compras",new Font(Font.FontFamily.HELVETICA,10,Font.BOLDITALIC)));
                    tabla.addCell(celdaComp);
                        String detalleComp = new String();
                        String subtotalcomp= new String();
                        for (int j = 0; j < so.getAlquileresCompras().size(); j++) 
                        {
                            SubObraXAlquilerCompra som = so.getAlquileresCompras().get(j);
                            detalleComp += som.getTipoAlquilerCompra().getNombre()+"\n";
                            subtotalcomp += "$"+som.calcularSubtotal()+"\n";
                        }                       
                    PdfPCell celdaCompD = new PdfPCell(new Paragraph(detalleComp,new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL)));
                    tabla.addCell(celdaCompD);
                    PdfPCell celdaCompST = new PdfPCell(new Paragraph(subtotalcomp,new Font(Font.FontFamily.HELVETICA,10,Font.BOLD)));
                    celdaCompST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    tabla.addCell(celdaCompST);      
                    
                    // Adicionales
                    PdfPCell celdaAdic = new PdfPCell(new Paragraph("Adicionales",new Font(Font.FontFamily.HELVETICA,10,Font.BOLDITALIC)));
                    tabla.addCell(celdaAdic);
                        String detalleAdic = new String();
                        String subtotalAdic= new String();
                        for (int j = 0; j < so.getAdicionales().size(); j++) 
                        {
                            SubObraXAdicional som = so.getAdicionales().get(j);
                            detalleAdic += som.getTipoAdicional().getNombre()+"\n";
                            subtotalAdic += "$"+som.calcularSubtotal()+"\n";
                        }                       
                    PdfPCell celdaAdicD = new PdfPCell(new Paragraph(detalleAdic,new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL)));
                    tabla.addCell(celdaAdicD);
                    PdfPCell celdaAdicST = new PdfPCell(new Paragraph(subtotalAdic,new Font(Font.FontFamily.HELVETICA,10,Font.BOLD)));
                    celdaAdicST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    tabla.addCell(celdaAdicST);                      

                    // Beneficios
                    PdfPCell celdaBen = new PdfPCell(new Paragraph("Beneficios",new Font(Font.FontFamily.HELVETICA,10,Font.BOLDITALIC)));
                    tabla.addCell(celdaBen);
                        String detalleBen = new String();
                        String subtotalBen= new String();
                        if(so.getGananciaPorcentaje()!=0)
                        {
                            detalleBen = so.getGananciaPorcentaje()+"%";
                        }
                        subtotalBen ="$"+so.getGananciaMonto();

                    PdfPCell celdaBenD = new PdfPCell(new Paragraph(detalleBen,new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL)));
                    tabla.addCell(celdaBenD);
                    PdfPCell celdaBenST = new PdfPCell(new Paragraph(subtotalBen,new Font(Font.FontFamily.HELVETICA,10,Font.BOLD)));
                    celdaBenST.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    tabla.addCell(celdaBenST); 
                    
                    // Sub Total
                    tabla.addCell("");
                    tabla.addCell("");
                    PdfPCell celdaST = new PdfPCell(new Paragraph("$"+so.calcularSubtotal(),new Font(Font.FontFamily.HELVETICA,10,Font.BOLD)));
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
            PdfPCell celdaNombre = new PdfPCell(new Paragraph("Precio Total Cotizado por la Obra:",new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL)));
            tabla.addCell(celdaNombre);
            
            PdfPCell celdaTotal = new PdfPCell(new Paragraph((String)params.get("COTIZACION_TOTAL"),new Font(Font.FontFamily.HELVETICA,10,Font.BOLD)));
            celdaTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            celdaTotal.setBackgroundColor(new BaseColor(194,214,155));
            tabla.addCell(celdaTotal); 
        super.doc.add(tabla);
        
        Paragraph pnl = new Paragraph(new Phrase("\n"));
        super.doc.add(pnl);        
    }
    
    
    
    
    
}
