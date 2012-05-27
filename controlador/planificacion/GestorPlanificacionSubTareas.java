/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import modelo.PlanificacionXXX;
import modelo.TareaPlanificacion;
import util.NTupla;
import vista.planificacion.PlanificacionSubTareas;

/**
 *
 * @author Emmanuel
 */
public class GestorPlanificacionSubTareas implements IGestorPlanificacion{
    private PlanificacionSubTareas pantalla;
    private GestorEditarTarea gestorPadre;
    private GestorEditarPlanificacion gestorPlanificacion;

    GestorPlanificacionSubTareas(GestorEditarTarea gestorPadre) {
        this.gestorPadre = gestorPadre;
        this.gestorPlanificacion = this.gestorPadre.getGestorPadre();
    }

    public PlanificacionXXX getPlanificacion() {
        return this.gestorPadre.getPlanificacion();
    }

    public TareaPlanificacion getTareaActual() {
        return this.gestorPadre.getTareaActual();
    }

    public void refrescarPantallas() {
        this.gestorPadre.refrescarPantallas();
    }

    public void setPantalla(PlanificacionSubTareas pantalla) {
        this.pantalla = pantalla;
    }

    public void cargarSubTareas() {
        pantalla.cargarSubTareas();
    }

    public ArrayList<NTupla> getSubTareas() {
        Iterator<TareaPlanificacion> itSubTareas = this.getTareaActual().getSubtareas().iterator();
        ArrayList<NTupla> subTareas = new ArrayList<NTupla>();
        while(itSubTareas.hasNext())
        {
            TareaPlanificacion subTarea = itSubTareas.next();
            NTupla nt = new NTupla();
            nt.setId(subTarea.hashCode());
            nt.setNombre(subTarea.getNombre());
            Date[] fechas = new Date[2];
            fechas[0] = subTarea.getFechaInicio();
            fechas[1] = subTarea.getFechaFin();
            nt.setData(fechas);
            subTareas.add(nt);
        }
        return subTareas;
    }

    public void setGestorPlanificacion(GestorEditarPlanificacion gestor) {
        this.gestorPlanificacion = gestor;
    }

    public GestorEditarPlanificacion getGestorPlanificacion() {
        return gestorPlanificacion;
    }

    public PlanificacionSubTareas getPantalla() {
        return pantalla;
    }

    public GestorEditarTarea getGestorPadre() {
        return gestorPadre;
    }

    public TareaPlanificacion getSubTareaPlanificada(int hashcode){
        Iterator<TareaPlanificacion> it =  this.getTareaActual().getSubtareas().iterator();
        TareaPlanificacion subtarea = null;
        while(it.hasNext())
        {
            TareaPlanificacion ta = it.next();
            if(ta.hashCode() == hashcode)
            {
                subtarea = ta;
                break;
            }
        }
        return subtarea;
    }

    public String getNombreTareaContenedora() {
        List<TareaPlanificacion> tareas = this.getTareaActual().buscarCaminoHastaTareaConCotizacion(this.gestorPadre.getPlanificacion(),false,true);
        for (int i = 0; i < tareas.size(); i++) {
            TareaPlanificacion tareaPlanificacion = tareas.get(i);
            return tareaPlanificacion.getNombre();
        }
        return "";
    }
}
