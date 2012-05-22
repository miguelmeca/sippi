/*            NTupla nt1 = new NTupla(1);
 nt1.setNombre("TestSubTarea1");
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion;

import com.hackelare.coolgantt.CoolGanttPhase;
import config.Iconos;
import controlador.GestorAbstracto;
import controlador.planificacion.cotizacion.GestorEditarCotizacionModificada;
import java.awt.Point;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
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


/**
 * Las Tuplas tienen que tener el formato:
 *
 * @author Administrador
 */
public class GestorEditarPlanificacion extends GestorAbstracto implements IGestorPlanificacion{

    private EditarPlanificacion _pantalla;
    private GestorArbolDeRecursos _gestorArbolRecursos;
    private Session sesion;
    private PedidoObra pedidoDeObra;
    private PlanificacionXXX planificacion;

    @Override
    public PlanificacionXXX getPlanificacion() {
        return planificacion;
    }

    public void setPlanificacion(PlanificacionXXX planificacion) {
        this.planificacion = planificacion;
    }
    
    private HashMap<Integer,Boolean> generatedIdGantt;

    public GestorEditarPlanificacion(EditarPlanificacion _pantalla, int idPedidoDeObra) {
        this._pantalla = _pantalla;
        this._gestorArbolRecursos = new GestorArbolDeRecursos();
        generatedIdGantt = new HashMap<Integer, Boolean>();

        try {
            sesion = HibernateUtil.getSession();
            
            HibernateUtil.getSession().beginTransaction();
            this.pedidoDeObra = (PedidoObra) sesion.load(PedidoObra.class, idPedidoDeObra);
            this.planificacion = pedidoDeObra.getPlanificacion();
            HibernateUtil.getSession().getTransaction().commit();
            
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
            }

            if (this.planificacion != null && this.planificacion.getCotizacion() != null && this.planificacion.getCotizacion().getCotizacionOriginal() != null) {
                _pantalla.setTxtNroCotizacion(this.planificacion.getCotizacion().getCotizacionOriginal().getNroCotizacion());
                _pantalla.setCotizacionMontoTotal(String.valueOf(this.planificacion.getCotizacion().getCotizacionOriginal().CalcularTotal()));
            }
            
            if (this.planificacion != null)
            {
                _pantalla.setDescripcionPlanificacion(this.planificacion.getDescripcion());
            }
            

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeError("No se pudo cargar los datos generales de la cotizacion");
        }

    }

    private void mostrarMensajeError(String msg) {
        System.err.println("[ERROR] " + msg);
        _pantalla.mostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error!", msg);
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
    

    
    public void agregarNuevaTareaArbolDesdeCotizacion(int hashTaraCotizada, TareaPlanificacion padre, Date fechaInicio, Date fechaFin)
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
                if(padre.esCotizadaODescendienteDeTareaCotizada(planificacion))
                {
                    mostrarMensajeError("No se puede agregar una tarea cotizada como descendiente de otra tarea cotizada");
                    return; 
                }
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
        _pantalla.agregarNuevaTareaArbol(nuevaTarea.hashCode(),nuevaTarea.getNombre(),hashPadre);
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
//        _pantalla.agregarNuevaTareaGantt(nuevaTarea.getIdTareaGantt(),nombreGantt,idTareaGanttPadre);
    }
    
    private Date fechaMas(Date fch, int horas) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, horas);
        return new Date(cal.getTimeInMillis());
    }    
    
    private TareaPlanificacion crearTarea(int hashTaraCotizada, Date fechaInicio, Date fechaFin)
    { // VEO QUE NO ESTE REPETIDA
        TareaPlanificacion nuevaTarea = null;
        nuevaTarea=planificacion.buscarTareaPorHashTareaCotizada(hashTaraCotizada);
        if(nuevaTarea!=null)
        {
            mostrarMensajeError("La Tarea: "+nuevaTarea.getNombre()+"\nya se encuentra agregada");
            return null;
        }
         
        
        // CREO LA NUEVA TAREA
        nuevaTarea = new TareaPlanificacion();
       // nuevaTarea.setId(id);
        nuevaTarea.setFechaInicio(fechaInicio);
        nuevaTarea.setFechaFin(fechaFin);
        nuevaTarea.setIdTareaGantt(generarIdTareaGantt());  
        
        
        /*Date fechaEstimada = this.planificacion.getFechaInicio();
        nuevaTarea.setFechaInicio(fechaEstimada);
        nuevaTarea.setFechaFin(fechaMas(fechaEstimada,3));*/
        
        // CArgo la SubObra X Tarea Planificada Mod
        try
        {   
            SubObraXTareaModif soxtm;            
             soxtm=(SubObraXTareaModif)this.planificacion.getCotizacion().getSubObraXTareaPorHash(hashTaraCotizada);
             nuevaTarea.setNombre(soxtm.getNombre());
             nuevaTarea.setTareaCotizada(soxtm);
             nuevaTarea.setTipoTarea(soxtm.getTipoTarea());
             return nuevaTarea;
            /*List<SubObra> listaSubObrasMod = this.planificacion.getCotizacion().getSubObras();
              for (int i = 0; i < listaSubObrasMod.size(); i++) {
                SubObraModificada som = (SubObraModificada)listaSubObrasMod.get(i);
                List<SubObraXTarea> lsitasoxtm = som.getTareas();
                  for (int j = 0; j < lsitasoxtm.size(); j++) {
                      SubObraXTareaModif soxtm = (SubObraXTareaModif)lsitasoxtm.get(j);
                      //if(soxtm.getId()==id)
                      if(soxtm.hashCode()==hash)
                      {
                          nuevaTarea.setTareaCotizada(soxtm);
                          return nuevaTarea;
                      }
                  }
            }*/
        
        }catch(Exception e)
        {
           e.printStackTrace();
           mostrarMensajeError("No se pudo cargar la Cotizacion Original Modificada");
           return null;
        }
    
    }
    
    public DefaultTreeModel cargarModeloArbolTareas()
    {
        ArbolIconoNodo raiz = new ArbolIconoNodo(0,ArbolDeTareasTipos.TIPO_OBRA, pedidoDeObra.getNombre(),Iconos.ICONO_OBRA);
        DefaultTreeModel modelo = new DefaultTreeModel(raiz);
        
        //Agregar todas las tareas, subtareas y recursos de cada obra        
        cargarTareasEnArbol(modelo, planificacion.getTareas(),raiz);       
        
        
        return modelo;
    } 
    
    //Metodo recursivo
    private void cargarTareasEnArbol(DefaultTreeModel modelo, List<TareaPlanificacion> listaTareas,ArbolIconoNodo padre)
    {
        int cantTareas= listaTareas.size();
        for (int i = 0; i < cantTareas; i++) 
        {
            TareaPlanificacion tarea= listaTareas.get(i);
            ArbolIconoNodo nodoTarea = new ArbolIconoNodo(tarea.hashCode(),ArbolDeTareasTipos.TIPO_TAREA,tarea.getNombre(),Iconos.ICONO_TAREA);
            modelo.insertNodeInto(nodoTarea, padre, i);            
            
            cargarTareasEnArbol(modelo, tarea.getSubtareas(),nodoTarea);
            
            if(tarea.getMateriales()!=null && !tarea.getMateriales().isEmpty())
            {
                ArbolIconoNodo nodoMateriales = new ArbolIconoNodo(tarea.hashCode(),ArbolDeTareasTipos.TIPO_MATERIALES,ArbolDeTareasTipos.TIPO_MATERIALES,Iconos.ICONO_MATERIALES);
                modelo.insertNodeInto(nodoMateriales, nodoTarea, 0);
                int cantMateriales=tarea.getMateriales().size();
                for (int j = 0; j < cantMateriales; j++) 
                {
                  PlanificacionXMaterial material=tarea.getMateriales().get(j);
                  ArbolIconoNodo nodoMaterial = new ArbolIconoNodo(material.hashCode(),ArbolDeTareasTipos.TIPO_MATERIAL,material.toString(),Iconos.ICONO_MATERIAL);
                  modelo.insertNodeInto(nodoMaterial, nodoMateriales, j);
                } 
            }
            
            if(tarea.getHerramientas()!=null && !tarea.getHerramientas().isEmpty())
            {
               ArbolIconoNodo nodoHerramientas = new ArbolIconoNodo(tarea.hashCode(),ArbolDeTareasTipos.TIPO_HERRAMIENTAS,ArbolDeTareasTipos.TIPO_HERRAMIENTAS,Iconos.ICONO_HERRAMIENTAS);
               modelo.insertNodeInto(nodoHerramientas, nodoTarea, 1);
               int cantHeramientas=tarea.getHerramientas().size();
               for (int j = 0; j < cantHeramientas; j++) 
               {
                  PlanificacionXHerramienta herramienta=tarea.getHerramientas().get(j);
                  ArbolIconoNodo nodoHerramienta = new ArbolIconoNodo(herramienta.hashCode(),ArbolDeTareasTipos.TIPO_HERRAMIENTA,herramienta.toString(),Iconos.ICONO_HERRAMIENTA);
                  modelo.insertNodeInto(nodoHerramienta, nodoHerramientas, j);
               }
            }
                        
            if(tarea.getAlquilerCompras()!=null && !tarea.getAlquilerCompras().isEmpty())
            {
                ArbolIconoNodo nodoAlquileresCompras = new ArbolIconoNodo(tarea.hashCode(),ArbolDeTareasTipos.TIPO_ALQUILERESCOMPRAS,ArbolDeTareasTipos.TIPO_ALQUILERESCOMPRAS,Iconos.ICONO_ALQUILERESCOMPRAS);
                modelo.insertNodeInto(nodoAlquileresCompras, nodoTarea, 2);
                int cantAlquileresCompras=tarea.getAlquilerCompras().size();
                for (int j = 0; j < cantAlquileresCompras; j++) 
                {
                    PlanificacionXAlquilerCompra alquilerCompra=tarea.getAlquilerCompras().get(j);
                    ArbolIconoNodo nodoAlquilerCompra = new ArbolIconoNodo(alquilerCompra.hashCode(),ArbolDeTareasTipos.TIPO_ALQUILERCOMPRA,alquilerCompra.toString(),Iconos.ICONO_ALQUILERCOMPRA);
                    modelo.insertNodeInto(nodoAlquilerCompra, nodoAlquileresCompras, j);
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
                }
                else
                {
                    
                    _pantalla.asociarRecursoATareaArbol(hashRecuso,nombre, hashTareaPadre, tipoRecurso);
                }
            }
            else
            {
                mostrarMensajeError("No se pudo encontrar la Tarea padre a la que se le quiere agregar la SubTarea");
                return;
            } 
           
        }
        
        
    }
    
    public boolean quitarTarea(int hash, String nombre,int idTareaPadre)
    {
       boolean eliminada= planificacion.eliminarTareaPorHash(hash, true);
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
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void tareaCambioFecha(int id, Date date) {
        
        TareaPlanificacion nuevaTarea = planificacion.buscarTareaPorIdTareaGantt(id);    
        if(nuevaTarea!=null)
        {
            // OJO, muevo Toda la tarea
            // Calculo la duracion:
            int dias = FechaUtil.diasDiferencia(nuevaTarea.getFechaInicio(),date);
            // x dias para la izquierda o derecha?
            if(nuevaTarea.getFechaInicio().after(date))
            {
                // Derecha
                nuevaTarea.setFechaFin(FechaUtil.fechaMas(nuevaTarea.getFechaFin(),dias));
            }
            else
            {
                // Izquierda
                nuevaTarea.setFechaFin(FechaUtil.fechaMas(nuevaTarea.getFechaFin(),dias));
            }
            nuevaTarea.setFechaInicio(date);
        }
    }

    public void tareaCambioFechaInicio(int id, Date date) {
        TareaPlanificacion nuevaTarea = planificacion.buscarTareaPorIdTareaGantt(id);    
        if(nuevaTarea!=null)
        {
            nuevaTarea.setFechaInicio(date);
        }
    }

    public void tareaCambioFechaFin(int id, Date date) {
        TareaPlanificacion nuevaTarea = planificacion.buscarTareaPorIdTareaGantt(id);    
        if(nuevaTarea!=null)
        {
            nuevaTarea.setFechaFin(date);
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
                _pantalla.actualizar(0, "", true,null);
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
            _pantalla.actualizar(0, "", true,null);
        }
    }

    public void guardarPlanificacion() {
        try
        {
            HibernateUtil.beginTransaction();
            sesion = HibernateUtil.getSession();
            GestorEditarCotizacionModificada.guardarCotizacion(planificacion.getCotizacion(), sesion);
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

    public SubObraXHerramientaModif getGastosHerramientaFromHash(String hash) {
        return (SubObraXHerramientaModif) planificacion.getCotizacion().getSubObraXHerramientaPorHash(Integer.parseInt(hash));
    }

    public SubObraXMaterialModif getGastosMaterialFromHash(String hash) {
        return (SubObraXMaterialModif) planificacion.getCotizacion().getSubObraXMaterialPorHash(Integer.parseInt(hash));
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
    
    public boolean crearSubTarea(String nombre, Date inicioTarea,  Date finTarea, TareaPlanificacion tareaPadre)
    {
        try{
            TareaPlanificacion subTarea=new TareaPlanificacion();
            subTarea.setTipoTarea(subTarea.getTipoTarea());
            subTarea.setNombre(nombre);
            subTarea.setFechaInicio(inicioTarea);
            subTarea.setFechaFin(finTarea);
            tareaPadre.addSubTarea(subTarea);
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
        int cantidadDisponiblePlanificacion = PlanificacionUtils.getCantidadAsignadaAMaterial(planificacion, soxmm);
        int cantidadDisponible = soxmm.getCantidadDisponible() - cantidadDisponiblePlanificacion;
        if(cantidadDisponible<0)
        {
            // Me paso, modifico el soxmm
            soxmm.setCantidad(soxmm.getCantidad()+Math.abs(cantidadDisponible));
        }       
        
        return true;
    }
}
