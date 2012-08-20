/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import modelo.DetalleOrdenDeCompra;
import modelo.OrdenDeCompra;
import util.FechaUtil;
import vista.reportes.ReportDesigner;

/**
 * @author Iuga
 */
public class EmitirOrdenDeCompra extends ReportDesigner  {
    
    private OrdenDeCompra odc;

    public EmitirOrdenDeCompra(OrdenDeCompra odc) {
        this.odc = odc;
    }
    
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {     
        // Cotización Nro
//        Paragraph PNroOrdenDeCompra = new Paragraph();
//            PNroOrdenDeCompra.setAlignment(Paragraph.ALIGN_RIGHT);
//            Phrase nroOC = new Phrase("Orden De Compra Nº: "+String.valueOf(this.odc.getId()),new Font(Font.FontFamily.HELVETICA,11,Font.BOLD));
//            PNroOrdenDeCompra.add(nroOC);    
//        super.doc.add(PNroOrdenDeCompra);
        
        
        // Titulos e Introducción
        Paragraph PTitulo = new Paragraph();
            PTitulo.setAlignment(Paragraph.ALIGN_LEFT);
            Phrase nPre = new Phrase("Datos de la Orden de Compra",ReportDesigner.FUENTE_TITULO_2);
            PTitulo.add(nPre);    
            
            Phrase nEM = new Phrase("\nProveedor: ",ReportDesigner.FUENTE_NORMAL_B);
            PTitulo.add(nEM);  
                Phrase nEMD = new Phrase(this.odc.getProveedor().getRazonSocial(),ReportDesigner.FUENTE_NORMAL);
                PTitulo.add(nEMD);  
            Phrase nEPP = new Phrase("\nFecha: ",ReportDesigner.FUENTE_NORMAL_B);
            PTitulo.add(nEPP);  
                Phrase nEPP2 = new Phrase(String.valueOf(FechaUtil.getFecha(this.odc.getFechaUltimaModificacion())),ReportDesigner.FUENTE_NORMAL);
                PTitulo.add(nEPP2);                  
            Phrase nOB = new Phrase("\nForma de Pago: ",ReportDesigner.FUENTE_NORMAL_B);
            PTitulo.add(nOB); 
                Phrase nEM2 = new Phrase(String.valueOf(this.odc.getFormaDePago().getNombre()),ReportDesigner.FUENTE_NORMAL);
                PTitulo.add(nEM2);  
            Phrase nFI = new Phrase("\nForma de Entrega: ",ReportDesigner.FUENTE_NORMAL_B);
            PTitulo.add(nFI);    
                Phrase nEM3 = new Phrase(String.valueOf(this.odc.getFormaDeEntrega()),ReportDesigner.FUENTE_NORMAL);
                PTitulo.add(nEM3);              
        super.doc.add(PTitulo);
        
        Paragraph pnl = new Paragraph(new Phrase("\n"));
        super.doc.add(pnl);
        
        Iterator<DetalleOrdenDeCompra> itDetalleOC = this.odc.getDetalle().iterator();
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        
        Paragraph paCantidad = new Paragraph();
        paCantidad.add(new Phrase("Cantidad", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaCantidad = new PdfPCell(paCantidad);
        celdaCantidad.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaCantidad.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaCantidad);
        
        Paragraph paDescripcion = new Paragraph();
        paDescripcion.add(new Phrase("Descripción", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaDescripcion = new PdfPCell(paDescripcion);
        celdaDescripcion.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaDescripcion.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaDescripcion);
        
        Paragraph paPrecioUnitario = new Paragraph();
        paPrecioUnitario.add(new Phrase("Precio Unit. ", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaPrecioUnitario = new PdfPCell(paPrecioUnitario);
        celdaPrecioUnitario.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaPrecioUnitario.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaPrecioUnitario);
        
        Paragraph paSubtotal = new Paragraph();
        paSubtotal.add(new Phrase("Subtotal ", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaSubtotal = new PdfPCell(paSubtotal);
        celdaSubtotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaSubtotal.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaSubtotal);
        
        double total = 0;
        while(itDetalleOC.hasNext())
        {
            DetalleOrdenDeCompra detalle = itDetalleOC.next();
            this.pintarDetalle(tabla, detalle);
            total += detalle.getCantidad()* detalle.getPrecioUnitario();
        }
        if(total != 0){
            tabla.addCell("");tabla.addCell("");tabla.addCell("");
            PdfPCell celdaTotal = new PdfPCell(new Paragraph(String.valueOf(total),ReportDesigner.FUENTE_NORMAL_B));
            celdaTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            celdaTotal.setBackgroundColor(new BaseColor(194,214,155));
            tabla.addCell(celdaTotal);
        }
        
        super.doc.add(tabla);
        Paragraph pnl1 = new Paragraph(new Phrase("\n"));
        super.doc.add(pnl1);
    }

    private void pintarDetalle(PdfPTable tabla, DetalleOrdenDeCompra detalle) {
        Paragraph paCantidad = new Paragraph();
        paCantidad.add(new Phrase(String.valueOf(detalle.getCantidad()), ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaCantidad = new PdfPCell(paCantidad);
        celdaCantidad.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla.addCell(celdaCantidad);
        
        Paragraph paDescripcion = new Paragraph();
        paDescripcion.add(new Phrase(String.valueOf(detalle.getDescripcion()), ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaDescripcion = new PdfPCell(paDescripcion);
        celdaDescripcion.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla.addCell(celdaDescripcion);
        
        Paragraph paPrecioUnitario = new Paragraph();
        paPrecioUnitario.add(new Phrase(String.valueOf(detalle.getPrecioUnitario()), ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaPrecioUnitario = new PdfPCell(paPrecioUnitario);
        celdaPrecioUnitario.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla.addCell(celdaPrecioUnitario);
        
        double subtotal = detalle.getCantidad()*detalle.getPrecioUnitario();
        Paragraph paSubtotal = new Paragraph();
        paSubtotal.add(new Phrase(String.valueOf(subtotal), ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaSubtotal = new PdfPCell(paSubtotal);
        celdaSubtotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla.addCell(celdaSubtotal);
    }
    
}
