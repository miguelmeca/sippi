package controlador.ejecucion;

import java.util.ArrayList;
import modelo.Ejecucion;
import modelo.PedidoObra;
import modelo.TareaEjecucion;
import util.HibernateUtil;

/**
 *
 * @author Iuga
 */
public class EjecucionUtils {

    /**
     * Busca Recursivamente y devuelve Todas las Tareas y SubTareas que haya en
     * una ejecucion
     *
     * @param ejecucion
     * @return
     */
    public static ArrayList<TareaEjecucion> getTodasTareasEjecucion(Ejecucion ejecucion) {
        ArrayList<TareaEjecucion> lista = new ArrayList<TareaEjecucion>();

        for (int i = 0; i < ejecucion.getTareas().size(); i++) {
            TareaEjecucion tarea = ejecucion.getTarea(i);
            getTareasRecursivas(lista, tarea);
        }

        return lista;
    }

    private static void getTareasRecursivas(ArrayList<TareaEjecucion> lista, TareaEjecucion tarea) {
        lista.add(tarea);
        for (int i = 0; i < tarea.getSubtareas().size(); i++) {
            TareaEjecucion tarearec = (TareaEjecucion)tarea.getSubtareas().get(i);
            getTareasRecursivas(lista, tarearec);
        }
    }

    /**
     * Desde un ID de Ejecucion, busco y reetorno el PedidoDeObra
     *
     * @param id
     * @return
     */
    public static PedidoObra getNumeroObraFromNumeroEjecucion(int idEjecucion) {
        try {
            HibernateUtil.beginTransaction();
            PedidoObra po = (PedidoObra) HibernateUtil.getSession().createQuery("FROM PedidoObra po WHERE :cID = po.ejecucion.id").setParameter("cID", idEjecucion).uniqueResult();
            HibernateUtil.commitTransaction();
            if (po != null) {
                return po;
            }
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            return null;
        }
        return null;
    }
    
    /**
     * Retorna el número de Orden de Trabajo, lo separe en otro método por las dudas
     * que lo querramos cambiar así está centralizado.
     * @param nroPedidoObra
     * @param nroTarea
     * @return 
     */
    public static String getNumeroOrdenDeTrabajo(int nroPedidoObra, int nroTarea){
        return nroPedidoObra+"-"+nroTarea;
    }

    /**
     * Se le pasa un hash de cualquier tarea y lo retorna esa tarea.
     * Retorna NULL si no la encuentra.
     * @param tareaHash
     * @return 
     */
    public static TareaEjecucion getTareaFromHash(Ejecucion ejecucion,int tareaHash) {
         ArrayList<TareaEjecucion> tareas = EjecucionUtils.getTodasTareasEjecucion(ejecucion);
         for (int i = 0; i < tareas.size(); i++) {
            TareaEjecucion tareaEjecucion = tareas.get(i);
            if(tareaEjecucion.hashCode()==tareaHash){
                return tareaEjecucion;
            }
        }
        return null;
    }
    
}
