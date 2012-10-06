package controlador.ejecucion;

import java.util.ArrayList;
import modelo.Ejecucion;
import modelo.TareaEjecucion;
import modelo.TareaPlanificacion;

/**
 *
 * @author Iuga
 */
public class EjecucionUtils {
    
    /**
     * Busca Recursivamente y devuelve Todas las Tareas y SubTareas que haya en una ejecucion
     * @param ejecucion
     * @return 
     */
    public static ArrayList<TareaEjecucion> getTodasTareasEjecucion(Ejecucion ejecucion)
    {
        ArrayList<TareaEjecucion> lista = new ArrayList<TareaEjecucion>();
        
        for (int i = 0; i < ejecucion.getListaTareas().size(); i++) {
            TareaEjecucion tarea = ejecucion.getListaTareas().get(i);
            getTareasRecursivas(lista, tarea);
        }
        
        return lista;
    }
    private static void getTareasRecursivas(ArrayList<TareaEjecucion> lista, TareaEjecucion tarea)
    {
        lista.add(tarea);
        for (int i = 0; i < tarea.getSubtareas().size(); i++) {
            TareaEjecucion tarearec = tarea.getSubtareas().get(i);
            getTareasRecursivas(lista, tarearec);
        }        
    }
    
}
