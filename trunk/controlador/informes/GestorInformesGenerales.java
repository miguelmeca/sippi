package controlador.informes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.PedidoObra;
import util.FechaUtil;
import util.HibernateUtil;
import vista.reportes.ReportDesigner;
import vista.reportes.sources.InformeGeneralDeGanancias;

/**
 *
 * @author Iuga
 */
public class GestorInformesGenerales{
    
    private Date fechaInicio;
    private Date fechaFin;
    private HashMap<String,Object> datos;

    public GestorInformesGenerales(Date fechaInicio, Date fechaFin) {
        this.datos = new HashMap<String,Object>();
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.datos.put("INFORME_FECHA_INICIO",this.fechaInicio);
        this.datos.put("INFORME_FECHA_FIN",this.fechaFin);
    }
    
    public void generarInformeGanancias() throws Exception{
        
        List<PedidoObra> listaObras = null;
        try
        {
            HibernateUtil.beginTransaction();
            listaObras = (List)HibernateUtil.getSession().createQuery("FROM PedidoObra PO WHERE PO.estado = :eName").setParameter("eName", PedidoObra.ESTADO_FINALIZADO).list();
            HibernateUtil.commitTransaction();
        }
        catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error:"+e.getMessage());
            throw new Exception("No se pudo cargar correctamente el listado de Obras",e);
        }
        
        // Valido que haya obras
        List<PedidoObra> listaObrasFiltradas = new ArrayList<PedidoObra>();
        for (int i = 0; i < listaObras.size(); i++) {
            PedidoObra po = listaObras.get(i);
            
            if(FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicio, fechaFin)){
                listaObrasFiltradas.add(po);
            }
        }
        datos.put("LISTA_PEDIDOS_OBRA", listaObrasFiltradas);
        
        if(listaObrasFiltradas.isEmpty()){
            throw new Exception("No hay obras finalizadas en el período de tiempo ingresado");
        }
        
        InformeGeneralDeGanancias reporte = new InformeGeneralDeGanancias();
        try {
            reporte.setNombreReporte("Montos Cotizados VS Montos Ejecutados");
            reporte.setNombreArchivo("MontosCotizadosVSEjecutados",ReportDesigner.REPORTE_TIPO_OTROS);
            reporte.makeAndShow(datos);
        } catch (Exception ex) {
            System.err.println("Error al generar el reporte");
            throw new Exception("Se produjo un error al generar el Reporte",ex);
        }
    }
    
}
