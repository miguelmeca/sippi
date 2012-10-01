/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.planificacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.*;
import util.FechaUtil;
import util.NTupla;

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
     * Se le pasa un hash de Tarea del Gantt y te retorna la Tarea Asociada a ese hash.
     * @param plan
     * @param hash
     * @return
     */
    public static TareaPlanificacion getTareaFromHash(PlanificacionXXX plan, int hash)
    {
        return plan.buscarTareaPorHash(hash);
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
    
    /**
     * Busca Recursivamente y devuelve Todas las Tareas y SubTareas que haya en una planificacion
     * @param plan
     * @return 
     */
    public static ArrayList<TareaPlanificacion> getTodasTareasPlanificacion(PlanificacionXXX plan)
    {
        ArrayList<TareaPlanificacion> lista = new ArrayList<TareaPlanificacion>();
        
        for (int i = 0; i < plan.getTareas().size(); i++) {
            TareaPlanificacion tarea = plan.getTareas().get(i);
            getTareasRecursivas(lista, tarea);
        }
        
        return lista;
    }
    private static void getTareasRecursivas(ArrayList<TareaPlanificacion> lista, TareaPlanificacion tarea)
    {
        lista.add(tarea);
        for (int i = 0; i < tarea.getSubtareas().size(); i++) {
            TareaPlanificacion tarearec = tarea.getSubtareas().get(i);
            getTareasRecursivas(lista, tarearec);
        }        
    }
    
    /**
     * Busco en todas las tareas planificadas la cantidad de horas asignadas a cierta Herramienta (SubObraxHerramienta)
     * @param plan
     * @param sxh
     * @return 
     */
    public static int getHorasTotalesAsignadasAHerramienta(PlanificacionXXX plan, SubObraXHerramientaModif sxh)
    {
        int count = 0;
        ArrayList<TareaPlanificacion> listaTareas = getTodasTareasPlanificacion(plan);
        for (int i = 0; i < listaTareas.size(); i++) {
            TareaPlanificacion tarea = listaTareas.get(i);
            for (int j = 0; j < tarea.getHerramientas().size(); j++) {
                PlanificacionXHerramienta pxh = tarea.getHerramientas().get(j);
                if(pxh!=null && pxh.getHerramientaCotizacion()!=null)
                {
                    if(sxh.hashCode() == pxh.getHerramientaCotizacion().hashCode())
                    {
                        count += pxh.getHorasAsignadas();
                    }
                }
            }
        }
        return count;
    }
    
//    /**
//     * Busco en todas las tareas planificadas la cantidad del Material asignado (SubObraxMaterial)
//     * @param plan
//     * @param sxh
//     * @return 
//     */
//    public static int getCantidadAsignadaAMaterial(PlanificacionXXX plan, SubObraXMaterialModif sxm)
//    {
//        int count = 0;
//        ArrayList<TareaPlanificacion> listaTareas = getTodasTareasPlanificacion(plan);
//        for (int i = 0; i < listaTareas.size(); i++) {
//            TareaPlanificacion tarea = listaTareas.get(i);
//            for (int j = 0; j < tarea.getMateriales().size(); j++) {
//                PlanificacionXMaterial pxm = tarea.getMateriales().get(j);
//                if(pxm!=null && pxm.getMaterialCotizacion()!=null)
//                {
//                    if(sxm.hashCode() == pxm.getMaterialCotizacion().hashCode())
//                    {
//                        count += pxm.getCantidad();
//                    }
//                }
//            }
//        }
//        return count;
//    }
    
    /**
     * Busco en todas las tareas planificadas si la (SubObraxHerramienta) esta usandose
     * @param plan
     * @param sxh
     * @return 
     */
    public static boolean estaSubObraXHerramientaEnUso(PlanificacionXXX plan, SubObraXHerramienta sxh)
    {
        ArrayList<TareaPlanificacion> listaTareas = getTodasTareasPlanificacion(plan);
        for (int i = 0; i < listaTareas.size(); i++) {
            TareaPlanificacion tarea = listaTareas.get(i);
            for (int j = 0; j < tarea.getHerramientas().size(); j++) {
                PlanificacionXHerramienta pxh = tarea.getHerramientas().get(j);
                if(pxh!=null && pxh.getHerramientaCotizacion()!=null)
                {
                    if(sxh.hashCode() == pxh.getHerramientaCotizacion().hashCode())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Busco en todas las tareas planificadas si la (SubObraxMaterial) esta usandose
     * @param plan
     * @param sxm
     * @return 
     */
    public static boolean estaSubObraXMaterialEnUso(PlanificacionXXX plan, SubObraXMaterial sxm)
    {
        ArrayList<TareaPlanificacion> listaTareas = getTodasTareasPlanificacion(plan);
        for (int i = 0; i < listaTareas.size(); i++) {
            TareaPlanificacion tarea = listaTareas.get(i);
            for (int j = 0; j < tarea.getMateriales().size(); j++) {
                PlanificacionXMaterial pxm = tarea.getMateriales().get(j);
                if(pxm!=null && pxm.getMaterialCotizacion()!=null)
                {
                    if(sxm.hashCode() == pxm.getMaterialCotizacion().hashCode())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Busco en todas las tareas planificadas si la (SubObraxAlquilerCompra) esta usandose
     * @param plan
     * @param sxac
     * @return 
     */
    public static boolean estaSubObraXAlquilerCompraEnUso(PlanificacionXXX plan, SubObraXAlquilerCompra sxac)
    {
        ArrayList<TareaPlanificacion> listaTareas = getTodasTareasPlanificacion(plan);
        for (int i = 0; i < listaTareas.size(); i++) {
            TareaPlanificacion tarea = listaTareas.get(i);
            for (int j = 0; j < tarea.getAlquilerCompras().size(); j++) {
                PlanificacionXAlquilerCompra pxac = tarea.getAlquilerCompras().get(j);
                if(pxac!=null && pxac.getAlquilerCompraCotizacion()!=null)
                {
                    if(sxac.hashCode() == pxac.getAlquilerCompraCotizacion().hashCode())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static List<NTupla> getTareasQuePoseanMaterialAsignado(PlanificacionXXX plan, SubObraXMaterialModif sxm) {
        int count = 0;
        ArrayList<TareaPlanificacion> listaTareas = getTodasTareasPlanificacion(plan);
        List<NTupla> tareas = new ArrayList<NTupla>();
        for (int i = 0; i < listaTareas.size(); i++) {
            TareaPlanificacion tarea = listaTareas.get(i);
            for (int j = 0; j < tarea.getMateriales().size(); j++) {
                PlanificacionXMaterial pxm = tarea.getMateriales().get(j);
                if(pxm!=null && pxm.getMaterialCotizacion()!=null)
                {
                    if(sxm.hashCode() == pxm.getMaterialCotizacion().hashCode())
                    {
                        NTupla nt = new NTupla();
                        nt.setId(tarea.hashCode());
                        nt.setNombre(tarea.getNombre());
                        nt.setData(pxm.getCantidad());
                        tareas.add(nt);
                    }
                }
            }
        }
        return tareas;
    }
    
    public static List<NTupla> getTareasQuePoseanAlquilerCompraAsignado(PlanificacionXXX plan, SubObraXAlquilerCompraModif sxac) {
        int count = 0;
        ArrayList<TareaPlanificacion> listaTareas = getTodasTareasPlanificacion(plan);
        List<NTupla> tareas = new ArrayList<NTupla>();
        for (int i = 0; i < listaTareas.size(); i++) {
            TareaPlanificacion tarea = listaTareas.get(i);
            for (int j = 0; j < tarea.getAlquilerCompras().size(); j++) {
                PlanificacionXAlquilerCompra pxac = tarea.getAlquilerCompras().get(j);
                if(pxac!=null && pxac.getAlquilerCompraCotizacion()!=null)
                {
                    if(sxac.hashCode() == pxac.getAlquilerCompraCotizacion().hashCode())
                    {
                        NTupla nt = new NTupla();
                        nt.setId(tarea.hashCode());
                        nt.setNombre(tarea.getNombre());
                        nt.setData(pxac.getCantidad());
                        tareas.add(nt);
                    }
                }
            }
        }
        return tareas;
    }

    /**
     * Determina si una SubObraXTareaModif esta siendo usada en una Planificacion
     * @param plan
     * @param soxtm
     * @return 
     */
    public static boolean estaSubObraXTareaEnUso(PlanificacionXXX plan, SubObraXTareaModif soxtm) {
        ArrayList<TareaPlanificacion> listaTareas = getTodasTareasPlanificacion(plan);
        for (int i = 0; i < listaTareas.size(); i++) {
            TareaPlanificacion tarea = listaTareas.get(i);
            SubObraXTareaModif stm = tarea.getTareaCotizada();
            if(soxtm.hashCode()==stm.hashCode())
            {
                return true;
            }
        }
        return false;
    }
    
    public static TareaPlanificacion getTareaPadre(PlanificacionXXX plan,TareaPlanificacion tareaHija)
    {
        TareaPlanificacion padre = null;
        
        List<TareaPlanificacion> padres = tareaHija.buscarCaminoHastaTareaConCotizacion(plan,true,true);
        if(padres.size()>=2)
        {
            padre = padres.get(padres.size()-2);
        }
        
        return padre;
    }
    
    /**
     * Verifica si la fecha que se pasa puede ser su fecha de inicio o fin de la 
     * tarea hija, dependiendo de la tarea Padre ( la fecha de inicio y fin no pueden
     * exceder a las del padre )
     * @param plan Planificacion sobre la que se esta trabajando
     * @param inicio Boolean, TRUE valida la fecha de inicio, en FALSE la de Fin
     * @param date Fecha a validar
     * @param tareaHija Tarea a la que se le quiere cambiar la fecha de inicio/fin
     * @return 
     */
    public static boolean esFechaValidaParaHija(PlanificacionXXX plan,boolean inicio,Date date,TareaPlanificacion tareaHija)
    {
        if(tareaHija!=null)
        {
            List<TareaPlanificacion> padres = tareaHija.buscarCaminoHastaTareaConCotizacion(plan,true,true);
            if(padres.size()>=2)
            {
                TareaPlanificacion padre = padres.get(padres.size()-2);
                if(inicio)
                {
                    // Chequeo la fecha de inicio
                    if(FechaUtil.fechaMayorOIgualQue(date,padre.getFechaInicio()))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    // Chequeo la fecha de fin
                    // Chequeo la fecha de inicio
                    if(FechaUtil.fechaMayorOIgualQue(padre.getFechaFin(),date))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }                    
                }
            }
            else
            {
                // No tiene Padre, movimiento libre
                return true;
            }
        }
        else
        {
            return false;
        }  
    }

    /**
     * RECURSIVA:
     * Cuando le cambio la fecha de inicio a una tarea, verifico si es posible el cambio
     * y que sus tareas hijas no queden afuera de la tarea padre ( la que estoy modificando )
     * @param planificacion lanificacion sobre la que se esta trabajando
     * @param boolean Boolean, TRUE valida la fecha de inicio, en FALSE la de Fin
     * @param date Fecha a validar
     * @param tarea Tarea a la que se le quiere cambiar la fecha de inicio/fin
     * @return 
     */
    public static boolean esFechaValidaParaSusHijas(boolean inicio, Date date, TareaPlanificacion tarea) {
        
        if(tarea!=null)
        {
             for (int i = 0; i < tarea.getSubtareas().size(); i++) {
                TareaPlanificacion t = tarea.getSubtareas().get(i);
                if(inicio)
                {
                    // Si es la de inicio A la izquierda puedo mover libremente
                    if(!FechaUtil.fechaMayorOIgualQue(tarea.getFechaInicio(), date))
                    {
                        // Muevo a la derecha
                        if(!FechaUtil.fechaMayorOIgualQue(t.getFechaInicio(),date))
                        {
                            return false;
                        }
                    }
                }
                else
                {
                    // Si es la de Fin A la derecha puedo mover libremente
                    if(!FechaUtil.fechaMayorOIgualQue(date,tarea.getFechaFin()))
                    {
                        // muevo a la izquierda
                        if(!FechaUtil.fechaMayorOIgualQue(date,t.getFechaFin()))
                        {
                            return false;
                        }
                    }
                }
             }
        }
        else
        {
            return false;
        }
        return true; // No tiene hijas
    }
    
    
    
    

}
