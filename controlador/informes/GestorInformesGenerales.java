package controlador.informes;

import controlador.utiles.gestorBDvarios;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import modelo.Cotizacion;
import modelo.DetalleTareaEjecucion;
import modelo.DetalleTareaEjecucionXDia;
import modelo.Empleado;
import modelo.EmpresaCliente;
import modelo.Herramienta;
import modelo.ItemComprable;
import modelo.Material;
import modelo.PedidoObra;
import modelo.Planta;
import modelo.Recurso;
import modelo.RecursoEspecifico;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jfree.data.category.DefaultCategoryDataset;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
import vista.reportes.ReportDesigner;
import vista.reportes.sources.InformPorcentajeDeBeneficiosSegunAceptacionDeCotizaciones;
import vista.reportes.sources.InformeCantidadCotizacionesRechazadas;
import vista.reportes.sources.InformeCompletoControl;
import vista.reportes.sources.InformeComprasPorMes;
import vista.reportes.sources.InformeGeneralDeGanancias;
import vista.reportes.sources.InformeHorasEjecutadasPorEmpleado;
import vista.reportes.sources.InformeObrasPorAnio;

/**
 *
 * @author Iuga
 */
public class GestorInformesGenerales {

    private Date fechaInicio;
    private Date fechaFin;
    private HashMap<String, Object> datos;
    
    public final static String SELECCIONE_UN_TIPO_RECURSO = "Seleccione un tipo de recurso...";

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

        double porcentAceptadasMaximo = 0.0;
        double porcentAceptadasMinimo = 9999.0;
        double porcentAceptadasSumatoria = 0.0;
        int porcentAceptadasCantidad = 0;
        double porcentAceptadasPromedio = 0.0;
                
        double porcentRechazadasMaximo = 0.0;
        double porcentRechazadasMinimo = 999.0;
        double porcentRechazadasSumatoria = 0.0;
        int porcentRechazadasCantidad = 0;
        double porcentRechazadasPromedio = 0.0;
                
                
        boolean ban=false;
      //  for (int i = 0; i < listaObrasFiltradas.size(); i++) {
      //      PedidoObra po = listaObrasFiltradas.get(i);

        //    if (FechaUtil.fechaEnRango(po.getFechaInicio(), fechaInicio, fechaFin)
      //              && !po.getEstado().equals(PedidoObra.ESTADO_SOLICITADO)) {
                ban=true;
                List<Cotizacion> listaCotizacionesAceptadas = obtenerCotizacionesAceptadas(listaObrasFiltradas);
                List<Cotizacion> listaCotizacionesRechazadas = obtenerCotizacionesRechazadas(listaObrasFiltradas);
                boolean primeraVez;
                
                
                                
                for (Cotizacion co: listaCotizacionesAceptadas) {
                    double montoBase=co.CalcularMontoBase();
                    double montoTotal=co.CalcularTotal();
                    double ganancia = montoTotal - montoBase;
                    double gananciaPorcentajeTotal = (double)Math.round(((ganancia*100)/montoBase));
                   
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
                 
                
      //      }
            
      //  }
        
        if(porcentAceptadasCantidad > 0){
             porcentAceptadasPromedio=porcentAceptadasSumatoria / porcentAceptadasCantidad;
        }
        if(porcentRechazadasCantidad > 0){
            porcentRechazadasPromedio=porcentRechazadasSumatoria / porcentRechazadasCantidad;
        }
                
        if(porcentAceptadasCantidad > 0) {
                    datos.put("porcentajeAceptadasMaximo", porcentAceptadasMaximo);
                    datos.put("porcentajeAceptadasMinimo", porcentAceptadasMinimo);
                    datos.put("porcentajeAceptadasPromedio", porcentAceptadasPromedio);
        }
        if(porcentRechazadasCantidad > 0) {
                    datos.put("porcentajeRechazadasMaximo", porcentRechazadasMaximo);
                    datos.put("porcentajeRechazadasMinimo", porcentRechazadasMinimo);
                    datos.put("porcentajeRechazadasPromedio", porcentRechazadasPromedio);
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
    
    public List<NTupla> mostrarEmpleados() throws Exception{
       
        List<Empleado> listaEmpleados = new ArrayList<Empleado>();
        List<NTupla> listaTuplasEmpleados = new ArrayList<NTupla>();
        try {
            HibernateUtil.beginTransaction();
            listaEmpleados = (List) HibernateUtil.getSession().createQuery("FROM Empleado").list();
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error:" + e.getMessage());
            throw new Exception("No se pudo cargar correctamente el listado de empleados", e);
        }
        
        for (Empleado empleado : listaEmpleados) {
//            if(empleado.estaActivo())
//            {
                NTupla t= new NTupla(empleado.getoId());
                t.setNombre(empleado.getNombreEmpleado());
                t.setData(empleado);
                listaTuplasEmpleados.add(t);
//            }
        }
        
        return listaTuplasEmpleados;
    }
    
    private List<Cotizacion> obtenerCotizacionesAceptadas(List<PedidoObra> lpo) {
        List<Cotizacion> listaCotizacionesAceptadas = new ArrayList<Cotizacion>();
        for (PedidoObra po:lpo) {        
        
            if ( po.getCotizaciones() != null) {
                for (int j = 0; j < po.getCotizaciones().size(); j++) {
                    Cotizacion cot = po.getCotizaciones().get(j);
                    if (cot.getEstado().equals(Cotizacion.ESTADO_ACEPTADO)) {
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
                    if (cot.getEstado().equals(Cotizacion.ESTADO_RECHAZADO)) {
                        listaCotizacionesRechazadas.add(cot);
                    }
                }
            }
        }
        return listaCotizacionesRechazadas;
    }
    
    /**
     * Método que permite dibujar el informe de Carga Horaria de Empleados
     * Por mes en base al id del empleado seleccionado. Si el id es = 0 entonces
     * se muestran la información de todos los empleados que hayan trabajado en
     * el periodo determinado por fechaInicio y fechaFin
     * @param id del Empleado seleccionado
     * @throws Exception Mensajes al usuario de fallas o ausencia de errores
     */
    public void generarInformeCargaHorariaEmpleados(int id) throws Exception{
        
        HashMap<Integer,HashMap<String,Integer>> empleados = new HashMap<Integer,HashMap<String,Integer>>();
        List detallesEjecucion = null;

        HibernateUtil.beginTransaction();
//        HibernateUtil.getSession().get(Empleado.class, id);
        if(id == 0) // Mostrar Todos
        {   
            detallesEjecucion = (List) HibernateUtil.getSession()
                    .createQuery("SELECT dte FROM DetalleTareaEjecucion AS dte INNER JOIN "
                    + "dte.listaDetallePorDia AS dteXDia "
                    + "WHERE dteXDia.fecha BETWEEN :fechaInicio AND :fechaFin "
                    + "AND dte.empleados IS NOT EMPTY")
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .list();                
        }
        else // Mostrar sólo el seleccionado
        { 
            detallesEjecucion = (List) HibernateUtil.getSession()
                    .createQuery("FROM DetalleTareaEjecucion AS dte "
                    + "WHERE :empleado in elements (dte.empleados) "
                    + "AND dte.empleados IS NOT EMPTY")
                    .setParameter("empleado", id)
                    .list();
        }
        HibernateUtil.commitTransaction();

        // Si no hay ningún dato en ejecución me evito el procesamiento.
        if(detallesEjecucion == null || detallesEjecucion.isEmpty()) 
        {
            throw new Exception("<HTML><BODY>No se encontraron datos "
                + "de asignaciones de empleados en la fechas seleccionadas.");
        }
        
        ArrayList<Integer> detallesXDiaYaVisitados = new ArrayList<Integer>();
        for( int i = 0 ; i < detallesEjecucion.size() ; i++)
        {
            DetalleTareaEjecucion detalle = (DetalleTareaEjecucion) detallesEjecucion.get(i);
            
            // Obtenemos el id del empleado asociado
            int idEmpleado = 0;
            for(int m = 0; m < detalle.getEmpleados().size() ; m++)
            {
                if(m > 0) {throw new Exception("Existe más de un empleado asociado a una detalleTareaEjecución");}

                Empleado emp = detalle.getEmpleados().get(m);
                idEmpleado = emp.getoId();
            }

            // Sumamos las horas de trabajo del empleado
            HashMap<String,Integer> prevHorasEmpleado = (HashMap<String,Integer>)empleados.get(idEmpleado);
            if(prevHorasEmpleado == null)
            {
                prevHorasEmpleado = new HashMap<String, Integer>();
            }
            for(int j=0 ; j < detalle.getListaDetallePorDia().size() ; j++ )
            {
                DetalleTareaEjecucionXDia detalleXDia = detalle.getListaDetallePorDia().get(j);

                if(!detallesXDiaYaVisitados.contains(detalleXDia.getId()))
                {
                    int horas = 0;
                    horas += detalleXDia.getCantHorasNormales();
                    horas += detalleXDia.getCantHorasAl50();
                    horas += detalleXDia.getCantHorasAl100();

                    Integer cantHorasPrev = prevHorasEmpleado.get(FechaUtil.getMaskedDate("MM-yyyy",detalleXDia.getFecha()));

                    // Si fuera la primera vez que se trabaja con esta fecha
                    // se la crea desde 0
                    if(cantHorasPrev == null)
                    {
                        cantHorasPrev = new Integer(0);
                    }
                    prevHorasEmpleado.put(FechaUtil.getMaskedDate("MM-yyyy",detalleXDia.getFecha()), cantHorasPrev + horas);
                    
                    detallesXDiaYaVisitados.add(detalleXDia.getId());
                }
            }
            empleados.put(idEmpleado, prevHorasEmpleado);
        }

        datos.put("EMPLEADOS", empleados);

        InformeHorasEjecutadasPorEmpleado reporte = new InformeHorasEjecutadasPorEmpleado(InformeHorasEjecutadasPorEmpleado.INFORME_MENSUAL);
        try {
            reporte.setNombreReporte("Horas Ejecutadas de Empleados por Mes");
            reporte.setNombreArchivo("HorasEjecutadasDeEmpleadosPorMes", ReportDesigner.REPORTE_TIPO_OTROS);
            reporte.makeAndShow(datos);
        } catch (Exception ex) {
            System.err.println("Error al generar el reporte");
            throw new Exception("Se produjo un error al generar el Reporte", ex);
        }
    }
    
    /**
     * Método que permite crear el informe de Compras por Mes en base al tipo
     * de recurso seleccionado. Entre ellos encontramos; Materiales, Herramientas
     * Alquileres/Compras y Gastos Varios.
     * @param tipoRecurso EL tipo de Recurso a Asociar
     * @throws Exception 
     */
    public void generarInformeComprasPorMes() throws Exception{
        
        HashMap<String,HashMap<String,Double>> compras = new HashMap<String,HashMap<String,Double>>();
                     
        HibernateUtil.beginTransaction(); 
        List<Object> recepciones = (List) HibernateUtil.getSession()
                .createQuery("SELECT (droc.cantidad * doc.precioUnitario) "
                + "AS precio, roc.fechaRecepcion, ic.id "
                + "FROM RecepcionOrdenDeCompra AS roc "
                + "INNER JOIN roc.recepcionesParciales AS droc "
                + "INNER JOIN droc.detalleOrdenDeCompra AS doc "
                + "INNER JOIN doc.item AS ic "
                + "WHERE roc.fechaRecepcion BETWEEN :fechaInicio AND :fechaFin")
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .list();   

        HibernateUtil.commitTransaction();
       
        for(int i=0; i < recepciones.size(); i++)
        {
            Object[] object = (Object[])recepciones.get(i);
            Double precio = (Double) object[0];
            Date fechaRecepcion = (Date) object[1];
            int idItemComprable = (Integer) object[2];
            
            ItemComprable itemComprable = (ItemComprable) HibernateUtil.getSession().get(ItemComprable.class, idItemComprable);
            Object item = itemComprable.getItem();
            
            Class itemClass = item.getClass();
            
            if(item instanceof RecursoEspecifico)
            {
                RecursoEspecifico re = (RecursoEspecifico) item;
                Recurso r = re.getRecurso();
                
                if(r instanceof Herramienta)
                {
                    itemClass = Herramienta.class;
                }
                if(r instanceof Material)
                {
                    itemClass = Material.class;
                }
            }
            
            HashMap<String,Double> compra = compras.get(FechaUtil.getMaskedDate("MM-yyyy",fechaRecepcion));
            if(compra == null)
            {
                compra = new HashMap<String, Double>();
            }
            Double precioAnterior = compra.get(itemClass.getName());
            if(precioAnterior == null)
            {
                precioAnterior = new Double(0d);
            }
            
            compra.put(itemClass.getName(), precioAnterior + precio);
            compras.put(FechaUtil.getMaskedDate("MM-yyyy",fechaRecepcion), compra);
        }

        datos.put("COMPRAS", compras);

        InformeComprasPorMes reporte = new InformeComprasPorMes();
        try {
            reporte.setNombreReporte("Compras Mensuales");
            reporte.setNombreArchivo("ComprasMensuales", ReportDesigner.REPORTE_TIPO_OTROS);
            reporte.makeAndShow(datos);
        } catch (Exception ex) {
            System.err.println("Error al generar el reporte");
            throw new Exception("Se produjo un error al generar el Reporte", ex);
        }
    }
}
