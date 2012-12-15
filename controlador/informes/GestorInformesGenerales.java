package controlador.informes;

import controlador.utiles.gestorBDvarios;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import modelo.Cotizacion;
import modelo.EmpresaCliente;
import modelo.PedidoObra;
import org.jfree.data.category.DefaultCategoryDataset;
import util.FechaUtil;
import util.HibernateUtil;
import vista.reportes.ReportDesigner;
import vista.reportes.sources.InformeCantidadCotizacionesRechazadas;
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
    
    public void generarInformeGanancias(int years) throws Exception{
        
        datos.put("YEARS_HISTORICO", years);
        
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
        
        // Historicos
        // Cotizado | Ejecutado | Ganancia
        DefaultCategoryDataset gananciasHistoricas = new DefaultCategoryDataset(); 
        
        Date fechaInicioHistoricos = getFechaDesdeHistoricos(fechaFin,years);
        for (int i = 0; i < listaObras.size(); i++) {
                PedidoObra po = listaObras.get(i);

                if(FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicioHistoricos, fechaFin) 
                        && po.getEstado().equals(PedidoObra.ESTADO_FINALIZADO))
                { 
                    int yearObra = FechaUtil.getYearFromDate(po.getFechaInicio());
                    double ganancia = po.calcularGanancia();
                    double cotizado = po.getPlanificacion().getCotizacion().getCotizacionOriginal().CalcularTotal();
                    double ejecutado = po.getEjecucion().calcularMontoTotal();
                    
                    // --------------------------------------------------
                    // GANANCIAS POR AÑO
                    // --------------------------------------------------
                    gananciasHistoricas.addValue(cotizado,"Montos Cotizados",""+yearObra);
                    gananciasHistoricas.addValue(ejecutado,"Montos Ejecutados",""+yearObra);
                    gananciasHistoricas.addValue(ganancia,"Diferencia",""+yearObra);
                    
                    // --------------------------------------------------
                }
        }

         datos.put("GANANCIAS_HISTORICAS", gananciasHistoricas);
        
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
    
    public void generarInformeCantidadCotizacionesRechazadas(int cantidadYears)throws Exception{
       
        // Cotizaciones comunes
        List<PedidoObra> listaObras = null;
        try
        {
            HibernateUtil.beginTransaction();
            listaObras = (List)HibernateUtil.getSession().createQuery("FROM PedidoObra").list();
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
            
            if(FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicio, fechaFin) 
                    && !po.getEstado().equals(PedidoObra.ESTADO_CANCELADO))
            {
                listaObrasFiltradas.add(po);
            }
        }
    
        if(listaObrasFiltradas.isEmpty()){
            throw new Exception("No hay obras finalizadas en el período de tiempo ingresado");
        }        
        
        datos.put("LISTA_PEDIDOS_OBRA", listaObrasFiltradas);
        datos.put("ANIOS_HISTORICO", cantidadYears);
        
        // Historico
        Date fechaInicioHistoricos = getFechaDesdeHistoricos(fechaFin,cantidadYears);
        
            HashMap<String,Integer> listaObrasHistorico = new HashMap<String,Integer>();
            HashMap<String,Double> listaRechazadoCliente = new HashMap<String,Double>();
            
            for (int i = 0; i < listaObras.size(); i++) {
                PedidoObra po = listaObras.get(i);

                if(FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicioHistoricos, fechaFin) 
                        && !po.getEstado().equals(PedidoObra.ESTADO_CANCELADO))
                {   
                    
                    int cantidadRechazadosObra = calcularCantidadCotizacionesRechazadas(po);
                    
                    // =======================================================================
                    // RECHAZADOS X AÑO
                    // =======================================================================
                    // Saco el año de la obra
                    int yearObra = FechaUtil.getYearFromDate(po.getFechaInicio());
                    
                    if(listaObrasHistorico.containsKey(String.valueOf(yearObra))){
                        // SUmo
                        int cantidad = listaObrasHistorico.get(String.valueOf(yearObra));
                        cantidad += cantidadRechazadosObra;
                        listaObrasHistorico.put(String.valueOf(yearObra),cantidad);
                    }else{
                        listaObrasHistorico.put(String.valueOf(yearObra),cantidadRechazadosObra);
                    }
                    
                    // =======================================================================
                    // RECHAZADOS X ClIENTE
                    // =======================================================================
                    // Get del Cliente de la Obra
                    gestorBDvarios util = new gestorBDvarios();
                    
                    String nombre = "";
                    if(po.getPlanta()!=null){
                        EmpresaCliente cliente = util.buscarEmpresaCliente(po.getPlanta());
                        if(cliente!=null){
                            nombre = cliente.getRazonSocial();
                        }
                    }
                    if(listaRechazadoCliente.containsKey(nombre)){
                        double cantidad = listaRechazadoCliente.get(nombre);
                        cantidad += cantidadRechazadosObra;
                        listaRechazadoCliente.put(nombre,cantidad);
                    }else{
                        listaRechazadoCliente.put(nombre,Double.valueOf(cantidadRechazadosObra));
                    }
                    
                }
            } 
        datos.put("OBRAS_HISTORICO",listaObrasHistorico);
        datos.put("HISTORICO_X_CLIENTE",listaRechazadoCliente);
        
        InformeCantidadCotizacionesRechazadas reporte = new InformeCantidadCotizacionesRechazadas();
        try {
            reporte.setNombreReporte("Cantidad de Cotizaciones rechazadas.");
            reporte.setNombreArchivo("CantidadCotizacionesRechazadas",ReportDesigner.REPORTE_TIPO_OTROS);
            reporte.makeAndShow(datos);
        } catch (Exception ex) {
            System.err.println("Error al generar el reporte");
            throw new Exception("Se produjo un error al generar el Reporte",ex);
        }
        
    }

    
    private Date getFechaDesdeHistoricos(Date fechaFin, int cantidadYears){
        // Historico
        // Resto de la fecha de fin la cantidad de años del historico
        int year_fin = FechaUtil.getYearFromDate(fechaFin);
        int year_inicio = year_fin - cantidadYears;

        Calendar cal = GregorianCalendar.getInstance();
        cal.set(year_inicio, Calendar.JANUARY, 1);
        Date inicio = cal.getTime();
        
        return inicio;
    }
    
    private int calcularCantidadCotizacionesRechazadas(PedidoObra po) {
        int cantidadTotal = 0;
        if(po.getCotizaciones()!=null){
            for(int j = 0; j < po.getCotizaciones().size(); j++) {
                Cotizacion cot = po.getCotizaciones().get(j);
                if(cot.getEstado().equals(Cotizacion.ESTADO_RECHAZADO)){
                    cantidadTotal++;
                }
            }
        }
        return cantidadTotal;
    }    
    
}
