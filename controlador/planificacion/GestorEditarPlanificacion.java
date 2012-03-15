/*            NTupla nt1 = new NTupla(1);
 nt1.setNombre("TestSubTarea1");
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion;

import config.Iconos;
import controlador.GestorAbstracto;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

            this.pedidoDeObra = (PedidoObra) sesion.load(PedidoObra.class, idPedidoDeObra);
            this.planificacion = pedidoDeObra.getPlanificacion();
            
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
            mostrarMensajeError("No se pudo cargar el Pedido ni la planificación asociada");
        }

    }

    public List getListaSubObras() {

        ArrayList<NTupla> lista = new ArrayList<NTupla>();

        try {
            if (this.planificacion != null && this.planificacion.getId() != 0) {
                List<SubObraModificada> listaSO = (List) this.planificacion.getCotizacion().getSubObra();
                for (int i = 0; i < listaSO.size(); i++) {
                    SubObraModificada som = listaSO.get(i);
                    NTupla nt1 = new NTupla(som.getSubObraModificadaId());
                    nt1.setNombre(som.getSubObra().getNombre());
                    lista.add(nt1);
                }
            }
        } catch (Exception e) {
            mostrarMensajeError("No se pudo cargar las subobras del pedido");
        }
        return lista;
    }

    public List getListaTareasXSubObra(int idSubObra) {

        ArrayList<NTupla> lista = new ArrayList<NTupla>();
        try {
            if (this.planificacion != null && this.planificacion.getId() != 0) {
                List<SubObraModificada> listaSO = (List) this.planificacion.getCotizacion().getSubObra();
                for (int i = 0; i < listaSO.size(); i++) {
                    SubObraModificada som = listaSO.get(i);
                    if (som.getSubObraOriginal().getId() == idSubObra) {
                        List<SubObraXTareaModif> listaTareasMod = som.getTareas();
                        for (int j = 0; j < listaTareasMod.size(); j++) {
                            SubObraXTareaModif starea = listaTareasMod.get(j);
                            NTupla nt1 = new NTupla(starea.getId());
                            nt1.setNombre(starea.getNombre());
                            lista.add(nt1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            mostrarMensajeError("No se pudo cargar las subobras del pedido");
        }
        return lista;
    }

    public void mostrarDatosGenerales(int idPedidoDeObra) {

        try {
            sesion = HibernateUtil.getSession();
            pedidoDeObra = (PedidoObra) sesion.load(PedidoObra.class, idPedidoDeObra);

            _pantalla.setLblObraNombre(pedidoDeObra.getNombre().substring(0, 60) + "...");
            _pantalla.setLblObraFechaFin(FechaUtil.getFecha(pedidoDeObra.getFechaFin()));
            _pantalla.setLblObraFechaInicio(FechaUtil.getFecha(pedidoDeObra.getFechaInicio()));
            _pantalla.setLblObraLugar(pedidoDeObra.getPlanta().getDomicilio().toString());
            _pantalla.setLblObraPlanta(pedidoDeObra.getPlanta().getRazonSocial());

            if (this.planificacion != null && this.planificacion.getCotizacion() != null) {
                _pantalla.setNroPlanificacion(String.valueOf(this.planificacion.getNumeroPlanificacion()));
                _pantalla.setFechaInicioPlanif(this.planificacion.getFechaInicio());
                _pantalla.setFechaFinPlanif(this.planificacion.getFechaFin());
            }

            if (this.planificacion != null && this.planificacion.getCotizacion() != null && this.planificacion.getCotizacion().getCotizacionOriginal() != null) {
                _pantalla.setTxtNroCotizacion(this.planificacion.getCotizacion().getCotizacionOriginal().getNroCotizacion());
                _pantalla.setCotizacionMontoTotal(String.valueOf(this.planificacion.getCotizacion().getCotizacionOriginal().CalcularTotal()));
            }

        } catch (Exception e) {
            mostrarMensajeError("No se pudo cargar los datos generales de la cotizacion");
        }

    }

    private void mostrarMensajeError(String msg) {
        System.err.println("[ERROR] " + msg);
        _pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error!", msg);
    }

    public void cargarArbolRecursos(int idSubObra, JTree treeRecursos) {
       
       TreeEntry nodoRoot = new TreeEntry("Recursos",Iconos.ICONO_HERRAMIENTA);
               
       try {
            if (this.planificacion != null && this.planificacion.getId() != 0) {
                List<SubObraModificada> listaSO = (List) this.planificacion.getCotizacion().getSubObra();
                for (int i = 0; i < listaSO.size(); i++) {
                    SubObraModificada som = listaSO.get(i);
                    if (som.getSubObraOriginal().getId() == idSubObra) {
                        
                        // CARGO LOS RECURSOS !!
                        // HERRAMIENTAS
                        TreeEntry nodoHerramientas = new TreeEntry("Herramientas",Iconos.ICONO_HERRAMIENTAS);
                        nodoRoot.add(nodoHerramientas);
                        
                        List<SubObraXHerramientaModif> listaHerramientas = som.getHerramientas();
                        for (int j = 0; j < listaHerramientas.size(); j++) {
                            SubObraXHerramientaModif herr = listaHerramientas.get(j);
                            TreeEntry subNodoHerramientas = new TreeEntry(herr.getHerramienta().getRecursoEsp().getNombre() + ":" +herr.getHerramienta().getNroSerie(),Iconos.ICONO_HERRAMIENTA);
                            subNodoHerramientas.setId(herr.getHerramienta().getRecursoEsp().getId());
                            subNodoHerramientas.setTipo(ArbolDeTareasTipos.TIPO_HERRAMIENTA);
                            nodoHerramientas.add(subNodoHerramientas);
                        }
                        
                        // MATERIALES
                        // NECESITO idRecursoEspecifico y idMaterial
                        TreeEntry nodoMateriales = new TreeEntry("Materiales",Iconos.ICONO_MATERIAL);
                        nodoRoot.add(nodoMateriales);
                        
                        List<SubObraXMaterialModif> listaMateriales = som.getMateriales();
                        for (int j = 0; j < listaMateriales.size(); j++) {
                            SubObraXMaterialModif mat = listaMateriales.get(j);
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
                                     System.err.println("[ERROR] No se pudo encontrar el nombre del Material");
                                 }
                                 
                            TreeEntry subNodoMaterial = new TreeEntry(nombre,Iconos.ICONO_MATERIALES);
                            subNodoMaterial.setId(mat.getMaterial().getId());
                            subNodoMaterial.setTipo(ArbolDeTareasTipos.TIPO_MATERIAL);
                            nodoMateriales.add(subNodoMaterial);
                        }
                        
                        // ALQUILERES / COMPRAS
                        TreeEntry nodoAlqComps = new TreeEntry("Alquileres / Compras",Iconos.ICONO_ALQUILERESCOMPRAS);
                        nodoRoot.add(nodoAlqComps);
                        
                        List<SubObraXAlquilerCompraModif> listaAlquileres = som.getAlquileres();
                        for (int j = 0; j < listaAlquileres.size(); j++) {
                            SubObraXAlquilerCompraModif alqcomp = listaAlquileres.get(j);
                            TreeEntry subNodoAlquComp = new TreeEntry(alqcomp.getTipoAlquilerCompra().getNombre()+" "+alqcomp.getDescripcion(),Iconos.ICONO_ALQUILERCOMPRA);
                            subNodoAlquComp.setId(alqcomp.getId());
                            subNodoAlquComp.setTipo(ArbolDeTareasTipos.TIPO_ALQUILERCOMPRA);
                            nodoAlqComps.add(subNodoAlquComp);
                        }
                        
                        
                    }
                }
            }
        } catch (Exception e) {
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
            mostrarMensajeError("No se pudo cargar la planificación, por lo tanto no se mostrará el Gantt");
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

   
    public void AgregarNuevaTarea(int id, String nombre) {
        
        TareaPlanificacion nuevaTarea=crearTarea(nombre, id);
        
        // Si existe:
        if(nuevaTarea== null && nuevaTarea.getTareaCotizada()==null)
        {
            mostrarMensajeError("No se pudo cargar la Tarea Original Cotizada");
            return;
        }
        
        // nuevaTarea.set
        // TODO: Falta la relacion 
        
        this.planificacion.getTareas().add(nuevaTarea);
        String nombreTarea = nuevaTarea.getNombre();
        _pantalla.AgregarNuevaTarea(nuevaTarea.getIdTareaGantt(),nombreTarea,nombreTarea,0);
//        _pantalla.updateGantt();
    }

    public int getCotizacionPlanificada() {
        return  this.planificacion.getCotizacion().getCotizacionOriginal().getId();
    }
    

    public void AgregarNuevaSubTarea(int id, String nombre,int idTareaPadre, int nivel) {

        TareaPlanificacion nuevaTarea=crearTarea(nombre, id);
        
        if(nuevaTarea== null && nuevaTarea.getTareaCotizada()==null)
        {
            mostrarMensajeError("No se pudo cargar la Tarea Original Cotizada");
            return;
        }
        
        // CARGO LA TAREA PADRE
        // Tengo el idGantt del padre, ahora tengo que conseguir el id de la Tarea y la tarea en si
        TareaPlanificacion padre = null;
        for (int i = 0; i < this.planificacion.getTareas().size(); i++) {
            TareaPlanificacion tarea = this.planificacion.getTareas().get(i);
            if(tarea.getIdTareaGantt()==idTareaPadre)
            {
                padre = tarea;
                continue;
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
        
        // Agrego el nivel para mostrar en el Gantt
        String nombreGantt = nuevaTarea.getNombre();
        for (int i = 0; i < nivel; i++) {
            nombreGantt+="@";
        }
        _pantalla.AgregarNuevaTarea(nuevaTarea.getIdTareaGantt(),nuevaTarea.getNombre(),nombreGantt,idTareaPadre);
    }
    
    private TareaPlanificacion crearTarea(String nombre, int id)
    {
        // VEO QUE NO ESTE REPETIDA
        for (int i = 0; i < this.planificacion.getTareas().size(); i++) {
        TareaPlanificacion tarea = this.planificacion.getTareas().get(i);
            if(tarea.getTareaCotizada()!=null && tarea.getTareaCotizada().getId()==id)
            {
                mostrarMensajeError("La Tarea: "+nombre+"\nya se encuentra agegada");
                return null;
            }
        }
        
        // CREO LA NUEVA TAREA
        TareaPlanificacion nuevaTarea = new TareaPlanificacion();
        nuevaTarea.setNombre(nombre);
        nuevaTarea.setIdTareaGantt(generarIdTareaGantt());        
        
        // CArgo la SubObra X Tarea Planificada Mod
        try
        {
              List<SubObraModificada> listaSubObrasMod = this.planificacion.getCotizacion().getSubObra();
              for (int i = 0; i < listaSubObrasMod.size(); i++) {
                SubObraModificada som = listaSubObrasMod.get(i);
                List<SubObraXTareaModif> lsitasoxtm = som.getTareas();
                  for (int j = 0; j < lsitasoxtm.size(); j++) {
                      SubObraXTareaModif soxtm = lsitasoxtm.get(j);
                      if(soxtm.getId()==id)
                      {
                          nuevaTarea.setTareaCotizada(soxtm);
                          return nuevaTarea;
                      }
                  }
            }
        
        }catch(Exception e)
        {
            mostrarMensajeError("No se pudo cargar la Cotizacion Original Modificada");
            return null;
        }
        return null;
    
    }
    
    public DefaultTreeModel getModeloArbolTareas()
    {
        ArbolIconoNodo raiz = new ArbolIconoNodo(planificacion.getId(),ArbolDeTareasTipos.TIPO_OBRA, pedidoDeObra.getNombre(),Iconos.ICONO_OBRA);
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
            TareaPlanificacion tarea= planificacion.getTareas().get(i);
            ArbolIconoNodo nodoTarea = new ArbolIconoNodo(tarea.getId(),ArbolDeTareasTipos.TIPO_TAREA,tarea.getNombre(),Iconos.ICONO_TAREA);
            modelo.insertNodeInto(nodoTarea, padre, i);            
            
            cargarTareasEnArbol(modelo, tarea.getSubtareas(),nodoTarea);
            
            if(tarea.getHerramientas()!=null && !tarea.getHerramientas().isEmpty())
            {
               ArbolIconoNodo nodoHerramientas = new ArbolIconoNodo(tarea.getId(),ArbolDeTareasTipos.TIPO_HERRAMIENTAS,ArbolDeTareasTipos.TIPO_HERRAMIENTAS,Iconos.ICONO_HERRAMIENTAS);
               modelo.insertNodeInto(nodoHerramientas, nodoTarea, 1);
               int cantHeramientas=tarea.getHerramientas().size();
               for (int j = 0; j < cantHeramientas; j++) 
               {
                  PlanificacionXHerramienta herramienta=tarea.getHerramientas().get(j);
                  ArbolIconoNodo nodoHerramienta = new ArbolIconoNodo(herramienta.getId(),ArbolDeTareasTipos.TIPO_HERRAMIENTA,herramienta.toString(),Iconos.ICONO_HERRAMIENTA);
                  modelo.insertNodeInto(nodoHerramienta, nodoHerramientas, j);
               }
            }
            
            if(tarea.getMateriales()!=null && !tarea.getMateriales().isEmpty())
            {
                ArbolIconoNodo nodoMateriales = new ArbolIconoNodo(tarea.getId(),ArbolDeTareasTipos.TIPO_MATERIALES,ArbolDeTareasTipos.TIPO_MATERIALES,Iconos.ICONO_MATERIALES);
                modelo.insertNodeInto(nodoMateriales, nodoTarea, 0);
                int cantMateriales=tarea.getMateriales().size();
                for (int j = 0; j < cantMateriales; j++) 
                {
                  PlanificacionXMaterial material=tarea.getMateriales().get(j);
                  ArbolIconoNodo nodoMaterial = new ArbolIconoNodo(material.getId(),ArbolDeTareasTipos.TIPO_MATERIAL,material.toString(),Iconos.ICONO_MATERIAL);
                  modelo.insertNodeInto(nodoMaterial, nodoMateriales, j);
                } 
            }
            
            if(tarea.getAlquilerCompras()!=null && !tarea.getAlquilerCompras().isEmpty())
            {
                ArbolIconoNodo nodoAlquileresCompras = new ArbolIconoNodo(tarea.getId(),ArbolDeTareasTipos.TIPO_ALQUILERESCOMPRAS,ArbolDeTareasTipos.TIPO_ALQUILERESCOMPRAS,Iconos.ICONO_ALQUILERESCOMPRAS);
                modelo.insertNodeInto(nodoAlquileresCompras, nodoTarea, 2);
                int cantAlquileresCompras=tarea.getAlquilerCompras().size();
                for (int j = 0; j < cantAlquileresCompras; j++) 
                {
                    PlanificacionXAlquilerCompra alquilerCompra=tarea.getAlquilerCompras().get(j);
                    ArbolIconoNodo nodoAlquilerCompra = new ArbolIconoNodo(alquilerCompra.getId(),ArbolDeTareasTipos.TIPO_ALQUILERCOMPRA,alquilerCompra.toString(),Iconos.ICONO_ALQUILERCOMPRA);
                    modelo.insertNodeInto(nodoAlquilerCompra, nodoAlquileresCompras, j);
                }
            }
            
        }
    }

    @Override
    public TareaPlanificacion getTareaActual() {
        return null;
    }

    @Override
    public void refrescarPantallas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
