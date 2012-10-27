package controlador.ejecucion;

import config.Iconos;
import controlador.planificacion.GestorEditarPlanificacion;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultTreeModel;
import modelo.Ejecucion;
import modelo.PedidoObra;
import modelo.PlanificacionXAlquilerCompra;
import modelo.PlanificacionXHerramienta;
import modelo.PlanificacionXMaterial;
import modelo.Recurso;
import modelo.RecursoEspecifico;
import modelo.RecursoXProveedor;
import modelo.TareaPlanificacion;
import org.hibernate.HibernateException;
import util.HibernateUtil;
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

    public GestorEjecucion(VentanaEjecucion vista, int idObra) {
        this.pantalla = vista;
        this.idObra = idObra;        
        cargarObra(idObra);
    }
    
    public int getIdObraActual(){
        return idObra;
    }
    
    private void mostrarMensajeError(String msg) {
        System.err.println("[ERROR] " + msg);
        pantalla.mostrarMensaje(JOptionPane.ERROR_MESSAGE, "Error!", msg);
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
            if(ejecucion==null)
            {mostrarMensajeError("La obra no contiene una ejecucion");}
            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error: Se produjo un error al cargar la Obra:\n" + e.getMessage());
            throw new HibernateException("Error: Se produjo un error al cargar la Obra");
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
    
    
}
