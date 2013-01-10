
package controlador.reportes;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.*;
import util.FechaUtil;
import util.GenericFilaConDetalles;
import util.HibernateUtil;
import vista.compras.informes.InformeDeComprasListadoDeProveedoresMasSolicitados;
import vista.reportes.ReportDesigner;

/**
 * @author Iuga
 */
public class GestorReportesCompras {
    
    public static final String REPORTE_PATH  = "/vista/planificacion/reportes/";
    public static final String FILTRO_PROVEEDOR_POR_MONTO = "Por Monto";
    public static final String FILTRO_PROVEEDOR_POR_CANTIDAD = "Por Cantidad";
    public static final String FILTRO_PRODUCTO_POR_MONTO = "Por Monto";
    public static final String FILTRO_PRODUCTO_POR_CANTIDAD = "Por Cantidad";
    

    public GestorReportesCompras() {
    }
    
   /**
    * Método que crea el informe de Ranking de Proveedores ordenándolos por 
    * cantidad o monto y filtrándo los productos de dichos proveedores por monto
    * o cantidad.
    * @param fechaInicio filtro de Fecha de Fin
    * @param fechaFin Filtro de Fecha de inicio
    * @param filtroProveedores Puede ser "Por Monto" o "Por Cantidad"
    * @param filtroProductos Puede ser "Por Monto" o "Por Cantidad"
    */
    public void emitirListadoProveedoresMasSolicitados(Date fechaInicio, Date fechaFin, String filtroProveedores, String filtroProductos, int cantProductosXProveedor) throws Exception
    {    
        HashMap<Proveedor,GenericFilaConDetalles<Proveedor, Number , HashMap>> filas = this.getProveedoresRankeados(fechaInicio, fechaFin, filtroProveedores, filtroProductos, cantProductosXProveedor);
        
        if(filas.size()> 0)
        {
            HashMap<String,Object> params = new HashMap<String, Object>();

            params.put(InformeDeComprasListadoDeProveedoresMasSolicitados.PARAM_FILTRO_FECHAINICIO,FechaUtil.getFecha(fechaInicio));
            params.put(InformeDeComprasListadoDeProveedoresMasSolicitados.PARAM_FILTRO_FECHAFIN, FechaUtil.getFecha(fechaFin));
            params.put(InformeDeComprasListadoDeProveedoresMasSolicitados.PARAM_FILTRO_PROVEEDOR, filtroProveedores);
            params.put(InformeDeComprasListadoDeProveedoresMasSolicitados.PARAM_FILTRO_PRODUCTOS, filtroProductos);

            InformeDeComprasListadoDeProveedoresMasSolicitados informe = new InformeDeComprasListadoDeProveedoresMasSolicitados(filas);
            informe.setNombreReporte("Informe de Proveedores Más Solicitados");
            informe.setNombreArchivo("InformeProveedoresMasSolicitados-",
    //                FechaUtil.getFecha(fechaInicio) +
    //                "-" +
    //                FechaUtil.getFecha(fechaFin),
                    ReportDesigner.REPORTE_TIPO_COMPRAS);
            try {
                informe.makeAndShow(params);
            } catch (DocumentException ex) {
                throw new DocumentException("Hubo un error al intentar crear el informe. Por favor, contacte al administrador.");
            } catch (FileNotFoundException ex) {
                throw new FileNotFoundException("No se pudo crear el archivo del informe. Por favor, contacte al administrador.");
            }
        }
        else
        {
            throw new Exception("No existen datos de compras concretadas en el periodo seleccionado.");
        }
    }
    
    private HashMap<Proveedor,GenericFilaConDetalles<Proveedor, Number , HashMap>> getProveedoresRankeados(Date fechaInicio, Date fechaFin, String filtroProveedores, String filtroProductos, int cantProductosXProveedor) throws Exception
    {
        HashMap<Proveedor,GenericFilaConDetalles<Proveedor, Number , HashMap>> filasOrdenadas = new HashMap<Proveedor, GenericFilaConDetalles<Proveedor, Number, HashMap>>();
        
        try {
            HashMap<Proveedor,GenericFilaConDetalles<Proveedor, Number , HashMap>> filas = new HashMap<Proveedor, GenericFilaConDetalles<Proveedor, Number, HashMap>>();
            List<OrdenDeCompra> ordenesDeCompra = HibernateUtil.getSession().createQuery("from OrdenDeCompra").list();
            GenericFilaConDetalles<Proveedor, Number , HashMap> filaProveedor;
            
            // Recorro las Órdenes de Cómpra
            for(int i=0; i< ordenesDeCompra.size(); i++)
            {
                OrdenDeCompra orden = ordenesDeCompra.get(i);
                
                // Si en mi lista ya está el proveedor, utilizo ese generic
                // para actualizar los valores. En caso contrario, creo uno
                // nuevo y lo empiezo a llenar.
                GenericFilaConDetalles<Proveedor, Number , HashMap> filaRepetida = filas.get(orden.getProveedor());
                if(filaRepetida != null)
                {
                    filaProveedor = filaRepetida;
                }
                else
                {
                    filaProveedor = new GenericFilaConDetalles<Proveedor, Number, HashMap>();
                    filaProveedor.setEntidad(orden.getProveedor());
                    filaProveedor.setDato(0d);
                    filas.put(orden.getProveedor(), filaProveedor);
                }
                
                // Recorro todas las recepciones de la Orden de Compra para 
                // poder actualizar los detalles y el monto total del proveedor.
                for(int j=0; j< orden.getRecepciones().size(); j++)
                {
                    RecepcionOrdenDeCompra recepcion = orden.getRecepciones().get(j);
                    
                    // Filtro las recepciones de acuerdo a las fechas de inicio
                    // y fin acordadas por el usuario.
                    if(FechaUtil.fechaMayorOIgualQue(recepcion.getFechaRecepcion(), fechaInicio)
                            && FechaUtil.fechaMayorOIgualQue(fechaFin, recepcion.getFechaRecepcion()))
                    {
                        // Recorro las recepciones parciales de la Recepcion general
                        for(int k=0; k < recepcion.getRecepcionesParciales().size() ; k++)
                        {
                            DetalleRecepcionOrdenDeCompra detalleRecep = recepcion.getRecepcionesParciales().get(k);

                            // Me fijo si existía este item. Si es así, entonces suma
                            // el valor sino lo crea y le setea el costo
                            Object item = detalleRecep.getDetalleOrdenDeCompra().getItem().getItem();
                            Object obj = filaProveedor.quitarDetalle(item);
                            if(obj != null)
                            {
                                Number itemCosto = (Number) obj;
                                // Dependiendo si el filtro era Cantidad o Monto 
                                // determino como hacer el cálculo
                                if(filtroProductos.equals(InformeDeComprasListadoDeProveedoresMasSolicitados.FILTRO_PRODUCTOS_CANTIDAD))
                                {
                                    filaProveedor.agregarDetalle(
                                            item,
                                            (Double)itemCosto + detalleRecep.getCantidad());
                                    filaProveedor.setDato((Double)filaProveedor.getDato() + 1d);
                                }
                                else
                                {
                                    if(filtroProductos.equals(InformeDeComprasListadoDeProveedoresMasSolicitados.FILTRO_PRODUCTOS_MONTO))
                                    {
                                        filaProveedor.agregarDetalle(
                                            item,
                                            (Double)itemCosto + detalleRecep.getDetalleOrdenDeCompra().calcularSubTotal());
                                        filaProveedor.setDato((Double)filaProveedor.getDato() + detalleRecep.getCantidad());
                                    }
                                    else
                                    {
                                        throw new Exception();
                                    }
                                }
                            }
                            else // No estaba en la lista, lo agrego.
                            {
                                // Dependiendo si el filtro era Cantidad o Monto 
                                // determino como hacer el cálculo
                                if(filtroProductos.equals(InformeDeComprasListadoDeProveedoresMasSolicitados.FILTRO_PRODUCTOS_CANTIDAD))
                                {
                                    filaProveedor.agregarDetalle(
                                            item,
                                            detalleRecep.getCantidad());
                                    filaProveedor.setDato((Double)filaProveedor.getDato() + 1d);
                                }
                                else
                                {
                                    if(filtroProductos.equals(InformeDeComprasListadoDeProveedoresMasSolicitados.FILTRO_PRODUCTOS_MONTO))
                                    {
                                        filaProveedor.agregarDetalle(
                                            item,
                                            detalleRecep.getDetalleOrdenDeCompra().calcularSubTotal());
                                        filaProveedor.setDato((Double)filaProveedor.getDato() + detalleRecep.getDetalleOrdenDeCompra().calcularSubTotal());
                                    }
                                    else
                                    {
                                        throw new Exception();
                                    }
                                }
                            }
                        }
                    }
                }
                
            }

            // Filtro la cantidadProductoXProveedor
            Iterator itProveedores = filas.entrySet().iterator();
            while(itProveedores.hasNext())
            {
                HashMap detalleOrdenado = new HashMap();
                Map.Entry proveedorEntry = (Map.Entry) itProveedores.next();
                GenericFilaConDetalles<Proveedor, Number , HashMap> proveedor = 
                        (GenericFilaConDetalles<Proveedor, Number , HashMap>) proveedorEntry.getValue();
                GenericFilaConDetalles<Proveedor, Number , HashMap> proveedorOrdenado = 
                        new GenericFilaConDetalles<Proveedor, Number, HashMap>();
                proveedorOrdenado.setEntidad(proveedor.getEntidad());
                proveedorOrdenado.setDato(proveedor.getDato());
                Iterator itProductos = proveedor.getDetalles().entrySet().iterator();
                boolean proveedorTieneProductos = false;
                while(itProductos.hasNext())
                {
                    proveedorTieneProductos = true;
                    Map.Entry producto = (Map.Entry)itProductos.next();
                    if(detalleOrdenado.size() < cantProductosXProveedor)
                    {
                        detalleOrdenado.put(producto.getKey(), producto.getValue());
                    }
                    else
                    {
                        detalleOrdenado.put(producto.getKey(), producto.getValue());
                        detalleOrdenado.remove(this.getKeyMenorProducto(detalleOrdenado));
                    }
                }
                // Solo lo agrego si tiene productos
                if(proveedorTieneProductos)
                {
                    Proveedor provKey = (Proveedor) proveedorEntry.getKey();
                    proveedorOrdenado.setDetalles(detalleOrdenado);
                    filasOrdenadas.put(provKey, proveedorOrdenado);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        }
        
        return filasOrdenadas;
    }
    
    private Object getKeyMenorProducto(HashMap hashMap)
    {
        Object keyMenor = null;
        Double valorMenor = 0d;
        Iterator itProductos = hashMap.entrySet().iterator();
        while(itProductos.hasNext())
        {
            Map.Entry producto = (Map.Entry) itProductos.next();
            if(valorMenor.equals(0d))
            {
                valorMenor = (Double) producto.getValue();
                keyMenor = (Object) producto.getKey();
            }
            else
            {
                Double valor = (Double) producto.getValue();
                if(valor < valorMenor)
                {
                    valorMenor = valor;
                    keyMenor = (Object) producto.getKey();
                }
            }
        }

        return keyMenor;
    }
}
