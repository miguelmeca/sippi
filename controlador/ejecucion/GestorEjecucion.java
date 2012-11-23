package controlador.ejecucion;
 
import com.toedter.calendar.DateUtil;
import config.Iconos;
import controlador.planificacion.GestorEditarPlanificacion;
import controlador.planificacion.GestorPlanificacionDatosGenerales;
import controlador.planificacion.PlanificacionUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultTreeModel;
import modelo.DetalleTareaEjecucion;
import modelo.DetalleTareaEjecucionXDia;
import modelo.DetalleTareaPlanificacion;
import modelo.Ejecucion;
import modelo.EjecucionXAdicional;
import modelo.EjecucionXAlquilerCompra;
import modelo.EjecucionXHerramienta;
import modelo.EjecucionXHerramientaXDia;
import modelo.EjecucionXMaterial;
import modelo.Empleado;
import modelo.Especialidad;
import modelo.PedidoObra;
import modelo.PlanificacionXAlquilerCompra;
import modelo.PlanificacionXHerramienta;
import modelo.PlanificacionXMaterial;
import modelo.Recurso;
import modelo.RecursoEspecifico;
import modelo.RecursoXProveedor;
import modelo.SubObraXAdicionalModif;
import modelo.TareaEjecucion;
import modelo.TareaPlanificacion;
import modelo.TipoTarea;
import org.hibernate.HibernateException;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
import util.RecursosUtil;
import util.Tupla;
import vista.ejecucion.VentanaEjecucion;
import vista.planificacion.ArbolDeTareasTipos;
import vista.planificacion.arbolTareas.ArbolIconoNodo;

/**
 *
 * @author Iuga
 */
public class GestorEjecucion {

    private VentanaEjecucion pantalla;
    private int idObra;
    private PedidoObra pedidoDeObra;
    private Ejecucion ejecucion;
    private TareaEjecucion tareaSeleccionada;
    private int hashTarea;
    DetalleTareaEjecucionXDia detalleRRHHXDiaSeleccionado;
    DetalleTareaEjecucion detalleRRHHSeleccionado;
    
    

    public GestorEjecucion(VentanaEjecucion vista, int idObra) {
        this.pantalla = vista;
        this.idObra = idObra;        
        cargarObra(idObra);
        mostrarDatosGenerales();
    }
    
    public int getIdObraActual(){
        return idObra;
    }
    
    public int getIdCotizacionPlanificada() {
        return pedidoDeObra.getPlanificacion().getCotizacion().getCotizacionOriginal().getId();
    }
    
    
    
    private void mostrarMensajeError(String msg) {
        System.err.println("[ERROR] " + msg);
        pantalla.mostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error!", msg);
    }
    
    public Ejecucion getEjecucionActual(){
        return ejecucion;
    }
    
    public void mostrarDatosGenerales() {

        try {
            if(pedidoDeObra.getNombre()!=null && pedidoDeObra.getNombre().length()>60)
            {
                pantalla.setLblObraNombre(pedidoDeObra.getNombre().substring(0, 60) + "...");
            }
            else
            {
                pantalla.setLblObraNombre(pedidoDeObra.getNombre());
            }
            
            pantalla.setLblObraFechaFin(FechaUtil.getFecha(pedidoDeObra.getFechaFin()));
            pantalla.setLblObraFechaInicio(FechaUtil.getFecha(pedidoDeObra.getFechaInicio()));
            pantalla.setLblObraLugar(pedidoDeObra.getPlanta().getDomicilio().toString());
            pantalla.setLblObraPlanta(pedidoDeObra.getPlanta().getRazonSocial());

            if (this.ejecucion != null) {
                pantalla.setFechaInicioEjecucion(this.ejecucion.getFechaInicio());
                pantalla.setFechaFinEjecucion(this.ejecucion.getFechaFin());
                pantalla.setEstadoEjecucion(this.ejecucion.getEstado());
            }
            
            if (this.ejecucion.getPlanificacionOriginal() != null) {
                pantalla.setFechaInicioPlanificacion(this.ejecucion.getPlanificacionOriginal().getFechaInicio());
                pantalla.setFechaFinPlanificacion(this.ejecucion.getPlanificacionOriginal().getFechaFin());
                pantalla.setPlanificacionMontoTotal(String.valueOf(this.ejecucion.getPlanificacionOriginal().calcularMontoTotal()));
                //TODO!!!!! WTF? NO TENEMOS MONTO TOTAL DE PLANIFICACION, ni si quiera calculado
            }

            if (this.ejecucion != null && this.ejecucion.getPlanificacionOriginal().getCotizacion() != null && this.ejecucion.getPlanificacionOriginal().getCotizacion().getCotizacionOriginal() != null) {
                pantalla.setTxtNroCotizacion(this.ejecucion.getPlanificacionOriginal().getCotizacion().getCotizacionOriginal().getNroCotizacion());
                pantalla.setCotizacionMontoTotal(String.valueOf(this.ejecucion.getPlanificacionOriginal().getCotizacion().getCotizacionOriginal().CalcularTotal()));
            }
            
            if (this.ejecucion != null)
            {
                pantalla.setDescripcionPlanificacion(this.ejecucion.getDescripcion());
            }

        } catch (Exception e) {
            e.printStackTrace();
            HibernateUtil.rollbackTransaction();
            mostrarMensajeError("No se pudo cargar los datos generales de la cotizacion");
        }

    }
    
    public DefaultTreeModel cargarModeloArbolTareas()
    {
        ArbolIconoNodo raiz = new ArbolIconoNodo(0,ArbolDeTareasTipos.TIPO_OBRA, pedidoDeObra.getNombre(),Iconos.ICONO_OBRA);
        DefaultTreeModel modelo = new DefaultTreeModel(raiz);
        
        //Agregar todas las tareas, subtareas y recursos de cada obra        
        cargarTareasEnArbol(modelo, ejecucion.getTareas(),raiz,/*true*/false);       
        
        return modelo;
    } 
    
    private void cargarObra(int idObra) {

        try {
            HibernateUtil.beginTransaction();
            this.pedidoDeObra = (PedidoObra) HibernateUtil.getSession().load(PedidoObra.class, idObra);
            ejecucion = this.pedidoDeObra.getEjecucion();
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error: Se produjo un error al cargar la Obra:\n" + e.getMessage());
            throw new HibernateException("Error: Se produjo un error al cargar la Obra");
        }
        
        if (ejecucion == null) {
            mostrarMensajeError("La obra no contiene una ejecucion");
        }
    }
    
    //Metodo recursivo
    private void cargarTareasEnArbol(DefaultTreeModel modelo, List<TareaPlanificacion> listaTareas,ArbolIconoNodo padre,boolean incluirRecursos)
    {
        int cantTareas= listaTareas.size();
        for (int i = 0; i < cantTareas; i++) 
        {
            TareaPlanificacion tarea= listaTareas.get(i);
            ArbolIconoNodo nodoTarea = new ArbolIconoNodo(tarea.hashCode(),ArbolDeTareasTipos.TIPO_TAREA,tarea.getNombre(),Iconos.ICONO_TAREA);
            modelo.insertNodeInto(nodoTarea, padre, i);            
            
            cargarTareasEnArbol(modelo, tarea.getSubtareas(),nodoTarea,incluirRecursos);
            
            int contadorTipoRecursosCargados=0;

            if(incluirRecursos)
            {
                if(tarea.getMateriales()!=null && !tarea.getMateriales().isEmpty())
                {
                    ArbolIconoNodo nodoMateriales = new ArbolIconoNodo(tarea.hashCode(),ArbolDeTareasTipos.TIPO_MATERIALES,ArbolDeTareasTipos.TIPO_MATERIALES,Iconos.ICONO_MATERIALES);
                    modelo.insertNodeInto(nodoMateriales, nodoTarea, 0);
                    int cantMateriales=tarea.getMateriales().size();
                    for (int j = 0; j < cantMateriales; j++) 
                    {
                        String nombre="";
                        PlanificacionXMaterial material=tarea.getMateriales().get(j);
                        RecursoXProveedor rxp = material.getMaterialCotizacion().getMaterial();
                        RecursoEspecifico RE=null;
                        try {
                            RE = (RecursoEspecifico) HibernateUtil.getSession().createQuery("from RecursoEspecifico RE where :cID in elements(RE.proveedores)").setParameter("cID", rxp).uniqueResult();

                            if (RE != null) {
                                nombre += RE.getNombre();
                            }

                            Recurso R = (Recurso) HibernateUtil.getSession().createQuery("from Recurso RE where :cID in elements(RE.recursos)").setParameter("cID", RE).uniqueResult();
                            if (R != null) {
                                nombre = R.getNombre() + " " + nombre;
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(GestorEditarPlanificacion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        ArbolIconoNodo nodoMaterial = new ArbolIconoNodo(material.hashCode(),ArbolDeTareasTipos.TIPO_MATERIAL,nombre/*material.toString()*/,Iconos.ICONO_MATERIAL);
                        modelo.insertNodeInto(nodoMaterial, nodoMateriales, j); 
                    } 
                    contadorTipoRecursosCargados++;
                }
            
                if(tarea.getHerramientas()!=null && !tarea.getHerramientas().isEmpty())
                {
                    ArbolIconoNodo nodoHerramientas = new ArbolIconoNodo(tarea.hashCode(),ArbolDeTareasTipos.TIPO_HERRAMIENTAS,ArbolDeTareasTipos.TIPO_HERRAMIENTAS,Iconos.ICONO_HERRAMIENTAS);
                    modelo.insertNodeInto(nodoHerramientas, nodoTarea, contadorTipoRecursosCargados);
                    int cantHeramientas=tarea.getHerramientas().size();
                    for (int j = 0; j < cantHeramientas; j++) 
                    {
                        PlanificacionXHerramienta herramienta=tarea.getHerramientas().get(j);
                        ArbolIconoNodo nodoHerramienta = new ArbolIconoNodo(herramienta.hashCode(),ArbolDeTareasTipos.TIPO_HERRAMIENTA,(herramienta.getHerramientaCotizacion().getHerramienta().getRecursoEsp().getNombre() + ":" +herramienta.getHerramientaCotizacion().getHerramienta().getNroSerie())/*herramienta.toString()*/,Iconos.ICONO_HERRAMIENTA);
                        modelo.insertNodeInto(nodoHerramienta, nodoHerramientas, j);
                    }
                    contadorTipoRecursosCargados++;
                }
                if(tarea.getAlquilerCompras()!=null && !tarea.getAlquilerCompras().isEmpty())
                {
                    ArbolIconoNodo nodoAlquileresCompras = new ArbolIconoNodo(tarea.hashCode(),ArbolDeTareasTipos.TIPO_ALQUILERESCOMPRAS,ArbolDeTareasTipos.TIPO_ALQUILERESCOMPRAS,Iconos.ICONO_ALQUILERESCOMPRAS);
                    modelo.insertNodeInto(nodoAlquileresCompras, nodoTarea, contadorTipoRecursosCargados);
                    int cantAlquileresCompras=tarea.getAlquilerCompras().size();
                    for (int j = 0; j < cantAlquileresCompras; j++) 
                    {
                        PlanificacionXAlquilerCompra alquilerCompra=tarea.getAlquilerCompras().get(j);
                        ArbolIconoNodo nodoAlquilerCompra = new ArbolIconoNodo(alquilerCompra.hashCode(),ArbolDeTareasTipos.TIPO_ALQUILERCOMPRA,(alquilerCompra.getAlquilerCompraCotizacion().getTipoAlquilerCompra().getNombre()+" "+alquilerCompra.getAlquilerCompraCotizacion().getDescripcion()),Iconos.ICONO_ALQUILERCOMPRA);
                        modelo.insertNodeInto(nodoAlquilerCompra, nodoAlquileresCompras, j);
                    }
                    contadorTipoRecursosCargados++;
                }
           }
       } 
    }
    
    
    public TareaEjecucion setearTareaEjecucionSeleccionada(int hash) {    
        tareaSeleccionada=(TareaEjecucion)ejecucion.buscarTareaPorHash(hash);
        
        // No entiendo que hace este IF
        if(tareaSeleccionada!=null){
            hashTarea=hash;
        }
        else {
            hashTarea=0;
        }
        
        return tareaSeleccionada;
    }

    public TareaEjecucion getTareaSeleccionada() {
        return tareaSeleccionada;
    }

    /**
     * Retorna los datos de la tarea (Desacople del Control y modelo)
     * 0 > Id Tipo Tarea (int)
     * 1 > Estado (String)
     * 2 > Fecha Inicio (Date)
     * 3 > Fecha Fin (Date)
     * 4 > Observaciones (String)
     * @return 
     */
    public NTupla getDatosTareaSeleccionada() {
        if(tareaSeleccionada!=null){
            NTupla datos = new NTupla();
                datos.setId(tareaSeleccionada.hashCode());
                datos.setNombre(tareaSeleccionada.getNombre());
                
                    Object[] data = new Object[5];
                    if(tareaSeleccionada.getTipoTarea()!=null){
                        data[0] = tareaSeleccionada.getTipoTarea().getId();
                    }else{
                        data[0] = 0;
                    }
                    
                    data[1] = tareaSeleccionada.getEstado();
                    data[2] = tareaSeleccionada.getFechaInicio();
                    data[3] = tareaSeleccionada.getFechaFin();
                    data[4] = tareaSeleccionada.getObservaciones();
                    datos.setData(data);
                    
            return datos;
        }
        return null;
    }
   /* public List<DetalleTareaEjecucionXDia> getListaRRHH(){
        
            EjecucionUtils.getTodasTareasEjecucion(ejecucion);
            ArrayList<TareaEjecucion> listaTareas =EjecucionUtils.getTodasTareasEjecucion(ejecucion);
            ArrayList<DetalleTareaEjecucionXDia> listaRRHH= new ArrayList<DetalleTareaEjecucionXDia>();
            for (int i = 0; i < listaTareas.size(); i++) {
                TareaEjecucion tarea = listaTareas.get(i);   
                List<DetalleTareaPlanificacion> listaDetalles= tarea.getDetalles();
                for (int j = 0; j < listaDetalles.size(); j++) {
                    DetalleTareaEjecucion detalle=(DetalleTareaEjecucion)listaDetalles.get(j);
                    listaRRHH.addAll(detalle.getListaDetallePorDia());
                }
            }
            return listaRRHH;

   
    }*/
    
     
    public List<Object> getListaRRHH(Date fechaDesde, Date fechaHasta) {
        List<Object> listaTuplasDetallesXDia = new ArrayList<Object>();
        
            if (ejecucion != null) {
                List<TareaPlanificacion> todasTareas;
                if(hashTarea>0) {
                    todasTareas = tareaSeleccionada.obtenerTodaslasSubtareasRecursivamente(true);
                }
                else {
                   todasTareas = PlanificacionUtils.getTodasTareasPlanificacion(ejecucion);
                }
                for (int i = 0; i < todasTareas.size(); i++) {
                    TareaEjecucion tarea = (TareaEjecucion) todasTareas.get(i);
                    List<DetalleTareaPlanificacion> listaDetalle = tarea.getDetalles();
                    for (int j = 0; j < listaDetalle.size(); j++) {
                        DetalleTareaEjecucion detalleTareaEjecucion = (DetalleTareaEjecucion)listaDetalle.get(j);
                        List<DetalleTareaEjecucionXDia> listaDetallesXDia = detalleTareaEjecucion.getListaDetallePorDia();
                        Empleado empleado=null;
                        if(detalleTareaEjecucion.getEmpleados().size()>0) {
                            empleado = detalleTareaEjecucion.getEmpleados().get(0);
                        }
                        
                        
                        for (DetalleTareaEjecucionXDia detalleXDia: listaDetallesXDia) {
                            
                            if(fechaDesde==null || FechaUtil.fechaMayorOIgualQue( detalleXDia.getFecha(), fechaDesde))
                            {
                                if(fechaHasta==null || FechaUtil.fechaMayorQue(FechaUtil.fechaMas(fechaHasta, 1),detalleXDia.getFecha()))
                                {
                            
                                    NTupla nt = new NTupla(detalleTareaEjecucion.hashCode());
                                    String nombreyLegajo;                           
                                    if(empleado!=null)
                                    {
                                      nombreyLegajo = empleado.getNombreEmpleadoYLegajo();
                                    }
                                    else
                                    {
                                       nombreyLegajo = "SIN ASIGNAR"; 
                                    }

                                    nt.setNombre(nombreyLegajo);
                                    nt.setData(detalleXDia);
                                    NTupla fecha=new NTupla();
                                    fecha.setNombre(FechaUtil.getFecha(detalleXDia.getFecha()));
                                    fecha.setData(detalleXDia.getFecha());
                                    
                                    NTupla tuplaTarea = new NTupla(tarea.hashCode());
                                    tuplaTarea.setNombre(tarea.getNombre());
                                    tuplaTarea.setData(detalleTareaEjecucion);
                                    

                                         Object[] data = new Object[6];
                                         //Interasante chanchada esta... :D
                                         data[0] = nt;
                                         data[1] = tuplaTarea;
                                         data[2] = fecha;
                                         data[3] = detalleXDia.getCantHorasNormales();
                                         data[4] = detalleXDia.getCantHorasAl50();
                                         data[5] = detalleXDia.getCantHorasAl100();

                                      
                                     listaTuplasDetallesXDia.add(data);
                                 }
                             }
                        }
                    }
                }            
        }
        return listaTuplasDetallesXDia;        
    }
    
    
    public List<Object> getListaHerramientas( Date fechaDesde, Date fechaHasta) {
        List<Object> listaTuplasDetallesXDia = new ArrayList<Object>();
        
            if (ejecucion != null) {
                List<TareaPlanificacion> todasTareas;
                if(hashTarea>0) {
                    todasTareas = tareaSeleccionada.obtenerTodaslasSubtareasRecursivamente(true);
                }
                else {
                   todasTareas = PlanificacionUtils.getTodasTareasPlanificacion(ejecucion);
                }
                for (int i = 0; i < todasTareas.size(); i++) {
                    TareaEjecucion tarea = (TareaEjecucion)todasTareas.get(i);
                    List<PlanificacionXHerramienta> listaHerramientas = tarea.getHerramientas();
                    for (int j = 0; j < listaHerramientas.size(); j++) {
                        EjecucionXHerramienta herramientaEjecucion = (EjecucionXHerramienta)listaHerramientas.get(j);
                        List<EjecucionXHerramientaXDia> listaEjecucionXHerramientaXDia = herramientaEjecucion.getUsoHerramientasXdia();
                        
                        
                        for (EjecucionXHerramientaXDia herramientaXDia: listaEjecucionXHerramientaXDia) {
                            
                           if(fechaDesde==null || FechaUtil.fechaMayorOIgualQue( herramientaXDia.getFecha(), fechaDesde))
                            {
                                if(fechaHasta==null || FechaUtil.fechaMayorQue(FechaUtil.fechaMas(fechaHasta, 1),herramientaXDia.getFecha()))
                                {
                                    NTupla nt = new NTupla(herramientaEjecucion.hashCode());
                                    nt.setNombre(herramientaEjecucion.getHerramienta().getNombre());
                                    nt.setData(herramientaXDia); 
                                    NTupla fecha=new NTupla();
                                    fecha.setNombre(FechaUtil.getFecha(herramientaXDia.getFecha()));
                                    fecha.setData(herramientaXDia.getFecha());

                                    Object[] data = new Object[4];
                                    //Interasante chanchada esta... :D
                                    data[0] = nt;
                                    data[1] = new Tupla(tarea.hashCode(),tarea.getNombre());
                                    data[2] = fecha;
                                    data[3] = herramientaXDia.getHorasUtilizadas();

                                    
                                    listaTuplasDetallesXDia.add(data);
                                }
                           }
                        }
                    }
                }            
        }
        return listaTuplasDetallesXDia;        
    }
    
    public List<Object> getListaMateriales() {
        List<Object> listaTuplasMateriales = new ArrayList<Object>();
        
            if (ejecucion != null) {
                List<TareaPlanificacion> todasTareas;
                if(hashTarea>0) {
                    todasTareas = tareaSeleccionada.obtenerTodaslasSubtareasRecursivamente(true);
                }
                else {
                   todasTareas = PlanificacionUtils.getTodasTareasPlanificacion(ejecucion);
                }
                for (int i = 0; i < todasTareas.size(); i++) {
                    TareaEjecucion tarea = (TareaEjecucion)todasTareas.get(i);
                    List<PlanificacionXMaterial> listaMateriales = tarea.getMateriales();
                    for (int j = 0; j < listaMateriales.size(); j++) {
                        EjecucionXMaterial materialEjecucion = (EjecucionXMaterial)listaMateriales.get(j);
                        RecursoXProveedor rxp = materialEjecucion.getMaterial();
                            RecursoEspecifico recesp = RecursosUtil.getRecursoEspecifico(rxp);
                       
                          
                                    NTupla nt = new NTupla(materialEjecucion.hashCode());
                                    nt.setNombre(recesp.getNombre());
                                    nt.setData(materialEjecucion); 
                                    
                                    Object[] data = new Object[5];
                                    //Interasante chanchada esta... :D
                                    data[0] = nt;
                                    data[1] = new Tupla(tarea.hashCode(),tarea.getNombre());
                                    data[2] = materialEjecucion.getMaterialPlanificado().getCantidad();
                                    data[3] = materialEjecucion.getCantidad();
                                    data[4] = materialEjecucion.getPrecioUnitario();
                                    
                                    listaTuplasMateriales.add(data);
                                
                           
                        
                    }
                }            
        }
        return listaTuplasMateriales;        
    }
    
     public List<Object> getListaAlquileresCompras() {
        List<Object> listaTuplasAlquileresCompras = new ArrayList<Object>();
        
            if (ejecucion != null) {
                List<TareaPlanificacion> todasTareas;
                if(hashTarea>0) {
                    todasTareas = tareaSeleccionada.obtenerTodaslasSubtareasRecursivamente(true);
                }
                else {
                   todasTareas = PlanificacionUtils.getTodasTareasPlanificacion(ejecucion);
                }
                for (int i = 0; i < todasTareas.size(); i++) {
                    TareaEjecucion tarea = (TareaEjecucion)todasTareas.get(i);
                    List<PlanificacionXAlquilerCompra> listaAlquileresCompras = tarea.getAlquilerCompras();
                    for (int j = 0; j < listaAlquileresCompras.size(); j++) {
                        EjecucionXAlquilerCompra alquilerEjecucion = (EjecucionXAlquilerCompra)listaAlquileresCompras.get(j);                       
                           
                       
                          
                                    NTupla nt = new NTupla(alquilerEjecucion.hashCode());
                                    nt.setNombre(alquilerEjecucion.getDescripcion());
                                    nt.setData(alquilerEjecucion); 
                                    
                                    Object[] data = new Object[5];
                                    //Interasante chanchada esta... :D
                                    data[0] = nt;
                                    data[1] = new Tupla(tarea.hashCode(),tarea.getNombre());
                                    data[2] = alquilerEjecucion.getAlquilerCompraPlanificado().getCantidad();
                                    data[3] = alquilerEjecucion.getCantidad();
                                    data[4] = alquilerEjecucion.getPrecioUnitario();

                                    
                                    listaTuplasAlquileresCompras.add(data);
                                
                           
                        
                    }
                }            
        }
        return listaTuplasAlquileresCompras;        
    }
     
   public List<Object> getListaAdicionales() {
        List<Object> listaTuplasAdicionales = new ArrayList<Object>();
        
            if (ejecucion != null) {
                
              List<EjecucionXAdicional> listaMateriales = ejecucion.getAdicionales();
              for (int j = 0; j < listaMateriales.size(); j++) {
                   EjecucionXAdicional adicionalEjecucion = listaMateriales.get(j);
                   SubObraXAdicionalModif soxad = adicionalEjecucion.getAdicionalPlanificado();
                       
                          
                   NTupla nt = new NTupla(adicionalEjecucion.hashCode());
                   nt.setNombre(soxad.getTipoYDescripcion());
                   nt.setData(adicionalEjecucion); 
                                    
                   Object[] data = new Object[4];
                   //Interasante chanchada esta... :D
                   data[0] = nt;
                   data[1] = soxad.getCantidad();
                   data[2] = adicionalEjecucion.getCantidad();
                   data[3] = adicionalEjecucion.getPrecioUnitario();
                                    
                   listaTuplasAdicionales.add(data);
                                
                   
                }            
        }
        return listaTuplasAdicionales;        
    }

    /**
     * Retorna la lista de los tipos de tarea
     * @return 
     */
    public List<Tupla> cargarTiposDeTarea() {
        List<Tupla> res = new ArrayList<Tupla>();
        try {
            Iterator<TipoTarea> tipoTareas = HibernateUtil.getSession().createQuery("FROM TipoTarea").iterate();
            while(tipoTareas.hasNext()){
                TipoTarea tipoTarea = tipoTareas.next();
                res.add(new Tupla(tipoTarea.getId(),tipoTarea.getNombre()));
            }
        } catch (Exception ex) {
            Logger.getLogger(GestorPlanificacionDatosGenerales.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    /**
     * MMMMMMMmmmmm....
     * @return 
     */
    public List<TareaPlanificacion> getTareasPadres() {
        if(this.ejecucion!=null)
        {
            return ejecucion.getTareas();
        }
        return new ArrayList<TareaPlanificacion>();
    }

    /**
     * True si se puede modificar la Ejecucion, False sino.
     * TODO: Agregarle LOGICA !!!
     * @return 
     */
    public boolean esEjecucionEditable() {
        return true;
    }

    /**
     * Actualiza el "Nombre" de la tarea cuando pierde foco el componente.
     * @param nombre 
     */
    public void actualizarNombreTarea(String nombre) {
        TareaEjecucion t = getTareaSeleccionada();
        if(t!=null){
            t.setNombre(nombre);
        }
    }

    /**
     * Actualiza las "Observaciones" de la tarea cuando pierde foco el componente.
     * @param nombre 
     */    
    public void actualizarObservacionesTarea(String observaciones) {
        TareaEjecucion t = getTareaSeleccionada();
        if(t!=null){
            t.setObservaciones(observaciones);
        }
    }

    /**
     * Actualiza el "Tipo" de la tarea cuando pierde foco el componente.
     * @param nombre 
     */        
    public void actualizarTipoTarea(Tupla tp) {
        // Busco el tipo de Tarea
        TipoTarea tt = buscarTipoTareaPorId(tp.getId());
        
        TareaEjecucion t = getTareaSeleccionada();
        if(t!=null && tt!=null){
            t.setTipoTarea(tt);
        } 
    }
    
    /**
     * Retorna el Tipo Tarea segun el ID del Objeto
     * @param id
     * @return 
     */
    private TipoTarea buscarTipoTareaPorId(int id) {
        TipoTarea tt = null;
        try
        {
            HibernateUtil.beginTransaction();
            tt = (TipoTarea)HibernateUtil.getSession().load(TipoTarea.class,id);   
            HibernateUtil.commitTransaction();
        }
        catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error:"+e.getMessage());
        } 
        return tt;
    }

    /**
     * Actualiza el "Estado" de la tarea cuando pierde foco el componente.
     * @param nombre 
     */  
    public void actualizarEstadoTarea(Tupla tpe) {
        if(tpe!=null){
            TareaEjecucion t = getTareaSeleccionada();
            if(t!=null){
                String nombreEstado = TareaEjecucion.getNombreEstadoSegunID(tpe.getId());
                t.setEstado(nombreEstado);
            }
        }
    }

    /**
     * Actualiza la "Fecha de Inicio" de la tarea cuando pierde foco el componente.
     */      
    public void actualizarFechaInicioTarea(Date fechaInicio) throws IllegalArgumentException{
        TareaEjecucion t = getTareaSeleccionada();
        if(t!=null){
            
            // Valido
            if(FechaUtil.fechaMayorQue(fechaInicio, t.getFechaFin())){
                throw new IllegalArgumentException("La Fecha de Inicio no puede ser superior a la de Fin");
            }
            
            if (PlanificacionUtils.esFechaValidaParaHija(ejecucion, true, fechaInicio, t)) {
                if (PlanificacionUtils.esFechaValidaParaSusHijas(true, fechaInicio, t)) {
                    t.setFechaInicio(fechaInicio);
                } else {
                    pantalla.refreshGanttAndData();
                }
            } else {
                // La fecha que se paso es invalida, calculo la del padre y se la seteo
                TareaPlanificacion padre = PlanificacionUtils.getTareaPadre(ejecucion, t);
                if (padre != null) {
                    t.setFechaInicio(padre.getFechaInicio());
                }
                // Ahora al Gantt ???
                pantalla.refreshGanttAndData();
            }
            t.setFechaInicio(fechaInicio);
        }
    }

    /**
     * Actualiza la "Fecha de Fin" de la tarea cuando pierde foco el componente.
     */        
    public void actualizarFechaFinTarea(Date fechaFin)  throws IllegalArgumentException{
        TareaEjecucion t = getTareaSeleccionada();
        if(t!=null){
            
            // Valido
            if(FechaUtil.fechaMayorQue(t.getFechaInicio(),fechaFin)){
                throw new IllegalArgumentException("La Fecha de Fin no puede ser menor a la de Inicio");
            }
            
            if(PlanificacionUtils.esFechaValidaParaHija(ejecucion,false,fechaFin,t))
            {
                if(PlanificacionUtils.esFechaValidaParaSusHijas(false,fechaFin,t))
                {
                    t.setFechaFin(fechaFin);
                }
                else
                {
                    pantalla.refreshGanttAndData();
                }
            }
            else
            {
                // La fecha que se paso es invalida, calculo la del padre y se la seteo
                TareaPlanificacion padre = PlanificacionUtils.getTareaPadre(ejecucion, t);
                if(padre!=null)
                {
                    t.setFechaFin(padre.getFechaFin());
                }
                // Ahora al Gantt ???
                pantalla.refreshGanttAndData();
            }            
            
            t.setFechaFin(fechaFin);
        }
    }

    public void setTareaGanttSeleccionada(int id) {
        System.out.println("Tarea del gantt Seleccionada:"+id);
        setearTareaEjecucionSeleccionada(id);
    }
    
    /**
     * Cambio de Fecha la Tarea Entera
     * @param date 
     */
    public void tareaCambioFecha(Date date) {
        // Muevo Toda la tarea
        TareaEjecucion nuevaTarea = getTareaSeleccionada();  
        if(nuevaTarea!=null)
        {
            // Calculo la duracion:
            int dias = FechaUtil.diasDiferencia(nuevaTarea.getFechaInicio(),date);
            
            // x dias para la izquierda o derecha?
            Date res = FechaUtil.fechaMas(nuevaTarea.getFechaFin(),dias);
            
            if(PlanificacionUtils.esFechaValidaParaHija(ejecucion,true,date,nuevaTarea) &&
               PlanificacionUtils.esFechaValidaParaHija(ejecucion,false,res,nuevaTarea)  )
            {
                if(PlanificacionUtils.esFechaValidaParaSusHijas(true,date,nuevaTarea) && 
                   PlanificacionUtils.esFechaValidaParaSusHijas(false,res,nuevaTarea))
                {
                    nuevaTarea.setFechaInicio(date);

                    if(nuevaTarea.getFechaInicio().after(date))
                    {
                        // Derecha
                        nuevaTarea.setFechaFin(res);
                    }
                    else
                    {
                        // Izquierda
                        nuevaTarea.setFechaFin(res);
                    }
                }
                else
                {
                    pantalla.refreshGanttAndData();
                }
            }
            else
            {
                // Ahora al Gantt ???
                pantalla.refreshGanttAndData();
            }
        }
    }    

    public Date getFechaInicioObra() {
        if(this.pedidoDeObra!=null){
            return this.pedidoDeObra.getFechaInicio();
        }
        return new Date();
    }

    public Date getFechaFinObra() {
        if(this.pedidoDeObra!=null){
            return this.pedidoDeObra.getFechaFin();
        }
        return new Date();
    }
    
    public void setDetalleRRHHXDiaSeleccionado(DetalleTareaEjecucionXDia detalleXDiaSeleccionado) {
        this.detalleRRHHXDiaSeleccionado=detalleXDiaSeleccionado;
    }
    
    public DetalleTareaEjecucionXDia getDetalleRRHHXDiaSeleccionado() {
        return this.detalleRRHHXDiaSeleccionado;
    }
    
    public void setDetalleRRHHSeleccionado(DetalleTareaEjecucion detalleSeleccionado) {
        this.detalleRRHHSeleccionado=detalleSeleccionado;
    }
    
    public DetalleTareaEjecucion getDetalleRRHHSeleccionado() {
        return this.detalleRRHHSeleccionado;
    }

    /**
     * Guarda en DB los cambios de la ejecución
     * @return 
     */
    public boolean guardar() {
        try
        {
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().saveOrUpdate(this.ejecucion);
                for (int i = 0; i < this.ejecucion.getTareas().size(); i++) {
                    TareaPlanificacion tarea = this.ejecucion.getTareas().get(i);
                    HibernateUtil.getSession().saveOrUpdate(tarea);
                }
            HibernateUtil.commitTransaction();

        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error: "+e.getMessage());
            return false;
        }   
        return true;
    }

    public void actualizarDescripcionEjecucion(String text) {
        this.ejecucion.setDescripcion(text);
    }

}
