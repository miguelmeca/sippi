/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

    GestorPlanificacionSubTareas(GestorEditarTarea gestorPadre) {
        this.gestorPadre = gestorPadre;
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
            nt.setId(subTarea.getId());
            nt.setNombre(subTarea.getNombre());
            Date[] fechas = new Date[2];
            fechas[0] = subTarea.getFechaInicio();
            fechas[1] = subTarea.getFechaFin();
            nt.setData(fechas);
        }
        return subTareas;
    }
}
