/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.reportes.sources;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import modelo.Material;
import modelo.PrecioSegunCantidad;
import modelo.RecursoEspecifico;
import modelo.SubObra;
import modelo.SubObraXAdicional;
import modelo.SubObraXAlquilerCompra;
import modelo.SubObraXHerramienta;
import modelo.SubObraXMaterial;
import modelo.SubObraXTarea;
import util.FechaUtil;
import util.RecursosUtil;
import vista.reportes.ReportDesigner;

/**
 *
 * @author Iuga
 */
public class CotizacionExternaXRecurso extends ReportDesigner{

    private int cotizacion_id;
    
    public CotizacionExternaXRecurso(int cot_id) throws FileNotFoundException, DocumentException
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

        // Tablas de Recursos y Cabeceras
        PdfPTable tablaManoDeObra = new PdfPTable(7);
        PdfPTable tablaMateriales = new PdfPTable(5);
        PdfPTable tablaHerramientas = new PdfPTable(4);
        PdfPTable tablaCompras = new PdfPTable(4);
        PdfPTable tablaAdicionales = new PdfPTable(4);

        tablaManoDeObra.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tablaManoDeObra.setWidthPercentage(95);
        PdfPCell celdaMOT = new PdfPCell(new Paragraph("Mano de Obra",ReportDesigner.FUENTE_NORMAL_BK));
        celdaMOT.setPaddingLeft(0);
        celdaMOT.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaMOT.setBackgroundColor(new BaseColor(219,229,241));
        celdaMOT.setColspan(7);
        tablaManoDeObra.addCell(celdaMOT);
        tablaManoDeObra.addCell(new Paragraph("Tarea",ReportDesigner.FUENTE_NORMAL_BK));
        tablaManoDeObra.addCell(new Paragraph("Cant. Operarios",ReportDesigner.FUENTE_NORMAL_BK));
        tablaManoDeObra.addCell(new Paragraph("Rango Empleado",ReportDesigner.FUENTE_NORMAL_BK));
        tablaManoDeObra.addCell(new Paragraph("Cant. Horas",ReportDesigner.FUENTE_NORMAL_BK));
        tablaManoDeObra.addCell(new Paragraph("Fecha Inicio",ReportDesigner.FUENTE_NORMAL_BK));
        tablaManoDeObra.addCell(new Paragraph("Fecha Fin",ReportDesigner.FUENTE_NORMAL_BK));
        tablaManoDeObra.addCell(new Paragraph("Subtotal",ReportDesigner.FUENTE_NORMAL_BK));


        tablaMateriales.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tablaMateriales.setWidthPercentage(95);
        PdfPCell celdaMat = new PdfPCell(new Paragraph("Materiales",ReportDesigner.FUENTE_NORMAL_BK));
        celdaMat.setPaddingLeft(0);
        celdaMat.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaMat.setBackgroundColor(new BaseColor(219,229,241));
        celdaMat.setColspan(5);
        tablaMateriales.addCell(celdaMat);
        tablaMateriales.addCell(new Paragraph("Cantidad",ReportDesigner.FUENTE_NORMAL_BK));
        tablaMateriales.addCell(new Paragraph("Material",ReportDesigner.FUENTE_NORMAL_BK));
        tablaMateriales.addCell(new Paragraph("Detalle",ReportDesigner.FUENTE_NORMAL_BK));
        tablaMateriales.addCell(new Paragraph("Precio",ReportDesigner.FUENTE_NORMAL_BK));
        tablaMateriales.addCell(new Paragraph("Subtotal",ReportDesigner.FUENTE_NORMAL_BK));

        tablaHerramientas.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tablaHerramientas.setWidthPercentage(95);
        PdfPCell celdaH = new PdfPCell(new Paragraph("Herramientas",ReportDesigner.FUENTE_NORMAL_BK));
        celdaH.setPaddingLeft(0);
        celdaH.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaH.setBackgroundColor(new BaseColor(219,229,241));
        celdaH.setColspan(4);
        tablaHerramientas.addCell(celdaH);
        tablaHerramientas.addCell(new Paragraph("Herramienta",ReportDesigner.FUENTE_NORMAL_BK));
//        tablaHerramientas.addCell(new Paragraph("Cantidad de Días",ReportDesigner.FUENTE_NORMAL_BK));
        tablaHerramientas.addCell(new Paragraph("Cantidad de Horas",ReportDesigner.FUENTE_NORMAL_BK));
        tablaHerramientas.addCell(new Paragraph("Costo por Hora",ReportDesigner.FUENTE_NORMAL_BK));
        tablaHerramientas.addCell(new Paragraph("Subtotal",ReportDesigner.FUENTE_NORMAL_BK));

        tablaCompras.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tablaCompras.setWidthPercentage(95);
        PdfPCell celdaAC = new PdfPCell(new Paragraph("Alquileres / Compras",ReportDesigner.FUENTE_NORMAL_BK));
        celdaAC.setPaddingLeft(0);
        celdaAC.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaAC.setBackgroundColor(new BaseColor(219,229,241));
        celdaAC.setColspan(4);
        tablaCompras.addCell(celdaAC);
        tablaCompras.addCell(new Paragraph("Cantidad",ReportDesigner.FUENTE_NORMAL_BK));
        tablaCompras.addCell(new Paragraph("Descripción",ReportDesigner.FUENTE_NORMAL_BK));
        tablaCompras.addCell(new Paragraph("Precio Unitario",ReportDesigner.FUENTE_NORMAL_BK));
        tablaCompras.addCell(new Paragraph("Subtotal",ReportDesigner.FUENTE_NORMAL_BK));

        tablaAdicionales.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        tablaAdicionales.setWidthPercentage(95);
        PdfPCell celdaAdi = new PdfPCell(new Paragraph("Gastos Generales",ReportDesigner.FUENTE_NORMAL_BK));
        celdaAdi.setPaddingLeft(0);
        celdaAdi.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celdaAdi.setBackgroundColor(new BaseColor(219,229,241));
        celdaAdi.setColspan(4);
        tablaAdicionales.addCell(celdaAdi);
        tablaAdicionales.addCell(new Paragraph("Descripción",ReportDesigner.FUENTE_NORMAL_BK));
        tablaAdicionales.addCell(new Paragraph("Cantidad",ReportDesigner.FUENTE_NORMAL_BK));
        tablaAdicionales.addCell(new Paragraph("Precio Unitario",ReportDesigner.FUENTE_NORMAL_BK));
        tablaAdicionales.addCell(new Paragraph("Subtotal",ReportDesigner.FUENTE_NORMAL_BK));

        // ARMADO DE TABLAS
        List listaSubObras = (List) params.get("LISTA_SUB_OBRAS");
        for (int i = 0; i < listaSubObras.size(); i++){
            SubObra so = (SubObra) listaSubObras.get(i);
            double subtotal = 0;

            // Mano de Obra
            Iterator<SubObraXTarea> itMO = so.getTareas().iterator();
            while(itMO.hasNext()){
                SubObraXTarea soxt = itMO.next();
                tablaManoDeObra.addCell(new PdfPCell(new Paragraph(soxt.getNombre(),ReportDesigner.FUENTE_NORMAL)));
                tablaManoDeObra.addCell(new PdfPCell(new Paragraph(soxt.getTipoTarea().getNombre(),ReportDesigner.FUENTE_NORMAL)));
                tablaManoDeObra.addCell(new PdfPCell(new Paragraph(String.valueOf(soxt.obtenerTotalDePersonas()),ReportDesigner.FUENTE_NORMAL)));
                tablaManoDeObra.addCell(new PdfPCell(new Paragraph(String.valueOf(soxt.obtenerTotalDeHorasNormales()),ReportDesigner.FUENTE_NORMAL)));
                tablaManoDeObra.addCell(new PdfPCell(new Paragraph(String.valueOf(soxt.obtenerTotalDeHorasAl50()),ReportDesigner.FUENTE_NORMAL)));
                tablaManoDeObra.addCell(new PdfPCell(new Paragraph(String.valueOf(soxt.obtenerTotalDeHorasAl100()),ReportDesigner.FUENTE_NORMAL)));
                tablaManoDeObra.addCell(new PdfPCell(new Paragraph("$"+String.valueOf(soxt.calcularSubtotal()),ReportDesigner.FUENTE_NORMAL)));
                subtotal += soxt.calcularSubtotal();
            }
            if(subtotal != 0){
                tablaManoDeObra.addCell("");tablaManoDeObra.addCell("");tablaManoDeObra.addCell("");
                tablaManoDeObra.addCell("");tablaManoDeObra.addCell("");tablaManoDeObra.addCell("");
                PdfPCell celdaSubTotal = new PdfPCell(new Paragraph("$"+String.valueOf(subtotal),ReportDesigner.FUENTE_NORMAL_B));
                celdaSubTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                celdaSubTotal.setBackgroundColor(new BaseColor(194,214,155));
                tablaManoDeObra.addCell(celdaSubTotal);
                subtotal = 0;
            }

            //Materiales
            Iterator<SubObraXMaterial> itMat = so.getMateriales().iterator();
            while(itMat.hasNext()){
                SubObraXMaterial soxm = itMat.next();
                tablaMateriales.addCell(new PdfPCell(new Paragraph(String.valueOf(soxm.getCantidad()),ReportDesigner.FUENTE_NORMAL)));
                RecursoEspecifico re = RecursosUtil.getRecursoEspecifico(soxm.getMaterial());
                Material m = (Material)RecursosUtil.getMaterial(re);
                tablaMateriales.addCell(new PdfPCell(new Paragraph(m.getNombre(),ReportDesigner.FUENTE_NORMAL)));
                tablaMateriales.addCell(new PdfPCell(new Paragraph(re.getNombre(),ReportDesigner.FUENTE_NORMAL)));
                tablaMateriales.addCell(new PdfPCell(new Paragraph("$"+String.valueOf(soxm.getPrecioUnitario()),ReportDesigner.FUENTE_NORMAL)));
                tablaMateriales.addCell(new PdfPCell(new Paragraph("$"+String.valueOf(soxm.calcularSubtotal()),ReportDesigner.FUENTE_NORMAL)));
                subtotal += soxm.calcularSubtotal();
            }
            if(subtotal != 0){
                tablaMateriales.addCell("");tablaMateriales.addCell("");tablaMateriales.addCell("");
                tablaMateriales.addCell("");
                PdfPCell celdaSubTotal = new PdfPCell(new Paragraph("$"+String.valueOf(subtotal),ReportDesigner.FUENTE_NORMAL_B));
                celdaSubTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                celdaSubTotal.setBackgroundColor(new BaseColor(194,214,155));
                tablaMateriales.addCell(celdaSubTotal);
                subtotal = 0;
            }

            // Herramientas
            Iterator<SubObraXHerramienta> itH = so.getHerramientas().iterator();
            while(itH.hasNext()){
                SubObraXHerramienta soxh = itH.next();
                String nombreH = soxh.getHerramienta().getRecursoEsp().getNombre()+" "+soxh.getHerramienta().getRecursoEsp().getRecurso().getNombre()+" ("+soxh.getHerramienta().getNroSerie()+")";
                tablaHerramientas.addCell(new PdfPCell(new Paragraph(nombreH,ReportDesigner.FUENTE_NORMAL)));
//                tablaHerramientas.addCell(new PdfPCell(new Paragraph(String.valueOf(soxh.getCantDias()),ReportDesigner.FUENTE_NORMAL)));
                tablaHerramientas.addCell(new PdfPCell(new Paragraph(String.valueOf(soxh.getCantHoras()),ReportDesigner.FUENTE_NORMAL)));
                tablaHerramientas.addCell(new PdfPCell(new Paragraph("$"+String.valueOf(soxh.getCostoXHora()),ReportDesigner.FUENTE_NORMAL)));
                tablaHerramientas.addCell(new PdfPCell(new Paragraph("$"+String.valueOf(soxh.calcularSubtotal()),ReportDesigner.FUENTE_NORMAL)));
                subtotal += soxh.calcularSubtotal();
            }
            if(subtotal != 0){
                tablaHerramientas.addCell("");tablaHerramientas.addCell("");tablaHerramientas.addCell("");
                PdfPCell celdaSubTotal = new PdfPCell(new Paragraph("$"+String.valueOf(subtotal),ReportDesigner.FUENTE_NORMAL_B));
                celdaSubTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                celdaSubTotal.setBackgroundColor(new BaseColor(194,214,155));
                tablaHerramientas.addCell(celdaSubTotal);
                subtotal = 0;
            }

            // Alquileres / Compras
            Iterator<SubObraXAlquilerCompra> itAC = so.getAlquileresCompras().iterator();
            while(itAC.hasNext()){
                SubObraXAlquilerCompra soxac = itAC.next();
                tablaCompras.addCell(new PdfPCell(new Paragraph(String.valueOf(soxac.getCantidad()),ReportDesigner.FUENTE_NORMAL)));
                tablaCompras.addCell(new PdfPCell(new Paragraph(soxac.getDescripcion(),ReportDesigner.FUENTE_NORMAL)));
                tablaCompras.addCell(new PdfPCell(new Paragraph("$"+String.valueOf(soxac.getPrecioUnitario()),ReportDesigner.FUENTE_NORMAL)));
                tablaCompras.addCell(new PdfPCell(new Paragraph("$"+String.valueOf(soxac.calcularSubtotal()),ReportDesigner.FUENTE_NORMAL)));
                subtotal += soxac.calcularSubtotal();
            }
            if(subtotal != 0){
                tablaCompras.addCell("");tablaCompras.addCell("");tablaCompras.addCell("");
                PdfPCell celdaSubTotal = new PdfPCell(new Paragraph("$"+String.valueOf(subtotal),ReportDesigner.FUENTE_NORMAL_B));
                celdaSubTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                celdaSubTotal.setBackgroundColor(new BaseColor(194,214,155));
                tablaCompras.addCell(celdaSubTotal);
                subtotal = 0;
            }


            // Adicionales
            Iterator<SubObraXAdicional> itA = so.getAdicionales().iterator();
            while(itA.hasNext()){
                SubObraXAdicional soxa = itA.next();
                tablaAdicionales.addCell(new PdfPCell(new Paragraph(soxa.getTipoAdicional().getNombre()+" - "+soxa.getDescripcion(),ReportDesigner.FUENTE_NORMAL)));
                tablaAdicionales.addCell(new PdfPCell(new Paragraph(String.valueOf(soxa.getCantidad()),ReportDesigner.FUENTE_NORMAL)));
                tablaAdicionales.addCell(new PdfPCell(new Paragraph("$"+String.valueOf(soxa.getPrecioUnitario()),ReportDesigner.FUENTE_NORMAL)));
                tablaAdicionales.addCell(new PdfPCell(new Paragraph("$"+String.valueOf(soxa.calcularSubtotal()),ReportDesigner.FUENTE_NORMAL)));
                subtotal += soxa.calcularSubtotal();
            }
            
            if(subtotal != 0){
                tablaAdicionales.addCell("");tablaAdicionales.addCell("");tablaAdicionales.addCell("");
                PdfPCell celdaSubTotal = new PdfPCell(new Paragraph("$"+String.valueOf(subtotal),ReportDesigner.FUENTE_NORMAL_B));
                celdaSubTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                celdaSubTotal.setBackgroundColor(new BaseColor(194,214,155));
                tablaAdicionales.addCell(celdaSubTotal);
                subtotal = 0;
            }
        }
        Paragraph pnl = new Paragraph(new Phrase("\n"));
        super.doc.add(tablaManoDeObra);
        super.doc.add(pnl);
        super.doc.add(tablaMateriales);
        super.doc.add(pnl);
        super.doc.add(tablaHerramientas);
        super.doc.add(pnl);
        super.doc.add(tablaCompras);
        super.doc.add(pnl);
        super.doc.add(tablaAdicionales);
        super.doc.add(pnl);

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

        super.doc.add(pnl);

        // DATOS GENERALES
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
