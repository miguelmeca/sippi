/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import controlador.users.UserSession;
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
        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        float[] anchos = {1.1f,9.9f,4f,4f,4f};
        tabla.setWidths(anchos);
        
        Paragraph paNumero = new Paragraph();
        paNumero.add(new Phrase("Nro.", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaNumero = new PdfPCell(paNumero);
        celdaNumero.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaNumero.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaNumero);
        
        Paragraph paDescripcion = new Paragraph();
        paDescripcion.add(new Phrase("Descripción", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaDescripcion = new PdfPCell(paDescripcion);
        celdaDescripcion.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaDescripcion.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaDescripcion);
        
        Paragraph paCantidad = new Paragraph();
        paCantidad.add(new Phrase("Cantidad", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaCantidad = new PdfPCell(paCantidad);
        celdaCantidad.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaCantidad.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaCantidad);
        
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
        for(int i=0; i< this.odc.getDetalle().size();i++)
        {
            DetalleOrdenDeCompra detalle = this.odc.getDetalle().get(i);
            this.pintarDetalle(tabla, detalle, i+1);
            total += detalle.getCantidad()* detalle.getPrecioUnitario();
        }
        if(total != 0){
            tabla.addCell("");tabla.addCell("");tabla.addCell("");
            PdfPCell celdaTituloTotal = new PdfPCell(new Paragraph("Total: ",ReportDesigner.FUENTE_NORMAL_B));
            celdaTituloTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla.addCell(celdaTituloTotal);
            PdfPCell celdaTotal = new PdfPCell(new Paragraph("$ "+String.valueOf(total),ReportDesigner.FUENTE_NORMAL_B));
            celdaTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            celdaTotal.setBackgroundColor(new BaseColor(194,214,155));
            tabla.addCell(celdaTotal);
        }
        
        super.doc.add(tabla);
        Paragraph pnl1 = new Paragraph(new Phrase("\n"));
        super.doc.add(pnl1);
        
        
        PdfPTable tablaPie = new PdfPTable(3);
        tablaPie.setWidthPercentage(100);
        
        Paragraph paElaboradoPor = new Paragraph();
        paElaboradoPor.add(new Phrase("Elaborado Por: "+UserSession.getInstance().getUsuarioLogeado().getUsuario(), ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaElaboradoPor = new PdfPCell(paElaboradoPor);
        celdaElaboradoPor.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaElaboradoPor.setBorder(0);
        tablaPie.addCell(celdaElaboradoPor);
        
        Paragraph paAutorizadoPor = new Paragraph();
        paAutorizadoPor.add(new Phrase("Autorizado Por: ________________", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaAutorizadoPor = new PdfPCell(paAutorizadoPor);
        celdaAutorizadoPor.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaAutorizadoPor.setBorder(0);
        tablaPie.addCell(celdaAutorizadoPor);
        
        Paragraph paRecibidoPor = new Paragraph();
        paRecibidoPor.add(new Phrase("Recibido Por: ________________", ReportDesigner.FUENTE_NORMAL_B));
        PdfPCell celdaRecibidoPor = new PdfPCell(paRecibidoPor);
        celdaRecibidoPor.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaRecibidoPor.setBorder(0);
        tablaPie.addCell(celdaRecibidoPor);
            
        super.doc.add(tablaPie);
    }

    private void pintarDetalle(PdfPTable tabla, DetalleOrdenDeCompra detalle, int index) {
        Paragraph paNumero = new Paragraph();
        paNumero.add(new Phrase(String.valueOf(index), ReportDesigner.FUENTE_NORMAL));
        PdfPCell celdaNumero = new PdfPCell(paNumero);
        celdaNumero.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla.addCell(celdaNumero);
        
        Paragraph paDescripcion = new Paragraph();
        paDescripcion.add(new Phrase(String.valueOf(detalle.getDescripcion()), ReportDesigner.FUENTE_NORMAL));
        PdfPCell celdaDescripcion = new PdfPCell(paDescripcion);
        celdaDescripcion.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla.addCell(celdaDescripcion);
        
        Paragraph paCantidad = new Paragraph();
        paCantidad.add(new Phrase(String.valueOf(detalle.getCantidad()), ReportDesigner.FUENTE_NORMAL));
        PdfPCell celdaCantidad = new PdfPCell(paCantidad);
        celdaCantidad.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla.addCell(celdaCantidad);
        
        Paragraph paPrecioUnitario = new Paragraph();
        paPrecioUnitario.add(new Phrase("$ "+String.valueOf(detalle.getPrecioUnitario()), ReportDesigner.FUENTE_NORMAL));
        PdfPCell celdaPrecioUnitario = new PdfPCell(paPrecioUnitario);
        celdaPrecioUnitario.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla.addCell(celdaPrecioUnitario);
        
        double subtotal = detalle.getCantidad()*detalle.getPrecioUnitario();
        Paragraph paSubtotal = new Paragraph();
        paSubtotal.add(new Phrase("$ "+String.valueOf(subtotal), ReportDesigner.FUENTE_NORMAL));
        PdfPCell celdaSubtotal = new PdfPCell(paSubtotal);
        celdaSubtotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla.addCell(celdaSubtotal);
    }
    
}
