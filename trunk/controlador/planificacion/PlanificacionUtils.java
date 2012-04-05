/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion;

import java.util.List;
import modelo.PlanificacionXXX;
import modelo.TareaPlanificacion;

/**
 *
 * @author Administrador
 */
public class PlanificacionUtils {
    
    /**
     * Se le pasa un id de Tarea del Gantt y te retorna la Tarea Asociada a ese ID.
     * Esto es porque el gantt no maneja los mismos IDS !!
     * @param plan
     * @param idTareaGantt
     * @return 
     */
    public static TareaPlanificacion getTareaFromGantt(PlanificacionXXX plan, int idTareaGantt)
    {
        return plan.buscarTareaPorIdTareaGantt(idTareaGantt);
    }
    
    /**
     * Busca la Tarea y la Elimina ( no importa si es una Tarea o SubTarea )
     * Si no se puede borrar directamente porque no se entontro la tarea devuelve false 
     * Devuelve True si lo pudo borrar exitosamente
     * @param plan
     * @param tarea
     * @return 
     */
    public static boolean eliminarTarea(PlanificacionXXX plan, TareaPlanificacion tarea)
    {
        for (int i = 0; i < plan.getTareas().size(); i++) {
            return eliminarTareaRecursivo(plan.getTareas(),tarea);
        }
        return false;
    }
    private static boolean eliminarTareaRecursivo(List<TareaPlanificacion> tareas, TareaPlanificacion tarea)
    {
        for (int i = 0; i < tareas.size(); i++) {
            TareaPlanificacion t = tareas.get(i);
            if(tarea.hashCode()==t.hashCode() && puedeEliminarseTarea(tarea))
            {
                // Es la mima tarea y puedo eliminarla
                System.out.println("[DEBUG] Se elimino la tarea "+tarea);
                tareas.remove(i);
                return true;
            }
            return eliminarTareaRecursivo(t.getSubtareas(),tarea);
        }
        return false;        
    }
    
    /**
     * El metodo verifica que una Tarea pueda eliminarse
     * Retorna TRUE si se puede, FALSE si no
     * REGLAS:
     * 1- NO puede eliminar una tarea si esta tiene otras SubTareas
     * X- SI puede eliminar una tarea si la/s reglas anteriores no se cumplen
     * @param tarea
     * @return 
     */
    public static boolean puedeEliminarseTarea(TareaPlanificacion tarea)
    {
        // Regla 1-
        if(tarea.getSubtareas().size()>0)
        {
            return false;
        }
        
        // Regla X-
        return true;
    }
    
    
}
