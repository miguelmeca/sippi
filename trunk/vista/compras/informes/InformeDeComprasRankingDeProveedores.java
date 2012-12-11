/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.compras.informes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import modelo.*;
import org.apache.commons.collections.IterableMap;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LinkedMap;
import sun.swing.SwingUtilities2;
import util.GenericFilaConDetalles;
import vista.reportes.ReportDesigner;



/**
 *
 * @author Administrador
 */
public class InformeDeComprasRankingDeProveedores extends InformeDeCompras{

    public static final String PARAM_FILTRO_PROVEEDOR = "Ordenar Proveedores";
    public static final String PARAM_FILTRO_PRODUCTOS = "Ordenar Productos";
    public static final String FILTRO_PRODUCTOS_MONTO = "Por Monto";
    public static final String FILTRO_PRODUCTOS_CANTIDAD = "Por Cantidad";
    
    private HashMap<Proveedor,GenericFilaConDetalles<Proveedor, Number , HashMap>> proveedores;
    private Map<Object, Double> map;
    

    
    public InformeDeComprasRankingDeProveedores(HashMap<Proveedor,GenericFilaConDetalles<Proveedor, Number , HashMap>> filas) {
        super();
        this.proveedores = filas;
    }
  
    @Override
    protected void makeCuerpo(HashMap<String,Object> params) throws DocumentException
    {
        makeCabecera(params);
        String filtroProducto = (String)params.get(InformeDeComprasRankingDeProveedores.PARAM_FILTRO_PRODUCTOS);
        Paragraph pnEspacio = new Paragraph(new Phrase("\n"));
        super.doc.add(pnEspacio);
        
        PdfPTable tabla = new PdfPTable(2);
        
        PdfPCell celdaNombre = new PdfPCell();
        celdaNombre.addElement(new Paragraph("Proveedor",ReportDesigner.FUENTE_NORMAL_B));
        celdaNombre.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        celdaNombre.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaNombre.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaNombre);
        
        PdfPCell celdaMonto = new PdfPCell();
        if(filtroProducto.equals(this.FILTRO_PRODUCTOS_CANTIDAD))
        {
            celdaMonto.addElement(new Paragraph(" Cantidad de Compras Realizadas",ReportDesigner.FUENTE_NORMAL_B));
        }
        else
        {
            if(filtroProducto.equals(this.FILTRO_PRODUCTOS_MONTO))
            {
                celdaMonto.addElement(new Paragraph(" Monto Comprado",ReportDesigner.FUENTE_NORMAL_B));
            }
        }
        celdaMonto.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        celdaMonto.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaMonto.setBackgroundColor(new BaseColor(219,229,241));
        tabla.addCell(celdaMonto);
        
        SupplierValueComparator sbvc =  new SupplierValueComparator(this.proveedores);
        TreeMap<Proveedor,GenericFilaConDetalles<Proveedor, Number , HashMap>> supplier_sorted_map = new TreeMap<Proveedor,GenericFilaConDetalles<Proveedor, Number , HashMap>>(sbvc);
        supplier_sorted_map.putAll(this.proveedores);
        Iterator itProveedoresOrdenados = supplier_sorted_map.entrySet().iterator();
        while(itProveedoresOrdenados.hasNext())
        {
            Map.Entry entrySupplier = (Map.Entry) itProveedoresOrdenados.next();
            GenericFilaConDetalles<Proveedor, Number , HashMap> proveedor = (GenericFilaConDetalles<Proveedor, Number , HashMap>) entrySupplier.getValue();
            
            PdfPCell celdaNombreProveedor = new PdfPCell(new Paragraph(proveedor.getEntidad().getRazonSocial(),ReportDesigner.FUENTE_NORMAL));
            celdaNombreProveedor.setBackgroundColor(new BaseColor(194,214,155));
            celdaNombreProveedor.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            tabla.addCell(celdaNombreProveedor);
            
            PdfPCell celdaMontoProveedor = null;
            if(filtroProducto.equals(InformeDeComprasRankingDeProveedores.FILTRO_PRODUCTOS_CANTIDAD)) {
                celdaMontoProveedor = new PdfPCell(new Paragraph(proveedor.getDato().toString(),ReportDesigner.FUENTE_NORMAL));
            }
            else
                if(filtroProducto.equals(InformeDeComprasRankingDeProveedores.FILTRO_PRODUCTOS_MONTO)) {
                    celdaMontoProveedor = new PdfPCell(new Paragraph("$ " + proveedor.getDato(),ReportDesigner.FUENTE_NORMAL));
            }
            celdaMontoProveedor.setBackgroundColor(new BaseColor(194,214,155));
            celdaMontoProveedor.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            tabla.addCell(celdaMontoProveedor);
            
            // Detalle del Proveedor
            PdfPCell celdaTituloProductos = new PdfPCell(new Paragraph("Detalle Productos",ReportDesigner.FUENTE_NORMAL_B));
            celdaTituloProductos.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            celdaTituloProductos.setBackgroundColor(new BaseColor(214,227,188));
            celdaTituloProductos.setColspan(2);
            tabla.addCell(celdaTituloProductos);
            
            PdfPCell celdaProductosProveedor = new PdfPCell();
            celdaProductosProveedor.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            celdaProductosProveedor.setColspan(2);
            
            List list1 = new List(List.ORDERED, 20);
            ProductValueComparator bvc =  new ProductValueComparator(proveedor.getDetalles());
            TreeMap<Object,Double> sorted_map = new TreeMap<Object,Double>(bvc);
            sorted_map.putAll(proveedor.getDetalles());
            Iterator itDetallesOrdenados = sorted_map.entrySet().iterator();
            while(itDetallesOrdenados.hasNext())
            {
                String nombre = "";
                Map.Entry entry = (Map.Entry) itDetallesOrdenados.next();
                Object key = entry.getKey();
                String unidadMedida = "";
                if(key instanceof RecursoEspecifico)
                {
                    nombre = ((RecursoEspecifico)key).getNombreRecurso() + " - " + ((RecursoEspecifico)key).getNombre();
                    unidadMedida = ((RecursoEspecifico)key).getRecurso().getUnidadDeMedida().getAbreviatura();
                }
                if(key instanceof TipoAlquilerCompra)
                {
                    nombre = ((TipoAlquilerCompra)key).getNombre();
                    unidadMedida = "un.";
                }
                if(key instanceof TipoAdicional)
                {
                    nombre = ((TipoAdicional)key).getNombre();
                    unidadMedida = "un.";
                }
                Object value = entry.getValue();
                
                if(filtroProducto.equals(this.FILTRO_PRODUCTOS_CANTIDAD))
                {
                    list1.add(new ListItem(nombre+" ( "+value + " " + unidadMedida + " )"));
                }
                else
                {
                    if(filtroProducto.equals(this.FILTRO_PRODUCTOS_MONTO))
                    {
                        list1.add(new ListItem(nombre+" ( $ "+value +" )"));
                    }
                }
            }
            
            celdaProductosProveedor.addElement(list1);
            
            celdaProductosProveedor.addElement(new Paragraph("\n"));
            
            tabla.addCell(celdaProductosProveedor);
        }
        super.doc.add(tabla);
    }
}

class SupplierValueComparator implements Comparator<Object> {

    Map<Proveedor, GenericFilaConDetalles<Proveedor, Number , HashMap>> base;
    public SupplierValueComparator(Map<Proveedor, GenericFilaConDetalles<Proveedor, Number , HashMap>> base) {
        this.base = base;
    }
  
    @Override
    public int compare(Object a, Object b) {
        if ((Double)base.get(a).getDato() >= (Double)base.get(b).getDato()) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}

class ProductValueComparator implements Comparator<Object> {

    Map<Object, Double> base;
    public ProductValueComparator(Map<Object, Double> base) {
        this.base = base;
    }
  
    @Override
    public int compare(Object a, Object b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}