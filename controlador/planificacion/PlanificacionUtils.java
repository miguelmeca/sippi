/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion;

import modelo.PlanificacionXXX;
import modelo.TareaPlanificacion;

/**
 *
 * @author Administrador
 */
public class PlanificacionUtils {
    
    /**
     * Se le pasa un id de Tarea del Gantt y te retorna la Tarea Asociada a ese ID
     * @param plan
     * @param idTareaGantt
     * @return 
     */
    public static TareaPlanificacion getTareaFromGantt(PlanificacionXXX plan, int idTareaGantt)
    {
        return plan.buscarTareaPorIdTareaGantt(idTareaGantt);
    }
    
}
