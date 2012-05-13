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
import vista.planificacion.EditarTareaEsfuerzo;

/**
 *
 * @author Fran
 */
public class GestorEditarTareaEsfuerzo implements IGestorPlanificacion{
    private EditarTareaEsfuerzo pantalla;
    private GestorEditarTarea gestorPadre;

    public GestorEditarTareaEsfuerzo(GestorEditarTarea gestorPadre){
        this.gestorPadre = gestorPadre;
    }

    public void setPantalla(EditarTareaEsfuerzo pantalla) {
        this.pantalla = pantalla;
    }

    public void cargarDatosTarea() {
        TareaPlanificacion tarea = gestorPadre.getTareaActual();
        if(tarea!=null && tarea.getId() != 0){
           /* pantalla.mostrarDatos(tarea.getNombre(),
                tarea.getTipoTarea().getNombre(),
                tarea.getFechaInicio(),
                tarea.getFechaFin(),
                tarea.getObservaciones());*/
        }
    }
    
    public void cargarComboTareasSuperiores() {
        try {
            
        } catch (Exception ex) {
            Logger.getLogger(GestorEditarTareaEsfuerzo.class.getName()).log(Level.SEVERE, null, ex);
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


    


    
}
