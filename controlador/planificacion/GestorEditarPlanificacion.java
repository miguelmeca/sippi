package controlador.planificacion;

import com.hackelare.coolgantt.CoolGanttPhase;
import config.Iconos;
import controlador.GestorAbstracto;
import controlador.planificacion.cotizacion.GestorEditarCotizacionModificada;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import modelo.*;
import org.hibernate.Session;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
import vista.gui.sidebar.IconTreeModel;
import vista.gui.sidebar.TreeEntry;
import vista.planificacion.ArbolDeTareasTipos;
import vista.planificacion.EditarPlanificacion;
import vista.planificacion.arbolTareas.ArbolIconoNodo;
import vista.planificacion.cotizacion.EditarCotizacionModificada;


/**
 * Las Tuplas tienen que tener el formato:
 *
 * @author Administrador
 */
public class GestorEditarPlanificacion extends GestorAbstracto implements IGestorPlanificacion{

    private EditarPlanificacion _pantalla;
    private Session sesion;
    private PedidoObra pedidoDeObra;
    private Planificacion planificacion;

    @Override
    public Planificacion getPlanificacion() {
        return planificacion;
    }

    public void setPlanificacion(Planificacion planificacion) {
        this.planificacion = planificacion;
    }

    public PedidoObra getPedidoDeObra() {
        return pedidoDeObra;
    }
    
    private HashMap<Integer,Boolean> generatedIdGantt;

    public GestorEditarPlanificacion(EditarPlanificacion _pantalla, int idPedidoDeObra) {
        this._pantalla = _pantalla;
        generatedIdGantt = new HashMap<Integer, Boolean>();

        try {
            sesion = HibernateUtil.getSession();
            
            HibernateUtil.beginTransaction();
            this.pedidoDeObra = (PedidoObra) sesion.load(PedidoObra.class, idPedidoDeObra);
            this.planificacion = pedidoDeObra.getPlanificacion();
            HibernateUtil.commitTransaction();
            
            if(this.planificacion!=null)
            {
                for (int i = 0; i < this.planificacion.getTareas().size(); i++) {
                    TareaPlanificacion tp = this.planificacion.getTareas().get(i);
                    if(tp!=null)
                    {
                        generatedIdGantt.put(tp.getIdTareaGantt(), Boolean.TRUE);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            HibernateUtil.rollbackTransaction();
            mostrarMensajeError("No se pudo cargar el Pedido ni la planificaciÃ³n asociada");
            
        }

    }

    public List getListaSubObras() {

        ArrayList<NTupla> lista = new ArrayList<NTupla>();

        try {
            if (this.planificacion != null && this.planificacion.getId() != 0) {
                List<SubObraModificada> listaSO = (List) this.planificacion.getCotizacion().getSubObras();
                for (int i = 0; i < listaSO.size(); i++) {
                    SubObraModificada som = listaSO.get(i);
                    NTupla nt1 = new NTupla(som.hashCode());
                    nt1.setNombre(som.getNombre());
                    lista.add(nt1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeError("No se pudo cargar las subobras del pedido");
        }
        return lista;
    }

    public List getListaTareasXSubObra(int hashSubObra) {

        ArrayList<NTupla> lista = new ArrayList<NTupla>();
        try {
            if (this.planificacion != null && this.planificacion.getId() != 0) {
                List<SubObra> listaSO =  this.planificacion.getCotizacion().getSubObras();
                for (int i = 0; i < listaSO.size(); i++) {
                    SubObraModificada som = (SubObraModificada)listaSO.get(i);
                    if (som.hashCode() == hashSubObra) {
                        List<SubObraXTarea> listaTareasMod = som.getTareas();
                        for (int j = 0; j < listaTareasMod.size(); j++) {
                            SubObraXTarea starea = (SubObraXTarea) listaTareasMod.get(j);
                            //NTupla nt1 = new NTupla(starea.getId());
                            NTupla nt1 = new NTupla(starea.hashCode());
                            nt1.setNombre(starea.getNombre());
                            lista.add(nt1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeError("No se pudo cargar las subobras del pedido");
        }
        return lista;
    }

    public void mostrarDatosGenerales(int idPedidoDeObra) {

        try {
            sesion = HibernateUtil.getSession();
            HibernateUtil.beginTransaction();
            pedidoDeObra = (PedidoObra) sesion.load(PedidoObra.class, idPedidoDeObra);

            if(pedidoDeObra.getNombre()!=null && pedidoDeObra.getNombre().length()>60)
            {
                _pantalla.setLblObraNombre(pedidoDeObra.getNombre().substring(0, 60) + "...");
            }
            else
            {
                _pantalla.setLblObraNombre(pedidoDeObra.getNombre());
            }
            
            _pantalla.setLblObraFechaFin(FechaUtil.getFecha(pedidoDeObra.getFechaFin()));
            _pantalla.setLblObraFechaInicio(FechaUtil.getFecha(pedidoDeObra.getFechaInicio()));
            _pantalla.setLblObraLugar(pedidoDeObra.getPlanta().getDomicilio().toString());
            _pantalla.setLblObraPlanta(pedidoDeObra.getPlanta().getRazonSocial());

            if (this.planificacion != null && this.planificacion.getCotizacion() != null) {
                _pantalla.setFechaInicioPlanif(this.planificacion.getFechaInicio());
                _pantalla.setFechaFinPlanif(this.planificacion.getFechaFin());
                _pantalla.setEstadoPlanificacion(this.planificacion.getEstado());
            }

            if (this.planificacion != null && this.planificacion.getCotizacion() != null && this.planificacion.getCotizacion().getCotizacionOriginal() != null) {
                _pantalla.setTxtNroCotizacion(this.planificacion.getCotizacion().getCotizacionOriginal().getNroCotizacion());
                _pantalla.setCotizacionMontoTotal(String.valueOf(this.planificacion.getCotizacion().getCotizacionOriginal().CalcularTotal()));
            }
            
            if (this.planificacion != null)
            {
                _pantalla.setDescripcionPlanificacion(this.planificacion.getDescripcion());
            }
            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            e.printStackTrace();
            HibernateUtil.rollbackTransaction();
            mostrarMensajeError("No se pudo cargar los datos generales de la cotizacion");
        }

    }

    private void mostrarMensajeError(String msg) {
        System.err.println("[ERROR] " + msg);
        _pantalla.mostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error!", msg);
    }
    
    private void mostrarMensajeExito(String msg) {
        _pantalla.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, "Exito!", msg);
    }    

    public void cargarArbolRecursos(int hashSubObra, JTree treeRecursos) {
       
       TreeEntry nodoRoot = new TreeEntry("Recursos",Iconos.ICONO_HERRAMIENTA);
               
       try {
            if (this.planificacion != null && this.planificacion.getId() != 0) {
                List<SubObraModificada> listaSO = (List) this.planificacion.getCotizacion().getSubObras();
                for (int i = 0; i < listaSO.size(); i++) {
                    SubObraModificada som = listaSO.get(i);
                    if (som.hashCode() == hashSubObra) {
                        
                        // CARGO LOS RECURSOS !!
                        // HERRAMIENTAS
                        TreeEntry nodoHerramientas = new TreeEntry("Herramientas",Iconos.ICONO_HERRAMIENTAS);
                        nodoRoot.add(nodoHerramientas);
                        
                        List<SubObraXHerramienta> listaHerramientas = som.getHerramientas();
                        for (int j = 0; j < listaHerramientas.size(); j++) {
                            SubObraXHerramienta herr = (SubObraXHerramienta)listaHerramientas.get(j);
                            TreeEntry subNodoHerramientas = new TreeEntry(herr.getHerramienta().getRecursoEsp().getNombre() + ":" +herr.getHerramienta().getNroSerie(),Iconos.ICONO_HERRAMIENTA);
                            //subNodoHerramientas.setId(herr.getId());
                            subNodoHerramientas.setId(herr.hashCode());
                            subNodoHerramientas.setTipo(ArbolDeTareasTipos.TIPO_HERRAMIENTA);
                            nodoHerramientas.add(subNodoHerramientas);
                        }
                        
                        // MATERIALES
                        // NECESITO idRecursoEspecifico y idMaterial
                        TreeEntry nodoMateriales = new TreeEntry("Materiales",Iconos.ICONO_MATERIALES);
                        nodoRoot.add(nodoMateriales);
                        
                        List<SubObraXMaterial> listaMateriales =  som.getMateriales();
                        for (int j = 0; j < listaMateriales.size(); j++) {
                            SubObraXMaterial mat = (SubObraXMaterial)listaMateriales.get(j);
                            String nombre = "";
                                // Busco, un Recurso Especifico que tenga ese recurso x proveedor
                                 try
                                 {
                                    RecursoXProveedor rxp = mat.getMaterial();
                                    RecursoEspecifico RE= (RecursoEspecifico)HibernateUtil.getSession().createQuery("from RecursoEspecifico RE where :cID in elements(RE.proveedores)").setParameter("cID", rxp).uniqueResult();  
                                    if(RE!=null)
                                    {
                                        nombre += RE.getNombre();
                                    }
                                    
                                    Recurso R = (Recurso)HibernateUtil.getSession().createQuery("from Recurso RE where :cID in elements(RE.recursos)").setParameter("cID", RE).uniqueResult();  
                                    if(R!=null)
                                    {
                                        nombre = R.getNombre()+" "+nombre;
                                    }                                    
                                    
                                 }
                                 catch(Exception e)
                                 {
                                     e.printStackTrace();
                                     System.err.println("[ERROR] No se pudo encontrar el nombre del Material");
                                 }
                                 
                            TreeEntry subNodoMaterial = new TreeEntry(nombre,Iconos.ICONO_MATERIAL);
                            //subNodoMaterial.setId(mat.getId());
                            subNodoMaterial.setId(mat.hashCode());
                            subNodoMaterial.setTipo(ArbolDeTareasTipos.TIPO_MATERIAL);
                            nodoMateriales.add(subNodoMaterial);
                        }
                        
                        // ALQUILERES / COMPRAS
                        TreeEntry nodoAlqComps = new TreeEntry("Alquileres / Compras",Iconos.ICONO_ALQUILERESCOMPRAS);
                        nodoRoot.add(nodoAlqComps);
                        
                        List<SubObraXAlquilerCompra> listaAlquileres = som.getAlquileresCompras();
                        for (int j = 0; j < listaAlquileres.size(); j++) {
                            SubObraXAlquilerCompra alqcomp = (SubObraXAlquilerCompra)listaAlquileres.get(j);
                            TreeEntry subNodoAlquComp = new TreeEntry(alqcomp.getTipoAlquilerCompra().getNombre()+" "+alqcomp.getDescripcion(),Iconos.ICONO_ALQUILERCOMPRA);
                            //subNodoAlquComp.setId(alqcomp.getId());
                            subNodoAlquComp.setId(alqcomp.hashCode());
                            subNodoAlquComp.setTipo(ArbolDeTareasTipos.TIPO_ALQUILERCOMPRA);
                            nodoAlqComps.add(subNodoAlquComp);
                        }
                        
                        
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeError("No se pudo cargar las subobras del pedido");
        } 
        
        IconTreeModel modelo = new IconTreeModel();
        modelo.RellenarArbol(nodoRoot);
        treeRecursos.setModel(modelo);
    }
    
    
    public List<TareaPlanificacion> getListaTareasPlanificadas()
    {
        List<TareaPlanificacion> listaTareas = new ArrayList<TareaPlanificacion>();
        if(this.planificacion!=null)
        {
            listaTareas = this.planificacion.getTareas();
        }
        else
        {
            mostrarMensajeError("No se pudo cargar la planificaciÃ³n, por lo tanto no se mostrarÃ¡ el Gantt");
        }
        return listaTareas;
    }
    
    public int generarIdTareaGantt()
    {
        int id = 1;
        while (id<=9999) {            
            if(!generatedIdGantt.containsKey(id))
            {
                generatedIdGantt.put(id, Boolean.TRUE);
                return id;
            }
            else
            {
                id++;
            }
        }
        return 0;
    }


    public int getCotizacionPlanificada() {
        return  this.planificacion.getCotizacion().getCotizacionOriginal().getId();
    }
    

    
    public void agregarNuevaTareaDesdeCotizacion(int hashTaraCotizada, TareaPlanificacion padre, Date fechaInicio, Date fechaFin)
    {
        TareaPlanificacion nuevaTarea=crearTarea( hashTaraCotizada, fechaInicio, fechaFin);
        
        if(nuevaTarea== null && nuevaTarea.getTareaCotizada()==null)
        {
            mostrarMensajeError("No se pudo cargar la Tarea Original Cotizada");
            return;
        }
        
        if(padre==null)
        {
            this.planificacion.getTareas().add(nuevaTarea); 
        }
        else
        {
            
            // Agrego el hijo al padre
            if(padre!=null)
            {
                
                padre.addSubTarea(nuevaTarea);
            }
            else
            {
                mostrarMensajeError("No se pudo encontrar la Tarea padre a la que se le quiere agregar la SubTarea");
                return;
            } 
        }
        int hashPadre;
        if(padre!=null)
        {hashPadre=padre.hashCode();}
        else
        {hashPadre=0;}
       _pantalla.agregarNuevaTarea(nuevaTarea.hashCode(),nuevaTarea.getNombre(),hashPadre);
        _pantalla.refreshGanttAndData();
    }
    
    public void agregarNuevaTareaGantt(int hashTareaCotizada, String nombre, int idTareaGanttPadre, int nivel)
    {
        //TareaPlanificacion nuevaTarea=crearTarea(nombre, hash);
        //FEcha hardcodeadea:
        TareaPlanificacion nuevaTarea=crearTarea(hashTareaCotizada, this.planificacion.getFechaInicio(), fechaMas(this.planificacion.getFechaInicio(),3)); 
        //TODO
        //IMPORTANTE!!!!  IUGA arregle el metodo crearTarea para que recibieralas fechas de la tarea. La ventana ya esta creada, solo te queda usarla        
        
        if(nuevaTarea== null && nuevaTarea.getTareaCotizada()==null)
        {
            mostrarMensajeError("No se pudo cargar la Tarea Original Cotizada");
            return;
        }
        
        if(idTareaGanttPadre<=0)
        {
            this.planificacion.getTareas().add(nuevaTarea); 
        }
        else
        {
            // CARGO LA TAREA PADRE
            // Tengo el idGantt del padre, ahora tengo que conseguir el id de la Tarea y la tarea en si
            TareaPlanificacion padre = null;
            for (int i = 0; i < this.planificacion.getTareas().size(); i++) {
                
                //TODO: IUGA, me parece q aca estas buscando solo en las tareas y no en las subtareas tmbn
                //Hice esto, fijate si te sirve:
                //TareaPlanificacion padre = null;
                //padre=planificacion.buscarSubTarea(idTareaPadre);
                TareaPlanificacion tarea = this.planificacion.getTareas().get(i);
                if(tarea.getIdTareaGantt()==idTareaGanttPadre)
                {
                    padre = tarea;
                    continue;//TODO: IUGA, no sera break? Un continue aca, no hace nada pq no hay codigo despues
                }
            }
            // Agrego el hijo al padre
            if(padre!=null)
            {
                padre.addSubTarea(nuevaTarea);
            }
            else
            {
                mostrarMensajeError("No se pudo encontrar la Tarea padre a la que se le quiere agregar la SubTarea");
                return;
            } 
        }
        // Agrego el nivel para mostrar en el Gantt
        String nombreGantt = nuevaTarea.getNombre();
        for (int i = 0; i < nivel; i++) {
            nombreGantt+="@";
        }
        _pantalla.refreshGanttAndData();
        _pantalla.inicializarArbolDeTareas();

    }
    
    private Date fechaMas(Date fch, int horas) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, horas);
        return new Date(cal.getTimeInMillis());
    }    
    
    private TareaPlanificacion crearTarea(int hashTaeraCotizada, Date fechaInicio, Date fechaFin)
    { // VEO QUE NO ESTE REPETIDA
        TareaPlanificacion nuevaTarea = null;
        nuevaTarea=planificacion.buscarTareaPorHashTareaCotizada(hashTaeraCotizada);
        if(nuevaTarea!=null)
        {
            mostrarMensajeError("La Tarea: "+nuevaTarea.getNombre()+"\nya se encuentra agregada");
            return null;
        }
        
        // CArgo la SubObra X Tarea Planificada Mod
        SubObraXTareaModif soxtm; 
        try
        {   
                       
             soxtm=(SubObraXTareaModif)this.planificacion.getCotizacion().getSubObraXTareaPorHash(hashTaeraCotizada);
             }catch(Exception e)
        {
           e.printStackTrace();
           mostrarMensajeError("No se pudo cargar la Cotizacion Original Modificada");
           return null;
        }
           // CREO LA NUEVA TAREA
        nuevaTarea = new TareaPlanificacion(soxtm);
       // nuevaTarea.setId(id);
        nuevaTarea.setFechaInicio(fechaInicio);
        nuevaTarea.setFechaFin(fechaFin);
        nuevaTarea.setIdTareaGantt(generarIdTareaGantt());    
             
        return nuevaTarea;
    }
    
    public DefaultTreeModel cargarModeloArbolTareas()
    {
        ArbolIconoNodo raiz = new ArbolIconoNodo(0,ArbolDeTareasTipos.TIPO_OBRA, pedidoDeObra.getNombre(),Iconos.ICONO_OBRA);
        DefaultTreeModel modelo = new DefaultTreeModel(raiz);
        
        //Agregar todas las tareas, subtareas y recursos de cada obra        
        cargarTareasEnArbol(modelo, planificacion.getTareas(),raiz,true);       
        
        return modelo;
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
    
    
    public void asociarRecurso(int hashRecuso, String nombre,int hashTareaPadre, String tipoRecurso)
    {
        //Veo si es un recurso
        String[] sinHijos=ArbolDeTareasTipos.getSinHijos();
        boolean esRecurso=false;
        for (int i = 0; i < sinHijos.length; i++) {
           if(sinHijos[i].equals(tipoRecurso))
           {
             esRecurso=true;
             break;
           }
        }
        if(!esRecurso)
        {return;}
        
        if(this.planificacion!=null)
        {
            
            // CARGO LA TAREA PADRE
            TareaPlanificacion tareaPadre = null;
            tareaPadre= planificacion.buscarTareaPorHash(hashTareaPadre);
            boolean exito=false;
            CotizacionModificada cotizacionMofificada=planificacion.getCotizacion();
            
            
            // Agrego el hijo al padre
            if(tareaPadre!=null)
            {
                 
                try {
                    sesion = HibernateUtil.getSession();
                    
                    //Si tuviera mas tiempo hubiera puesto el hashmap de recurso en TareaPlanificacion... bue.. ifs nomas
                    if(tipoRecurso.equals(ArbolDeTareasTipos.TIPO_ALQUILERCOMPRA))
                    {
                       //SubObraXAlquilerCompraModif alquilerCompra = (SubObraXAlquilerCompraModif) sesion.load(SubObraXAlquilerCompraModif.class, idRecuso);
                       SubObraXAlquilerCompraModif alquilerCompra = (SubObraXAlquilerCompraModif)cotizacionMofificada.getSubObraXAlquilerCompraPorHash(hashRecuso);
                        exito=tareaPadre.agregarAlquilerCompraCotizacion(alquilerCompra);  
                    }
                    if(tipoRecurso.equals(ArbolDeTareasTipos.TIPO_MATERIAL))
                    {
                       //SubObraXMaterialModif material = (SubObraXMaterialModif) sesion.load(SubObraXMaterialModif.class, idRecuso);
                       SubObraXMaterialModif material = (SubObraXMaterialModif) cotizacionMofificada.getSubObraXMaterialPorHash(hashRecuso);
                        exito=tareaPadre.agregarMaterialCotizacion(material);   
                    }
                    if(tipoRecurso.equals(ArbolDeTareasTipos.TIPO_HERRAMIENTA))
                    {
                      //SubObraXHerramientaModif herramienta = (SubObraXHerramientaModif) sesion.load(SubObraXHerramientaModif.class, idRecuso);
                      SubObraXHerramientaModif herramienta = (SubObraXHerramientaModif) cotizacionMofificada.getSubObraXHerramientaPorHash(hashRecuso);
                        exito=tareaPadre.agregarHerramientaCotizacion(herramienta);   
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarMensajeError("No se pudo cargar el recurso de cotizacion");
                    return;
                } 
               
                if(!exito)           
                {
                    mostrarMensajeError("El recurso ya se encuentara asociado a la tarea.");
                } else {
                    // _pantalla.asociarRecursoATareaArbol(hashRecuso,nombre, hashTareaPadre, tipoRecurso);
                }
            }
            else
            {
                mostrarMensajeError("No se pudo encontrar la Tarea padre a la que se le quiere agregar la SubTarea");
                return;
            } 
           
        }
        
        
    }
    
    public boolean quitarTarea(TareaPlanificacion tarea)
    {
       boolean eliminada= planificacion.eliminarTareaPorHash(tarea.hashCode(), true);
       return eliminada;
        
    }
    public void quitarRecurso(int id, String nombre,int idTareaPadre,String tipoRecurso)
    {
        
    }
    

    @Override
    public TareaPlanificacion getTareaActual() {
        return null;
    }

    @Override
    public void refrescarPantallas() {
        this._pantalla.refreshGanttAndData();

    }

    public void tareaCambioFecha(int id, Date date) {
        // Muevo Toda la tarea
        TareaPlanificacion nuevaTarea = planificacion.buscarTareaPorIdTareaGantt(id);    
        if(nuevaTarea!=null)
        {
            // Calculo la duracion:
            int dias = FechaUtil.diasDiferencia(nuevaTarea.getFechaInicio(),date);
            
            // x dias para la izquierda o derecha?
            Date res = FechaUtil.fechaMas(nuevaTarea.getFechaFin(),dias);
            
            if(PlanificacionUtils.esFechaValidaParaHija(planificacion,true,date,nuevaTarea) &&
               PlanificacionUtils.esFechaValidaParaHija(planificacion,false,res,nuevaTarea)  )
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
                    _pantalla.refreshGanttAndData();
                }
            }
            else
            {
                // Ahora al Gantt ???
                _pantalla.refreshGanttAndData();
            }
        }
    }

    public void tareaCambioFechaInicio(int id, Date date) {
        TareaPlanificacion nuevaTarea = planificacion.buscarTareaPorIdTareaGantt(id);    
        if(nuevaTarea!=null)
        {
            if(PlanificacionUtils.esFechaValidaParaHija(planificacion,true,date,nuevaTarea))
            {
                if(PlanificacionUtils.esFechaValidaParaSusHijas(true,date,nuevaTarea))
                {
                    nuevaTarea.setFechaInicio(date);
                }
                else
                {
                    _pantalla.refreshGanttAndData();
                }
                
            }
            else
            {
                // La fecha que se paso es invalida, calculo la del padre y se la seteo
                TareaPlanificacion padre = PlanificacionUtils.getTareaPadre(planificacion, nuevaTarea);
                if(padre!=null)
                {
                    nuevaTarea.setFechaInicio(padre.getFechaInicio());
                }
                // Ahora al Gantt ???
                _pantalla.refreshGanttAndData();
            }
        }
    }

    public void tareaCambioFechaFin(int id, Date date) {
        TareaPlanificacion nuevaTarea = planificacion.buscarTareaPorIdTareaGantt(id);    
        if(nuevaTarea!=null)
        {
            if(PlanificacionUtils.esFechaValidaParaHija(planificacion,false,date,nuevaTarea))
            {
                if(PlanificacionUtils.esFechaValidaParaSusHijas(false,date,nuevaTarea))
                {
                    nuevaTarea.setFechaFin(date);
                }
                else
                {
                    _pantalla.refreshGanttAndData();
                }
            }
            else
            {
                // La fecha que se paso es invalida, calculo la del padre y se la seteo
                TareaPlanificacion padre = PlanificacionUtils.getTareaPadre(planificacion, nuevaTarea);
                if(padre!=null)
                {
                    nuevaTarea.setFechaFin(padre.getFechaFin());
                }
                // Ahora al Gantt ???
                _pantalla.refreshGanttAndData();
            }
        }
    }
    
    /**
     * Le pasas un id de Tarea del Gantt y te retorna el ID de la tarea
     * @param idGantt
     * @return 
     */
    public int getIdTareaFromGantt(int idGantt)
    {
         TareaPlanificacion tarea = PlanificacionUtils.getTareaFromGantt(planificacion, idGantt);
         return tarea.getId();
    }

    public TareaPlanificacion getTareaFromIDGantt(int i) {
        return PlanificacionUtils.getTareaFromGantt(this.planificacion, i);
    }

    public Date getFechaInicioObra() {
        return planificacion.getFechaInicio();
    }

    public Date getFechaFinObra() {
        return planificacion.getFechaFin();
    }
    
    public boolean eliminarSubObraPorHash(int hash_subObra)
    {
        // Recorro, encuentro la subobra y la elimino
        for (int i = 0; i < this.planificacion.getCotizacion().getSubObras().size(); i++) 
        {
            SubObra so = this.planificacion.getCotizacion().getSubObras().get(i);
            if(so.hashCode()==hash_subObra)
            {
                this.planificacion.getCotizacion().getSubObras().remove(i);
                _pantalla.actualizar(0, EditarCotizacionModificada.CALLBACK_FLAG, true,null);
                return true;
            }
        }
        return false;
    }
    public void crearSubObra(String nombre)
    {
        // VEO QUE NO REPITA EL NOMBRE
        boolean _used_name = false;
        for (int i = 0; i < this.planificacion.getCotizacion().getSubObras().size(); i++) 
        {
            SubObra so = this.planificacion.getCotizacion().getSubObras().get(i);
            if(so.getNombre().equals(nombre))
            {
                _used_name=true;
            }
        }
        
        if(_used_name==false)
        {
            SubObraModificada nuevaso = new SubObraModificada();
            nuevaso.setNombre(nombre);
            this.planificacion.getCotizacion().getSubObras().add(nuevaso);
            _pantalla.actualizar(0, EditarCotizacionModificada.CALLBACK_FLAG, true,null);
        }
    }

    /**
     * Guarda la Planificación !
     */
    public void guardarPlanificacion() {
        try
        {
            HibernateUtil.beginTransaction();
            GestorEditarCotizacionModificada.guardarCotizacion(planificacion.getCotizacion(), sesion);
            //sesion = HibernateUtil.getSession();
            Iterator<TareaPlanificacion> itT = planificacion.getTareas().iterator();
            while(itT.hasNext())
            {
                TareaPlanificacion tarea = itT.next();
                this.guardarTareaRecursiva(tarea);
            }
            sesion.save(this.planificacion);
            HibernateUtil.commitTransaction();
        }
        catch(Exception e)
        {
            mostrarMensajeError("No se pudo guardar la Planificación.\nDescripción: "+e.getMessage());
            HibernateUtil.rollbackTransaction();
        }
        mostrarMensajeExito("La Planificación se Guardo exitosamente!");
    }
    
     public void guardarTareaRecursiva(TareaPlanificacion tareaR) throws Exception
    {    
        if(tareaR.getAlquilerCompras() != null)
        {
            Iterator<PlanificacionXAlquilerCompra> itAlquilerCompra = tareaR.getAlquilerCompras().iterator();
            while(itAlquilerCompra.hasNext())
            {
                PlanificacionXAlquilerCompra pac = itAlquilerCompra.next();
                HibernateUtil.getSession().saveOrUpdate(pac.getAlquilerCompraCotizacion());
                HibernateUtil.getSession().saveOrUpdate(pac);
            }
        }
        if(tareaR.getHerramientas() != null)
        {
            Iterator<PlanificacionXHerramienta> itHerramienta = tareaR.getHerramientas().iterator();
            while(itHerramienta.hasNext())
            {
                PlanificacionXHerramienta ph = itHerramienta.next();
                HibernateUtil.getSession().saveOrUpdate(ph.getHerramientaCotizacion());
                HibernateUtil.getSession().saveOrUpdate(ph);
            }
        }
        if(tareaR.getMateriales() != null)
        {
            Iterator<PlanificacionXMaterial> itMaterial = tareaR.getMateriales().iterator();
            while(itMaterial.hasNext())
            {
                PlanificacionXMaterial pm = itMaterial.next();
                HibernateUtil.getSession().saveOrUpdate(pm.getMaterialCotizacion());
                HibernateUtil.getSession().saveOrUpdate(pm);
            }
        }
        if(tareaR.getAsignacionesEmpleados() != null)
        {
            Iterator<AsignacionEmpleadoPlanificacion> itAsignaciones = tareaR.getAsignacionesEmpleados().iterator();
            while(itAsignaciones.hasNext())
            {
                AsignacionEmpleadoPlanificacion aep = itAsignaciones.next();
                HibernateUtil.getSession().saveOrUpdate(aep.getAsignacionTareaCotizacion());
                HibernateUtil.getSession().saveOrUpdate(aep);
            }
        }
        if(tareaR.getSubtareas() != null)
        {
            Iterator<TareaPlanificacion> itSubTareas = tareaR.getSubtareas().iterator();
            while(itSubTareas.hasNext())
            {
                TareaPlanificacion tp = itSubTareas.next();
                this.guardarTareaRecursiva(tp);
            }
        }
        HibernateUtil.getSession().saveOrUpdate(tareaR);  
    }

    void cerrarVentanaEditarTarea(Object key) {
        this._pantalla.cerrarVentanaEditarTarea(key);
    }

    public TareaPlanificacion getTareaPlanificacionFromTareaGantt(CoolGanttPhase p) {
       return PlanificacionUtils.getTareaFromGantt(planificacion,p.getId());
    }

    public TareaPlanificacion getTareaPlanificacionFromHashCode(int hash) {
       return PlanificacionUtils.getTareaFromHash(planificacion, hash);
    }

    public SubObraXHerramientaModif getGastosHerramientaFromHash(String hash) {
        return (SubObraXHerramientaModif) planificacion.getCotizacion().getSubObraXHerramientaPorHash(Integer.parseInt(hash));
    }

    public SubObraXMaterialModif getGastosMaterialFromHash(String hash) {
        return (SubObraXMaterialModif) planificacion.getCotizacion().getSubObraXMaterialPorHash(Integer.parseInt(hash));
    }
    
    public SubObraXAlquilerCompraModif getGastosAlquilerCompraFromHash(String hash) {
        return (SubObraXAlquilerCompraModif) planificacion.getCotizacion().getSubObraXAlquilerCompraPorHash(Integer.parseInt(hash));
    }
        
    public boolean asignarHerramientaATarea(SubObraXHerramientaModif soxhm, TareaPlanificacion tarea, int cantidadDeHoras) {
        PlanificacionXHerramienta pxh = new PlanificacionXHerramienta(soxhm);
        pxh.setHorasAsignadas(cantidadDeHoras);
        tarea.getHerramientas().add(pxh);
        
        // Modifico el soxhm si es necesario
        // Hago los calculos de nuevo
        int cantidadHorasDisponiblesPlanificacion = PlanificacionUtils.getHorasTotalesAsignadasAHerramienta(planificacion, soxhm);
        int horasDisponibles = soxhm.getHorasDisponibles()-cantidadHorasDisponiblesPlanificacion;
        if(horasDisponibles<0)
        {
            // Me paso, modifico el soxhm
            soxhm.setCantHoras(soxhm.getCantHoras()+Math.abs(horasDisponibles));
        }       
        
        return true;
    }
    
    //Sirve tanto como para crear tareas hijas de la planificacion o para crear subtareas de una tarea
    public boolean crearNuevaTareaPlanificacionVacia(String nombre, Date inicioTarea,  Date finTarea, TareaPlanificacion tareaPadre)
    {
        try{
            TareaPlanificacion subTarea=new TareaPlanificacion();
            subTarea.setTipoTarea(subTarea.getTipoTarea());
            subTarea.setNombre(nombre);
            subTarea.setFechaInicio(inicioTarea);
            subTarea.setFechaFin(finTarea);
            subTarea.setIdTareaGantt(generarIdTareaGantt());
            if(tareaPadre!=null)
            {tareaPadre.addSubTarea(subTarea);}
            else{
                // Es una tarea de planificacion hija de la planificacion (no es subtarea)
                SubObraXTareaModif tareaCotizacionNueva= new SubObraXTareaModif();
                tareaCotizacionNueva.setNombre(subTarea.getNombre());
                tareaCotizacionNueva.setTipoTarea(subTarea.getTipoTarea());
                tareaCotizacionNueva.setObservaciones(CotizacionModificada.obsevacionSubObraXTareaDeSubObraGeneral);                
                planificacion.getCotizacion().getSubObraGeneral().addTarea(tareaCotizacionNueva);
                
                subTarea.setTareaCotizada(tareaCotizacionNueva);
                this.planificacion.addTarea(subTarea);
                int hashPadre=0;
                if(tareaPadre!=null)
                {
                    hashPadre=tareaPadre.hashCode();
                }                
                
                _pantalla.agregarNuevaTarea(tareaCotizacionNueva.hashCode(), nombre,hashPadre);
            }
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e.getStackTrace());
            return false;
        }
    }
    
    public boolean asignarMaterialATarea(SubObraXMaterialModif soxmm, TareaPlanificacion tarea, int cantidad) {
        PlanificacionXMaterial pxm = new PlanificacionXMaterial(soxmm);
        pxm.setCantidad(cantidad);
        tarea.getMateriales().add(pxm);
        
        // Modifico el soxmm si es necesario
        // Hago los calculos de nuevo
//        int cantidadDisponiblePlanificacion = PlanificacionUtils.getCantidadAsignadaAMaterial(planificacion, soxmm);
        int cantidadDisponible = soxmm.getCantidadDisponible() - pxm.getCantidad();
        if(cantidadDisponible<0)
        {
            // Me paso, modifico el soxmm
            soxmm.setCantidad(soxmm.getCantidad()+Math.abs(cantidadDisponible));
        }       
        
        return true;
    }
    
        public boolean asignarAlquilerCompraATarea(SubObraXAlquilerCompraModif soxac, TareaPlanificacion tarea, int cantidad) {
        PlanificacionXAlquilerCompra pxac = new PlanificacionXAlquilerCompra(soxac);
        pxac.setCantidad(cantidad);
        tarea.getAlquilerCompras().add(pxac);
        
        // Modifico el soxmm si es necesario
        // Hago los calculos de nuevo
//        int cantidadDisponiblePlanificacion = PlanificacionUtils.getCantidadAsignadaAMaterial(planificacion, soxmm);
        int cantidadDisponible = soxac.getCantidad() - pxac.getCantidad();
        if(cantidadDisponible<0)
        {
            // Me paso, modifico el soxac
            soxac.setCantidad(soxac.getCantidad()+Math.abs(cantidadDisponible));
        }       
        
        return true;
    }
    
    public DefaultTreeModel cargarModeloArbolTareasSoloTareas()
    {
        ArbolIconoNodo raiz = new ArbolIconoNodo(0,ArbolDeTareasTipos.TIPO_OBRA, pedidoDeObra.getNombre(),Iconos.ICONO_OBRA);
        DefaultTreeModel modelo = new DefaultTreeModel(raiz);
        
        //Agregar todas las tareas, subtareas y recursos de cada obra        
        cargarTareasEnArbol(modelo, planificacion.getTareas(),raiz,false);       
        
        return modelo;
    }

    public void comenzarEjecucion() {
        
        // Ante todo, cambio el estado de la planificación
        // Y tb cambio el estado del pedido de obra ( atómico )
        this.planificacion.setEstado(Planificacion.ESTADO_FINALIZADA);
        this.pedidoDeObra.setEstadoEnEjecucion();
        
        // Ahora... Creo la Ejecución
 
        // Lanzo el algoritmo que crea la ejecucion
        Ejecucion ejecucion = null;
        try
        {
            ejecucion = crearEstructuraEjecucionDesdePlanificacion();
        }catch(Exception e)
        {
            _pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo hacer una copia de la ejecucion\nRevise que no falte ningún dato\n"+e.getMessage());
            return;
        }
        
        // Asocio la planificacion a la OBRA
        this.pedidoDeObra.setEjecucion(ejecucion);
        
        // Guardo la planificacion con su nuevo estado y posibles cambios sin guardar
        try
        {
             HibernateUtil.beginTransaction();
                // Guardo la Planificacion
                sesion.saveOrUpdate(this.planificacion);        
                // Guardo los cambiios en el Pedido de Obra
                sesion.saveOrUpdate(this.pedidoDeObra);              
                // Guardo los cambiios en la Ejecucion
                sesion.saveOrUpdate(ejecucion);        
                
                    // Tengo que guardar mejor la Ejecucion, no toma todos los cambios
                    for (int i = 0; i < ejecucion.getTareas().size(); i++) {
                        TareaEjecucion tarea = ejecucion.getTarea(i);
                        sesion.saveOrUpdate(tarea);
                        
                            // Guardo los Materiales
                            for (int j = 0; j < tarea.getMateriales().size(); j++) {
                                PlanificacionXMaterial mat = tarea.getMateriales().get(j);
                                 sesion.saveOrUpdate(mat);
                            }
                    }
                
            HibernateUtil.commitTransaction();
        } 
        catch (Exception ex)
        {
            HibernateUtil.rollbackTransaction();
            _pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error!","No se pudo generar una nueva Ejecución\nPongase en contacto con un administrador\n"+ex.getMessage());
        }        
        
        // Si no exploto hasta ahora, lanzo la nueva ventana
        _pantalla.lanzarEjecucion(this.pedidoDeObra.getId());
    }
    
    /**
     * Algoritmo que crea la Ejecucion a Partir de la Planificacion.
     * Pasos:
     * 1- Creo la Ejecucion
     * 2- Recursivamente, Por cada Tarea de Planificacion creo una de Ejecucion y la asocio
     * 3- Por cada Herramienta Planificada de una Tarea, agrego una a Ejecucion
     * 4- Por cada Material Planificado de una Tarea, agrego una a Ejecucion
     * 5- Por cada Herramienta Planificada de una Tarea, agrego una a Ejecucion
     * @return 
     */
    private Ejecucion crearEstructuraEjecucionDesdePlanificacion() {
        // 1- Creo la Ejecucion
        Ejecucion ejecucion = new Ejecucion(planificacion);
        ejecucion.setFechaInicio(new Date());
        ejecucion.setObservaciones("");
        ejecucion.setPlanificacionOriginal(planificacion);

            // 2- Recursivamente, Por cada Tarea de Planificacion creo una de Ejecucion y la asocio
            List<TareaPlanificacion> listaTareasEjecucion = new ArrayList<TareaPlanificacion>();
            
            for (int i = 0; i < planificacion.getTareas().size(); i++) {
                TareaPlanificacion tareaPlan =  planificacion.getTareas().get(i);
                TareaEjecucion tareaEjec = copiaRecursivaTareaXTarea(tareaPlan);
                listaTareasEjecucion.add(tareaEjec);
            }
            
            // 3- Copio los adicionales
            for(int i =0; i<planificacion.getCotizacion().getSubObras().size();i++){
                SubObra subObra = planificacion.getCotizacion().getSubObras().get(i);
                List<EjecucionXAdicional> adicionalesEjecucion = crearListadoAdicionalesEjecucion(subObra.getAdicionales());
                ejecucion.getAdicionales().addAll(adicionalesEjecucion);
            }

            ejecucion.setTareas(listaTareasEjecucion);

        return ejecucion;
    }

    /**
     * Atencion! Recursivo !!
     *
     * @param tareaPlan
     * @return
     */
    private TareaEjecucion copiaRecursivaTareaXTarea(TareaPlanificacion tareaPlan) {

        TareaEjecucion tareaEjec = new TareaEjecucion(tareaPlan);
        tareaEjec.setTareaPlanificada(tareaPlan);

        // 3- Por cada Herramienta Planificada de una Tarea, agrego una a Ejecucion
        tareaEjec.setHerramientas(crearListadoHerramientasEjecucion(tareaPlan.getHerramientas(),tareaEjec));

        // 4- Por cada Material Planificado de una Tarea, agrego una a Ejecucion
        tareaEjec.setMateriales(crearListadoMaterialesEjecucion(tareaPlan.getMateriales()));

        // 5- Por cada Alquiler/Compra Planificada de una Tarea, agrego una a Ejecucion
        tareaEjec.setAlquilerCompras(crearListadoAlquileresComprasEjecucion(tareaPlan.getAlquilerCompras()));

        // 6- Copio los adicionales ...
        // Y angora ???? ...., son por subObra (Anda y mira arriba)
        
        // 7- Ahora Copio los RRHH o Empleados lo que sea que sean por cada DetalleTareaPlanificacion
        // agrego una Tarea a Ejecucion
        tareaEjec.setDetalles(crearListadoDetalleListaTareas(tareaPlan.getDetallesSinDetallesVacios(),tareaEjec));
        
        // Mando a Recursividad
        for (int i = 0; i < tareaPlan.getSubtareas().size(); i++) {
            TareaPlanificacion tp = tareaPlan.getSubtareas().get(i);
            TareaEjecucion te = copiaRecursivaTareaXTarea(tp);
            tareaEjec.getSubtareas().add(te);
        }        
        
        return tareaEjec;
    }
    

    /**
     * Por cada Herramienta que haya en Planificacion sobre una Tarea, creo las correspondientes
     * de Ejecucion.
     * @param herramientas
     * @return 
     */
    private List<PlanificacionXHerramienta> crearListadoHerramientasEjecucion(List<PlanificacionXHerramienta> herramientas, TareaEjecucion tarea) {
        List<PlanificacionXHerramienta> listaHerramientas = new ArrayList<PlanificacionXHerramienta>();
        
            if(herramientas!=null){
                for (int i = 0; i < herramientas.size(); i++) {
                    PlanificacionXHerramienta planXHerramienta = herramientas.get(i);
                    
                    EjecucionXHerramienta ejcXHerr = new EjecucionXHerramienta();
                    ejcXHerr.setHerramientaPlanificada(planXHerramienta);
                    listaHerramientas.add(ejcXHerr);
                    
                    ejcXHerr.setHerramientaCotizacion(planXHerramienta.getHerramientaCotizacion());
                    
                    // Lleno los datos del objeto
                    ejcXHerr.setHorasAsignadas(planXHerramienta.getHorasAsignadas());
                    
                        // Creo las Herramientas X Dia
                        List<EjecucionXHerramientaXDia> herramientasXdia = crearHerramientasXDia(tarea);
                        ejcXHerr.setUsoHerramientasXdia(herramientasXdia);
                }
            }
        
        return listaHerramientas;
    }

    /**
     * Por cada Material que haya en Planificacion sobre una Tarea, creo las correspondientes
     * de Ejecucion.
     * @param materiales
     * @return 
     */
    private List<PlanificacionXMaterial> crearListadoMaterialesEjecucion(List<PlanificacionXMaterial> materiales) {
        List<PlanificacionXMaterial> listaMateriales = new ArrayList<PlanificacionXMaterial>();
        
            if(materiales!=null){
                for (int i = 0; i < materiales.size(); i++) {
                    PlanificacionXMaterial planXmaterial = materiales.get(i);
                    
                    EjecucionXMaterial ejcXmat = new EjecucionXMaterial();
                    ejcXmat.setMaterialPlanificado(planXmaterial);
                    
                    ejcXmat.setMaterialCotizacion(planXmaterial.getMaterialCotizacion());
                    
                    // Lleno los datos del Objeto
                    ejcXmat.setCantidad(planXmaterial.getCantidad());
                    
                    listaMateriales.add(ejcXmat);
                }
            }
        
        return listaMateriales;
    }

    /**
     * Por cada Alquiler/Compra que haya en Planificacion sobre una Tarea, creo las correspondientes
     * de Ejecucion.
     * @param alquileresCompras
     * @return 
     */
    private List<PlanificacionXAlquilerCompra> crearListadoAlquileresComprasEjecucion(List<PlanificacionXAlquilerCompra> alquileresCompras) {
        List<PlanificacionXAlquilerCompra> listaAlquileresCompras = new ArrayList<PlanificacionXAlquilerCompra>();
        
            if(alquileresCompras!=null){
                for (int i = 0; i < alquileresCompras.size(); i++) {
                    PlanificacionXAlquilerCompra planXalqucompra = alquileresCompras.get(i);
                    
                    EjecucionXAlquilerCompra ejcXalqcom = new EjecucionXAlquilerCompra();
                    ejcXalqcom.setAlquilerCompraPlanificado(planXalqucompra);
                    
                    ejcXalqcom.setAlquilerCompraCotizacion(planXalqucompra.getAlquilerCompraCotizacion());
                    
                    // Lleno los datos
                    ejcXalqcom.setCantidad(planXalqucompra.getCantidad());
                    
                    listaAlquileresCompras.add(ejcXalqcom);
                }
            }
        
        return listaAlquileresCompras;
    }

    /**
     * Por cada Alquiler/Compra que haya en Planificacion sobre una Tarea, creo las correspondientes
     * de Ejecucion.
     * @param alquileresCompras
     * @return 
     */
    private List<EjecucionXAdicional> crearListadoAdicionalesEjecucion(List<SubObraXAdicional> adicionales) {
        List<EjecucionXAdicional> listaAdicionales = new ArrayList<EjecucionXAdicional>();
        
            if(adicionales!=null){
                for (int i = 0; i < adicionales.size(); i++) {
                    SubObraXAdicional subObraXAdicional = adicionales.get(i);
                    
                    EjecucionXAdicional ejcXadi = new EjecucionXAdicional();
                    ejcXadi.setAdicionalPlanificado((SubObraXAdicionalModif)subObraXAdicional);
                    listaAdicionales.add(ejcXadi);
                }
            }
        
        return listaAdicionales;
    }
    
    

    public int getIdDeEjecucionDeObra() {
        if(this.pedidoDeObra.getEjecucion()!=null)
        {
            return this.pedidoDeObra.getEjecucion().getId();
        }
        return -1;
    }

    /**
     * Calculo el uso de las herramientas x día.
     * - Saco la fecha de inicio y fin de la tarea planificada
     * - Calculo la cantidad de dias entre ambas
     * - Por cada una:
     *   - Creo un objeto EjecucionXHerramientaXDia
     * @param tarea
     * @return 
     */
    private List<EjecucionXHerramientaXDia> crearHerramientasXDia(TareaEjecucion tarea) {
        List<EjecucionXHerramientaXDia> lista = new ArrayList<EjecucionXHerramientaXDia>();
        
            Date fechaInicio = tarea.getTareaPlanificada().getFechaInicio();
            Date fechaFin    = tarea.getTareaPlanificada().getFechaFin();
            
            System.out.println("Fecha de Inicio: "+FechaUtil.getFecha(fechaInicio));
            System.out.println("Fecha de Fin: "+FechaUtil.getFecha(fechaFin));
            
            int cantidadDias = FechaUtil.diasDiferencia(fechaInicio, fechaFin);
            
            for(int i=0; i<=cantidadDias; i++ ){
                EjecucionXHerramientaXDia herrXdia = new EjecucionXHerramientaXDia();
                herrXdia.setHorasUtilizadas(0);
                Date fecha = FechaUtil.fechaMas(fechaInicio, i);
                herrXdia.setFecha(fecha);
                System.out.println("Creado una Herramienta X día el: "+FechaUtil.getFecha(fecha));
                lista.add(herrXdia);
            }
            
        return lista;
    }

    /**
     * Retorna el estado de la Planificacion
     * @return 
     */
    public String getEstado() {
        return this.planificacion.getEstado();
    }
    
    /**
     * True, si se puede modificar segun el estado
     * False, si no se puede.
     * @return 
     */
    public boolean esPlanificacionEditable(){
        if(Planificacion.ESTADO_BAJA.equals(getEstado()) || Planificacion.ESTADO_FINALIZADA.equals(getEstado())){
            return false;
        }
        return true;
    }

    /**
     * Por cada DetalleTareaPlanificacion creo un DetalleTareaEjecucion ...
     * Tambien es la encargada de separar por día y mapear los DetalleTareaEjecucionXDia. 
     * @param detalles
     * @return 
     */
    private List<DetalleTareaPlanificacion> crearListadoDetalleListaTareas(List<DetalleTareaPlanificacion> detalles, TareaEjecucion tarea) {
        List<DetalleTareaPlanificacion> lista = new ArrayList<DetalleTareaPlanificacion>();
        
            for (int i = 0; i < detalles.size(); i++) {
                DetalleTareaPlanificacion detTareaPlan = detalles.get(i);
                
                for (int j = 0; j < detTareaPlan.getEmpleados().size(); j++) {
                
                    DetalleTareaEjecucion detTareaEjec = new DetalleTareaEjecucion(detTareaPlan);
                    detTareaEjec.setDetalleTareaPlanificado(detTareaPlan);
                    
                    detTareaEjec.setCantidadPersonas(1);
                    detTareaEjec.addEmpleados(detTareaPlan.getEmpleados().get(j));
                    
                    List<DetalleTareaEjecucionXDia> listaPorDía = crearDetalleTareaXDia(tarea);
                    detTareaEjec.setListaDetallePorDia(listaPorDía);
                    lista.add(detTareaEjec);
                }
                
            }
        
        return lista;
    }

    /**
     * Calculo el Detalle de Tarea por Día.
     * - Saco la fecha de inicio y fin de la tarea planificada
     * - Calculo la cantidad de dias entre ambas
     * - Por cada una:
     *   - Creo un objeto DetalleTareaEjecucionXDia
     * @param tarea
     * @return 
     */
    private List<DetalleTareaEjecucionXDia> crearDetalleTareaXDia( TareaEjecucion tarea) {
        List<DetalleTareaEjecucionXDia> lista = new ArrayList<DetalleTareaEjecucionXDia>();
        
            Date fechaInicio = tarea.getTareaPlanificada().getFechaInicio();
            Date fechaFin    = tarea.getTareaPlanificada().getFechaFin();
            
            System.out.println("Fecha de Inicio: "+FechaUtil.getFecha(fechaInicio));
            System.out.println("Fecha de Fin: "+FechaUtil.getFecha(fechaFin));
            
            int cantidadDias = FechaUtil.diasDiferencia(fechaInicio, fechaFin);
            System.out.println("Cantidad de días: "+cantidadDias);
            
            for(int i=0; i<=cantidadDias; i++ ){
                Date fecha = FechaUtil.fechaMas(fechaInicio, i);
                
                DetalleTareaEjecucionXDia detTareaXdia = new DetalleTareaEjecucionXDia();
                detTareaXdia.setCantHorasNormales(0);
                detTareaXdia.setCantHorasAl50(0);
                detTareaXdia.setCantHorasAl100(0);
                detTareaXdia.setFecha(fecha);
                System.out.println("Creado un Detalle Tarea X día el: "+FechaUtil.getFecha(fecha));
                lista.add(detTareaXdia);
            }
            
        return lista;
    }    
    
}