/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.PlanificacionXXX;
import modelo.TareaPlanificacion;
import modelo.TipoTarea;
import util.HibernateUtil;
import vista.planificacion.PlanificacionDatosGenerales;

/**
 *
 * @author Emmanuel
 */
public class GestorPlanificacionDatosGenerales implements IGestorPlanificacion{
    private PlanificacionDatosGenerales pantalla;
    private GestorEditarTarea gestorPadre;

    public GestorPlanificacionDatosGenerales(GestorEditarTarea gestorPadre){
        this.gestorPadre = gestorPadre;
    }

    public void setPantalla(PlanificacionDatosGenerales pantalla) {
        this.pantalla = pantalla;
    }

    public void cargarDatosTarea() {
        TareaPlanificacion tarea = gestorPadre.getTareaActual();
        if(tarea!=null){
            pantalla.mostrarDatos(tarea.getNombre(),
                tarea.getTipoTarea().getNombre(),
                tarea.getFechaInicio(),
                tarea.getFechaFin(),
                tarea.getObservaciones());
        }
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

    public void actualizarNombre(String text) {
        this.getTareaActual().setNombre(text);
    }

    public void actualizarObservaciones(String text) {
        this.getTareaActual().setObservaciones(text);
    }

    public void actualizarTipoTarea(String tipoTarea) {
        try {
            TipoTarea tt = (TipoTarea) HibernateUtil.getSession().createQuery("FROM TipoTarea WHERE nombre = :tipoTarea").setParameter("tipoTarea", tipoTarea).uniqueResult();
            this.getTareaActual().setTipoTarea(tt);
        } catch (Exception ex) {
            Logger.getLogger(GestorPlanificacionDatosGenerales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarFechaInicio(Date date) {
        this.getTareaActual().setFechaInicio(date);
    }

    public void actualizarFechaFin(Date date) {
        this.getTareaActual().setFechaFin(date);
    }

    public void cargarTiposDeTarea() {
        try {
            Iterator<TipoTarea> tipoTareas = HibernateUtil.getSession().createQuery("FROM TipoTarea").iterate();
            while(tipoTareas.hasNext()){
                TipoTarea tipoTarea= tipoTareas.next();
                pantalla.agregarElementoAlCombo(tipoTarea.getNombre());
            }
        } catch (Exception ex) {
            Logger.getLogger(GestorPlanificacionDatosGenerales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
