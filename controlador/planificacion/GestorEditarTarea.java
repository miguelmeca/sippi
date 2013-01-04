/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;


import java.util.Iterator;
import modelo.DetalleTareaPlanificacion;
import modelo.Planificacion;
import modelo.PlanificacionXAlquilerCompra;
import modelo.PlanificacionXHerramienta;
import modelo.PlanificacionXMaterial;
import modelo.TareaPlanificacion;
import util.HibernateUtil;
import vista.planificacion.PantallaEditarTarea;

/**
 *
 * @author Emmanuel
 */
public class GestorEditarTarea implements IGestorPlanificacion{
    private PantallaEditarTarea pantalla;
    private TareaPlanificacion tarea;
    private TareaPlanificacion tareaMemoria;

    // GESTORES
    private GestorEditarPlanificacion gestorPadre;
    private GestorEditarTarea gestorTareaPadre;

    // LISTA DE GESTORES
    private GestorPlanificacionDatosGenerales gestorDatosGenerales;
    private GestorPlanificacionSubTareas gestorSubTareas;
    private GestorPlanificacionAsigEmpleados gestorAsigEmpleados;
    private GestorPlanificacionHerramientas gestorHerramientas;
    private GestorPlanificacionMateriales gestorMateriales;
    private GestorPlanificacionAlquileresCompras gestorAlquileresCompras;
    private GestorEditarTareaDetalles gestorEsfuerzo;

    public GestorEditarTarea(GestorEditarPlanificacion gestor)
    {
        this.gestorPadre = gestor;
        this.tareaMemoria = new TareaPlanificacion();
    }

    public GestorEditarTarea getGestorTareaPadre() {
        return gestorTareaPadre;
    }

    public void setGestorTareaPadre(GestorEditarTarea gestorTareaPadre) {
        this.gestorTareaPadre = gestorTareaPadre;
    }

    public PantallaEditarTarea getPantalla() {
        return pantalla;
    }
    
    public void setPantalla(PantallaEditarTarea pantalla) {
        this.pantalla = pantalla;
    }

    public void seleccionarTarea(int idTarea)
    {
        if(idTarea != 0) // Si se trata de modificar una existente
        {
            this.tarea = gestorPadre.getPlanificacion().buscarTarea(idTarea);
        }
        else // Si se trata de una tarea nueva
        {
            this.tarea = new TareaPlanificacion();
        }
    }

    public void seleccionarTarea(TareaPlanificacion tarea){
        this.tarea = tarea;
        tareaMemoria.setNombre(tarea.getNombre());
        tareaMemoria.setObservaciones(tarea.getObservaciones());
        tareaMemoria.setTipoTarea(tarea.getTipoTarea());
        tareaMemoria.setFechaInicio(tarea.getFechaInicio());
        tareaMemoria.setFechaFin(tarea.getFechaFin());
        copiarEstadoTarea(tarea, tareaMemoria);
    }

    public GestorPlanificacionDatosGenerales getGestorDatosGenerales()
    {
        if(this.gestorDatosGenerales==null)
        {
            this.gestorDatosGenerales = new GestorPlanificacionDatosGenerales(this);
        }
        return gestorDatosGenerales;
    }

    public GestorPlanificacionSubTareas getGestorSubTareas()
    {
        if(this.gestorSubTareas==null)
        {
            this.gestorSubTareas = new GestorPlanificacionSubTareas(this);
        }
        return gestorSubTareas;
    }
    
    public GestorEditarTareaDetalles getGestorEsfuerzo()
    {
        if(this.gestorEsfuerzo==null)
        {
            this.gestorEsfuerzo = new GestorEditarTareaDetalles(this);
        }
        return gestorEsfuerzo;
    }

    public GestorPlanificacionAsigEmpleados getGestorAsigEmpleados()
    {
        if(this.gestorAsigEmpleados==null)
        {
            this.gestorAsigEmpleados = new GestorPlanificacionAsigEmpleados(this);
        }
        return gestorAsigEmpleados;
    }

    public GestorPlanificacionHerramientas getGestorHerramientas()
    {
        if(this.gestorHerramientas==null)
        {
            this.gestorHerramientas = new GestorPlanificacionHerramientas(this);
        }
        return gestorHerramientas;
    }

    public GestorPlanificacionMateriales getGestorMateriales()
    {
        if(this.gestorMateriales==null)
        {
            this.gestorMateriales = new GestorPlanificacionMateriales(this);
        }
        return gestorMateriales;
    }

    public GestorPlanificacionAlquileresCompras getGestorAlquileresCompras()
    {
        if(this.gestorAlquileresCompras==null)
        {
            this.gestorAlquileresCompras = new GestorPlanificacionAlquileresCompras(this);
        }
        return gestorAlquileresCompras;
    }

    @Override
    public void refrescarPantallas() {
        gestorPadre.refrescarPantallas();
        pantalla.actualizarPantallas();
    }

    @Override
    public Planificacion getPlanificacion() {
        return this.gestorPadre.getPlanificacion();
    }

    @Override
    public TareaPlanificacion getTareaActual() {
        return this.tarea;
    }

    public void guardarCambios() 
    {
        if(gestorTareaPadre == null){
            this.gestorPadre.refrescarPantallas();
            this.pantalla.setVisible(false);
        }
        else
        {
            boolean banMod = false;
            Iterator<TareaPlanificacion> subtareas = this.gestorTareaPadre.getTareaActual().getSubtareas().iterator();
            while(subtareas.hasNext())
            {
                TareaPlanificacion st = subtareas.next();
                if(st.hashCode() == this.tarea.hashCode())
                {
                    int index = this.gestorTareaPadre.getTareaActual().getSubtareas().indexOf(st);
                    this.gestorTareaPadre.getTareaActual().getSubtareas().set(index, this.tarea);
                    banMod = true;
                    break;
                }
            }
            if(!banMod)
            {
                this.gestorTareaPadre.getTareaActual().addSubTarea(tarea);
            }
        }
        gestorPadre.cerrarVentanaEditarTarea(tarea.hashCode());
    }

    public GestorEditarPlanificacion getGestorPadre() {
        return gestorPadre;
    }

//    public void agregarSubTarea() {
//        this.gestorTareaPadre.getTareaActual().addSubTarea(tarea);
//    }

    public boolean eliminarComoSubTarea(StringBuilder mensaje) {
        System.out.println("[DEBUG] Eliminar como SubTarea");
        if(PlanificacionUtils.puedeEliminarseTarea(getTareaActual()))
        {
            if(PlanificacionUtils.eliminarTarea(this.getGestorTareaPadre().getGestorPadre().getPlanificacion(), tarea))
            {
                // Se elimino correctamente
                mensaje.append("Se eliminó correctamente la Tarea");
                return true;
            }
            else
            {
                mensaje.append("Se produjo un error al borrar la Tarea");
                return false;
            }
        }
        else
        {
            // Avisar que esta tarea no puede eliminarse
            mensaje.append("Esta tarea no puede eliminarse\nCompruebe que no tiene SubTareas cargadas");
            return false;
        }
    }
    
    public void actualizarPantallaPadre(){
        this.gestorPadre.refrescarPantallas();
    }

    public void setTareaActual(TareaPlanificacion tarea) {
        this.tarea = tarea;
    }

    public TareaPlanificacion getTareaMemoria() {
        return tareaMemoria;
    }
    
    /**
     * Método que copia el estado de los item de las colecciones que no han sido
     * persistidos provenientes de la tareaOrigen a la tareaDestino.
     * Las colecciones contempladas son: Materiales, Herramientas, Alquileres/
     * Compras, SubTareas y DetalleTareaPlanificacion.
     * @param tareaOrigen Tarea desde la cual se copiaran los items no persisti-
     * dos
     * @param tareaDestino Tarea a la cual se copiaran los items no persistidos
     * en la tareaOrigen.
     */
    public void copiarEstadoTarea(TareaPlanificacion tareaOrigen, TareaPlanificacion tareaDestino)
    {
        if(tareaDestino == null) { tareaDestino = new TareaPlanificacion(); }

        for(int i=0; i<tareaOrigen.getMateriales().size(); i++)
        {
            PlanificacionXMaterial material = tareaOrigen.getMateriales().get(i);
            if(material.getId() == 0)
            {
                tareaDestino.getMateriales().add(material);
            }
        }
        for(int i=0; i<tareaOrigen.getHerramientas().size(); i++)
        {
            PlanificacionXHerramienta herramienta = tareaOrigen.getHerramientas().get(i);
            if(herramienta.getId() == 0)
            {
                tareaDestino.getHerramientas().add(herramienta);
            }
        }
        for(int i=0; i<tareaOrigen.getAlquilerCompras().size(); i++)
        {
            PlanificacionXAlquilerCompra alquilerCompra = tareaOrigen.getAlquilerCompras().get(i);
            if(alquilerCompra.getId() == 0)
            {
                tareaDestino.getAlquilerCompras().add(alquilerCompra);
            }
        }
        for(int i=0; i<tareaOrigen.getSubtareas().size(); i++)
        {
            TareaPlanificacion subTarea = tareaOrigen.getSubtareas().get(i);
            if(subTarea.getId() == 0)
            {
                tareaDestino.getSubtareas().add(subTarea);
            }
        }
        for(int i=0; i<tareaOrigen.getDetalles().size(); i++)
        {
            DetalleTareaPlanificacion detalle = tareaOrigen.getDetalles().get(i);
            if(detalle.getId() == 0)
            {
                tareaDestino.getDetalles().add(detalle);
            }
        }
    }
    
    /**
     * Método que quita todo aquel objeto no persistido de la colecciones de
     * la tareaALimpiar. Las colecciones analizadas son Materiales, Herramien-
     * tas, Alquileres/Compras, SubTareas y DetallesTareaPlanificación. 
     * Esta función es necesaria ya que el método HibernateUtil.getSession().
     * refresh(Object object) larga una excepción al encontrarse con un compo-
     * nente no persistido dentro de la tarea que se quiere refrescar.
     * @param tareaALimpiar La tarea a limpiar.
     */
    public void removerNoPersistidos(TareaPlanificacion tareaALimpiar)
    {
        if(tareaALimpiar != null)
        {
            for(int i=0; i<tareaALimpiar.getMateriales().size(); i++)
            {
                PlanificacionXMaterial material = tareaALimpiar.getMateriales().get(i);
                if(material.getId() == 0)
                {
                    tareaALimpiar.getMateriales().remove(material);
                }
            }
            for(int i=0; i<tareaALimpiar.getHerramientas().size(); i++)
            {
                PlanificacionXHerramienta herramienta = tareaALimpiar.getHerramientas().get(i);
                if(herramienta.getId() == 0)
                {
                    tareaALimpiar.getHerramientas().remove(herramienta);
                }
            }
            for(int i=0; i<tareaALimpiar.getAlquilerCompras().size(); i++)
            {
                PlanificacionXAlquilerCompra alquilerCompra = tareaALimpiar.getAlquilerCompras().get(i);
                if(alquilerCompra.getId() == 0)
                {
                    tareaALimpiar.getAlquilerCompras().remove(alquilerCompra);
                }
            }
            for(int i=0; i<tareaALimpiar.getSubtareas().size(); i++)
            {
                TareaPlanificacion subTarea = tareaALimpiar.getSubtareas().get(i);
                if(subTarea.getId() == 0)
                {
                    tareaALimpiar.getSubtareas().remove(subTarea);
                }
            }
            for(int i=0; i<tareaALimpiar.getDetalles().size(); i++)
            {
                DetalleTareaPlanificacion detalle = tareaALimpiar.getDetalles().get(i);
                if(detalle.getId() == 0)
                {
                    tareaALimpiar.getDetalles().remove(detalle);
                }
            }
        }
    }
    
    /**
     * Método que permite cancelar los cambios realizados sobre la tarea actual
     * @throws Exception Provoca una excepción cuando se aplica el refresco
     * de la instancia de la tarea a ser restaurada.
     */
    public void cancelarModificacionTareaActual() throws Exception
    {
        if(tarea != null && tarea.getId() > 0)
        {
            removerNoPersistidos(tarea);

            // Refresco la entidad habiendo quitado todos los componentes no 
            // persistidos
            HibernateUtil.getSession().refresh(this.tarea);

            tarea.setNombre(tareaMemoria.getNombre());
            tarea.setObservaciones(tareaMemoria.getObservaciones());
            tarea.setTipoTarea(tareaMemoria.getTipoTarea());
            tarea.setFechaInicio(tareaMemoria.getFechaInicio());
            tarea.setFechaFin(tareaMemoria.getFechaFin());
            copiarEstadoTarea(tareaMemoria, tarea);
        }
    }
}