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
import modelo.Planta;
import modelo.SubObra;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jfree.data.category.DefaultCategoryDataset;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;
import vista.reportes.ReportDesigner;
import vista.reportes.sources.InformPorcentajeDeBeneficiosSegunAceptacionDeCotizaciones;
import vista.reportes.sources.InformeCantidadCotizacionesRechazadas;
import vista.reportes.sources.InformeCompletoControl;
import vista.reportes.sources.InformeGeneralDeGanancias;
import vista.reportes.sources.InformeObrasPorAnio;

/**
 *
 * @author Iuga
 */
public class GestorInformesGenerales {

    private Date fechaInicio;
    private Date fechaFin;
    private HashMap<String, Object> datos;

    public GestorInformesGenerales(Date fechaInicio, Date fechaFin) {
        this.datos = new HashMap<String, Object>();
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.datos.put("INFORME_FECHA_INICIO", this.fechaInicio);
        this.datos.put("INFORME_FECHA_FIN", this.fechaFin);
    }

    public void generarInformeGanancias(int years) throws Exception {

        datos.put("YEARS_HISTORICO", years);

        List<PedidoObra> listaObras = null;
        try {
            HibernateUtil.beginTransaction();
            listaObras = (List) HibernateUtil.getSession().createQuery("FROM PedidoObra PO WHERE PO.estado = :eName").setParameter("eName", PedidoObra.ESTADO_FINALIZADO).list();
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error:" + e.getMessage());
            throw new Exception("No se pudo cargar correctamente el listado de Obras", e);
        }

        // Valido que haya obras
        List<PedidoObra> listaObrasFiltradas = new ArrayList<PedidoObra>();
        for (int i = 0; i < listaObras.size(); i++) {
            PedidoObra po = listaObras.get(i);

            if (FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicio, fechaFin)) {
                listaObrasFiltradas.add(po);
            }
        }
        datos.put("LISTA_PEDIDOS_OBRA", listaObrasFiltradas);

        if (listaObrasFiltradas.isEmpty()) {
            throw new Exception("No hay obras finalizadas en el período de tiempo ingresado");
        }

        // Historicos
        // Cotizado | Ejecutado | Ganancia
        DefaultCategoryDataset gananciasHistoricas = new DefaultCategoryDataset();

        Date fechaInicioHistoricos = getFechaDesdeHistoricos(fechaFin, years);
        for (int i = 0; i < listaObras.size(); i++) {
            PedidoObra po = listaObras.get(i);

            if (FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicioHistoricos, fechaFin)
                    && po.getEstado().equals(PedidoObra.ESTADO_FINALIZADO)) {
                int yearObra = FechaUtil.getYearFromDate(po.getFechaInicio());
                double ganancia = po.calcularGanancia();
                double cotizado = po.getPlanificacion().getCotizacion().getCotizacionOriginal().CalcularTotal();
                double ejecutado = po.getEjecucion().calcularMontoTotal();

                // --------------------------------------------------
                // GANANCIAS POR AÑO
                // --------------------------------------------------
                gananciasHistoricas.addValue(cotizado, "Montos Cotizados", "" + yearObra);
                gananciasHistoricas.addValue(ejecutado, "Montos Ejecutados", "" + yearObra);
                gananciasHistoricas.addValue(ganancia, "Diferencia", "" + yearObra);

                // --------------------------------------------------
            }
        }

        datos.put("GANANCIAS_HISTORICAS", gananciasHistoricas);

        InformeGeneralDeGanancias reporte = new InformeGeneralDeGanancias();
        try {
            reporte.setNombreReporte("Montos Cotizados VS Montos Ejecutados");
            reporte.setNombreArchivo("MontosCotizadosVSEjecutados", ReportDesigner.REPORTE_TIPO_OTROS);
            reporte.makeAndShow(datos);
        } catch (Exception ex) {
            System.err.println("Error al generar el reporte");
            throw new Exception("Se produjo un error al generar el Reporte", ex);
        }
    }

    public void generarInformeCantidadCotizacionesRechazadas(int cantidadYears) throws Exception {

        // Cotizaciones comunes
        List<PedidoObra> listaObras = null;
        try {
            HibernateUtil.beginTransaction();
            listaObras = (List) HibernateUtil.getSession().createQuery("FROM PedidoObra").list();
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error:" + e.getMessage());
            throw new Exception("No se pudo cargar correctamente el listado de Obras", e);
        }

        // Valido que haya obras
        List<PedidoObra> listaObrasFiltradas = new ArrayList<PedidoObra>();
        for (int i = 0; i < listaObras.size(); i++) {
            PedidoObra po = listaObras.get(i);

            if (FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicio, fechaFin)
                    && !po.getEstado().equals(PedidoObra.ESTADO_CANCELADO)) {
                listaObrasFiltradas.add(po);
            }
        }

        if (listaObrasFiltradas.isEmpty()) {
            throw new Exception("No hay obras finalizadas en el período de tiempo ingresado");
        }

        datos.put("LISTA_PEDIDOS_OBRA", listaObrasFiltradas);
        datos.put("ANIOS_HISTORICO", cantidadYears);

        // Historico
        Date fechaInicioHistoricos = getFechaDesdeHistoricos(fechaFin, cantidadYears);

        HashMap<String, Integer> listaObrasHistorico = new HashMap<String, Integer>();
        HashMap<String, Double> listaRechazadoCliente = new HashMap<String, Double>();

        for (int i = 0; i < listaObras.size(); i++) {
            PedidoObra po = listaObras.get(i);

            if (FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicioHistoricos, fechaFin)
                    && !po.getEstado().equals(PedidoObra.ESTADO_CANCELADO)) {

                int cantidadRechazadosObra = calcularCantidadCotizacionesRechazadas(po);

                // =======================================================================
                // RECHAZADOS X AÑO
                // =======================================================================
                // Saco el año de la obra
                int yearObra = FechaUtil.getYearFromDate(po.getFechaInicio());

                if (listaObrasHistorico.containsKey(String.valueOf(yearObra))) {
                    // SUmo
                    int cantidad = listaObrasHistorico.get(String.valueOf(yearObra));
                    cantidad += cantidadRechazadosObra;
                    listaObrasHistorico.put(String.valueOf(yearObra), cantidad);
                } else {
                    listaObrasHistorico.put(String.valueOf(yearObra), cantidadRechazadosObra);
                }

                // =======================================================================
                // RECHAZADOS X ClIENTE
                // =======================================================================
                // Get del Cliente de la Obra
                gestorBDvarios util = new gestorBDvarios();

                String nombre = "";
                if (po.getPlanta() != null) {
                    EmpresaCliente cliente = util.buscarEmpresaCliente(po.getPlanta());
                    if (cliente != null) {
                        nombre = cliente.getRazonSocial();
                    }
                }
                if (listaRechazadoCliente.containsKey(nombre)) {
                    double cantidad = listaRechazadoCliente.get(nombre);
                    cantidad += cantidadRechazadosObra;
                    listaRechazadoCliente.put(nombre, cantidad);
                } else {
                    listaRechazadoCliente.put(nombre, Double.valueOf(cantidadRechazadosObra));
                }

            }
        }
        datos.put("OBRAS_HISTORICO", listaObrasHistorico);
        datos.put("HISTORICO_X_CLIENTE", listaRechazadoCliente);

        InformeCantidadCotizacionesRechazadas reporte = new InformeCantidadCotizacionesRechazadas();
        try {
            reporte.setNombreReporte("Cantidad de Cotizaciones rechazadas.");
            reporte.setNombreArchivo("CantidadCotizacionesRechazadas", ReportDesigner.REPORTE_TIPO_OTROS);
            reporte.makeAndShow(datos);
        } catch (Exception ex) {
            System.err.println("Error al generar el reporte");
            throw new Exception("Se produjo un error al generar el Reporte", ex);
        }
    }
     public void generarInformePorcentajeDeBeneficiosSegunAceptacionDeCotizacion(NTupla empresaCliente) throws Exception {

        // Cotizaciones comunes
        List<PedidoObra> listaObras = null;
        List<Planta> listaPlantas = null;
        try {
            HibernateUtil.beginTransaction();
            int idEmpresaCliente=empresaCliente.getId();
            if(idEmpresaCliente>0) {
                listaPlantas =  ((EmpresaCliente)empresaCliente.getData()).getPlantas();
                /*
                listaObras = (List) HibernateUtil.getSession().createQuery("FROM PedidoObra AS po WHERE po.planta IN elements(:plants)").setParameter("plants", ((EmpresaCliente)empresaCliente.getData()).getPlantas()).list();
                */
                //SELECT ClinicId,Name from Clinic where :ClinicIds is NULL OR ClinicId IN :ClinicIds
                Criteria criteria = HibernateUtil.getSession().createCriteria(PedidoObra.class);
                
                criteria.add(Restrictions.in("planta", ((EmpresaCliente)empresaCliente.getData()).getPlantas()));
                
                listaObras = criteria.list();
            }
            else {
                listaObras = (List) HibernateUtil.getSession().createQuery("FROM PedidoObra").list();
            }
            
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
            throw new Exception("No se pudo cargar correctamente el listado de Obras", e);
        }

        // Valido que haya obras
        List<PedidoObra> listaObrasFiltradas = new ArrayList<PedidoObra>();
        for (int i = 0; i < listaObras.size(); i++) {
            PedidoObra po = listaObras.get(i);

            if (FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicio, fechaFin)
                    && !po.getEstado().equals(PedidoObra.ESTADO_SOLICITADO)) {
                listaObrasFiltradas.add(po);
            }
        }

        if (listaObrasFiltradas.isEmpty()) {
            throw new Exception("No hay obras en el período de tiempo ingresado");
        }

        datos.put("LISTA_PEDIDOS_OBRA", listaObrasFiltradas);
        datos.put("Cliente", empresaCliente);
        

        HashMap<String, Integer> listaObrasHistorico = new HashMap<String, Integer>();
        HashMap<String, Double> listaRechazadoCliente = new HashMap<String, Double>();

        boolean ban=false;
        for (int i = 0; i < listaObras.size(); i++) {
            PedidoObra po = listaObras.get(i);

            if (FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicio, fechaFin)
                    && !po.getEstado().equals(PedidoObra.ESTADO_SOLICITADO)) {
                ban=true;
                List<Cotizacion> listaCotizacionesAceptadas = obtenerCotizacionesAceptadas(listaObrasFiltradas);
                List<Cotizacion> listaCotizacionesRechazadas = obtenerCotizacionesRechazadas(listaObrasFiltradas);
                boolean primeraVez;
                
                double porcentAceptadasMaximo = 0.0;
                double porcentAceptadasMinimo = 9999.0;
                double porcentAceptadasSumatoria = 0.0;
                int porcentAceptadasCantidad = 0;
                double porcentAceptadasPromedio = 0.0;
                                
                for (Cotizacion co: listaCotizacionesAceptadas) {
                    double montoBase=co.CalcularMontoBase();
                    double montoTotal=co.CalcularTotal();
                    double ganancia = montoTotal - montoBase;
                    double gananciaPorcentajeTotal = (double)Math.round((ganancia/montoTotal)*10000)/100;
                   
                    if(montoTotal>0)
                    {
                        if(porcentAceptadasMaximo < gananciaPorcentajeTotal){
                            porcentAceptadasMaximo = gananciaPorcentajeTotal;
                        }
                        if(porcentAceptadasMinimo > gananciaPorcentajeTotal){
                            porcentAceptadasMinimo = gananciaPorcentajeTotal;
                        }
                        porcentAceptadasCantidad++;
                        porcentAceptadasSumatoria+=gananciaPorcentajeTotal;
                    }
                }
                if(porcentAceptadasCantidad > 0){
                    porcentAceptadasPromedio=porcentAceptadasSumatoria / porcentAceptadasCantidad;
                }
                
                double porcentRechazadasMaximo = 0.0;
                double porcentRechazadasMinimo = 999.0;
                double porcentRechazadasSumatoria = 0.0;
                int porcentRechazadasCantidad = 0;
                double porcentRechazadasPromedio = 0.0;
                
                for (Cotizacion co: listaCotizacionesRechazadas) {
                    double montoBase=co.CalcularMontoBase();
                    double montoTotal=co.CalcularTotal();
                    double ganancia = montoTotal - montoBase;
                    double gananciaPorcentajeTotal = (double)Math.round((ganancia/montoTotal)*10000)/100;
                   
                    if(montoTotal>0)
                    {
                        if(porcentRechazadasMaximo < gananciaPorcentajeTotal){
                            porcentRechazadasMaximo = gananciaPorcentajeTotal;
                        }
                        if(porcentRechazadasMinimo > gananciaPorcentajeTotal){
                            porcentRechazadasMinimo = gananciaPorcentajeTotal;
                        }
                        porcentRechazadasCantidad++;
                        porcentRechazadasSumatoria+=gananciaPorcentajeTotal;
                    }
                }
                if(porcentRechazadasCantidad > 0){
                    porcentRechazadasPromedio=porcentRechazadasSumatoria / porcentRechazadasCantidad;
                }
                
                if(porcentAceptadasMinimo<=100) {
                    datos.put("porcentajeAceptadasMaximo", porcentAceptadasMaximo);
                    datos.put("porcentajeAceptadasMinimo", porcentAceptadasMinimo);
                    datos.put("porcentajeAceptadasPromedio", porcentAceptadasPromedio);
                }
                if(porcentRechazadasMinimo<=100) {
                    datos.put("porcentajeRechazadasMaximo", porcentRechazadasMaximo);
                    datos.put("porcentajeRechazadasMinimo", porcentRechazadasMinimo);
                    datos.put("porcentajeRechazadasPromedio", porcentRechazadasPromedio);
                }
                
            }
            
        }
        
        if(!ban){
                throw new Exception("No hay cotizaciones rechazadas o aceptadas dentro del rango d fechas seleccionado");
            }
        
        InformPorcentajeDeBeneficiosSegunAceptacionDeCotizaciones reporte = new InformPorcentajeDeBeneficiosSegunAceptacionDeCotizaciones();
        try {
            reporte.setNombreReporte("Beneficios de obras anteriores segun aceptacion o rechazo de cotizaciones.");
            reporte.setNombreArchivo("Beneficios_obras_anteriores_segun_aceptacion", ReportDesigner.REPORTE_TIPO_OTROS);
            reporte.makeAndShow(datos);
        } catch (Exception ex) {
            System.err.println("Error al generar el reporte");
            throw new Exception("Se produjo un error al generar el Reporte", ex);
        }
    }

    private Date getFechaDesdeHistoricos(Date fechaFin, int cantidadYears) {
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
        if (po.getCotizaciones() != null) {
            for (int j = 0; j < po.getCotizaciones().size(); j++) {
                Cotizacion cot = po.getCotizaciones().get(j);
                if (cot.getEstado().equals(Cotizacion.ESTADO_RECHAZADO)) {
                    cantidadTotal++;
                }
            }
        }
        return cantidadTotal;
    }

    public void generarInformeObrasPorYear(Integer years) throws Exception {
        datos.put("YEARS_HISTORICO", years);

        // Cotizaciones comunes
        List<PedidoObra> listaObras = null;
        try {
            HibernateUtil.beginTransaction();
            listaObras = (List) HibernateUtil.getSession().createQuery("FROM PedidoObra").list();
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error:" + e.getMessage());
            throw new Exception("No se pudo cargar correctamente el listado de Obras", e);
        }

        // Historico
        Date fechaInicioHistoricos = getFechaDesdeHistoricos(fechaFin, years);
        HashMap<String, Integer> listaObrasHistorico = new HashMap<String, Integer>();

        for (int i = 0; i < listaObras.size(); i++) {
            PedidoObra po = listaObras.get(i);

            if (FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicioHistoricos, fechaFin)
                    && po.getEstado().equals(PedidoObra.ESTADO_FINALIZADO)) {
                String yearPo = "" + FechaUtil.getYearFromDate(po.getFechaInicio());
                if (listaObrasHistorico.containsKey(yearPo)) {
                    int cantidad = listaObrasHistorico.get(yearPo);
                    cantidad++;
                    listaObrasHistorico.put(yearPo, cantidad);
                } else {
                    listaObrasHistorico.put(yearPo, 1);
                }
            }
        }

        datos.put("DATOS_HISTORICOS", listaObrasHistorico);

        InformeObrasPorAnio reporte = new InformeObrasPorAnio();
        try {
            reporte.setNombreReporte("Cantidad de Obras Finalizadas por año.");
            reporte.setNombreArchivo("CantidadObrasPorAnio", ReportDesigner.REPORTE_TIPO_OTROS);
            reporte.makeAndShow(datos);
        } catch (Exception ex) {
            System.err.println("Error al generar el reporte");
            throw new Exception("Se produjo un error al generar el Reporte", ex);
        }

    }

    public void generarInformeControlObras(int cantidadYears) throws Exception{
        // Cotizaciones comunes
        List<PedidoObra> listaObras = null;
        try {
            HibernateUtil.beginTransaction();
            listaObras = (List) HibernateUtil.getSession().createQuery("FROM PedidoObra").list();
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error:" + e.getMessage());
            throw new Exception("No se pudo cargar correctamente el listado de Obras", e);
        }

        // Valido que haya obras
        List<PedidoObra> listaObrasFiltradas = new ArrayList<PedidoObra>();
        for (int i = 0; i < listaObras.size(); i++) {
            PedidoObra po = listaObras.get(i);

            if (FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicio, fechaFin)
                    && !po.getEstado().equals(PedidoObra.ESTADO_CANCELADO)) {
                listaObrasFiltradas.add(po);
            }
        }

        if (listaObrasFiltradas.isEmpty()) {
            throw new Exception("No hay obras finalizadas en el período de tiempo ingresado");
        }
        
         generarInformeControlObras(listaObrasFiltradas, cantidadYears);
    }

    public void generarInformeControlObras(PedidoObra po) throws Exception {
        List<PedidoObra> pedidos = new ArrayList<PedidoObra>();
        pedidos.add(po);
        generarInformeControlObras(pedidos, -1); // -1 Sin Historico
    }

    private void generarInformeControlObras(List<PedidoObra> pedidos, int years) throws Exception {
        datos.put("LISTA_PEDIDOS_OBRA", pedidos);
        datos.put("YEARS_HISTORICO", years);
        
        InformeCompletoControl reporte = new InformeCompletoControl();
        try {
            reporte.setNombreReporte("Informe de Control de Obras");
            reporte.setNombreArchivo("InformeCompletoControl", ReportDesigner.REPORTE_TIPO_OTROS);
            reporte.makeAndShow(datos);
        } catch (Exception ex) {
            System.err.println("Error al generar el reporte");
            throw new Exception("Se produjo un error al generar el Reporte", ex);
        }
        
    }
    
    public List<NTupla> mostrarClientes() throws Exception{
       
        List<EmpresaCliente> listaEmpresas = new ArrayList<EmpresaCliente>();
        List<NTupla> listaTuplasEmpresas = new ArrayList<NTupla>();
        try {
            HibernateUtil.beginTransaction();
            listaEmpresas = (List) HibernateUtil.getSession().createQuery("FROM EmpresaCliente").list();
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error:" + e.getMessage());
            throw new Exception("No se pudo cargar correctamente el listado de EmpresaCliente", e);
        }
        
        for (EmpresaCliente empC : listaEmpresas) {
            NTupla t= new NTupla(empC.getId());
            t.setNombre(empC.getRazonSocial());
            t.setData(empC);
            listaTuplasEmpresas.add(t);
        }
        
        return listaTuplasEmpresas;

    }
    
    private List<Cotizacion> obtenerCotizacionesAceptadas(List<PedidoObra> lpo) {
        List<Cotizacion> listaCotizacionesAceptadas = new ArrayList<Cotizacion>();
        for (PedidoObra po:lpo) {        
        
            if ( po.getCotizaciones() != null) {
                for (int j = 0; j < po.getCotizaciones().size(); j++) {
                    Cotizacion cot = po.getCotizaciones().get(j);
                    if (cot.getEstado().equals(Cotizacion.ESTADO_RECHAZADO)) {
                        listaCotizacionesAceptadas.add(cot);
                    }
                }
            }
        }
        return listaCotizacionesAceptadas;
    }
    private List<Cotizacion> obtenerCotizacionesRechazadas(List<PedidoObra> lpo) {
        List<Cotizacion> listaCotizacionesRechazadas = new ArrayList<Cotizacion>();
        for (PedidoObra po:lpo) {        
        
            if ( po.getCotizaciones() != null) {
                for (int j = 0; j < po.getCotizaciones().size(); j++) {
                    Cotizacion cot = po.getCotizaciones().get(j);
                    if (cot.getEstado().equals(Cotizacion.ESTADO_ACEPTADO)) {
                        listaCotizacionesRechazadas.add(cot);
                    }
                }
            }
        }
        return listaCotizacionesRechazadas;
    }
}
