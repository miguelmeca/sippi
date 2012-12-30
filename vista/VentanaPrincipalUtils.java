/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.ejecucion.EjecucionUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Ejecucion;
import modelo.PedidoObra;
import modelo.TareaEjecucion;
import util.FechaUtil;
import util.HibernateUtil;
import util.NTupla;
import util.Tupla;

/**
 * @author iuga
 */
public class VentanaPrincipalUtils {
   
    /**
     * Retorna la fecha Actual, pero formateada para el encabezado de la solapa de Inicio
     * @return Fecha al estilo "23 de Junio"
     */
    public static String getFechaActual()
    {
        String fecha = "";
        Date hoy = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd ' de ' MMMM");
        
        try
        {
            fecha = formatter.format(hoy);
        }catch(Exception e)
        {
            System.err.println("Se produjo un error al formatear la fecha de hoy");
            fecha = "Hoy";
        }
        
        return fecha;
    }

    /**
     * Veo en todas las ejecuciones activas si hay alguna tarea para hoy !!
     * Para todas las ejecuciones en ALTA (Ejecutandose)
     *  Saco todas sus tareas
     *      Para cada tarea veo si la fecha actual está en su rango
     *          Y si la tarea no está finalizada
     *              Agrego.
     * Retorno una NTupla:
     *  El ID es el Hash de la Tarea Ejecucion.
     *  [0] = Ejecucion ID
     *  [1] = ID de la Obra
     *  [2] = Nombre de la Obra
     *  [3] = Fecha Inicio Tarea
     *  [4] = Fecha Fin Tarea
     * @param date
     * @return 
     */
    public static List<NTupla> getTareasParaFecha(Date fechaElegida) {
        List<NTupla> tareas = new ArrayList<NTupla>();
        
        try
        {
            HibernateUtil.beginTransaction();
            List<Ejecucion> ejecuciones = HibernateUtil.getSession().createQuery("FROM Ejecucion WHERE estado=:cEst").setParameter("cEst",Ejecucion.ESTADO_CREADA).list();
            
                for (int i = 0; i < ejecuciones.size(); i++) {
                    Ejecucion ejec = ejecuciones.get(i);
                     ArrayList<TareaEjecucion> tarea = EjecucionUtils.getTodasTareasEjecucion(ejec);
                     for (int j = 0; j < tarea.size(); j++) {
                        TareaEjecucion tareaEjecucion = tarea.get(j);
                        // Si la tarea esta ejecutandose para hoy
                        if(FechaUtil.fechaEnRango(fechaElegida, tareaEjecucion.getFechaInicio(),tareaEjecucion.getFechaFin())){
                            // Y si la tarea no está terminada:
                            if(tareaEjecucion.estaTerminada()){
                                NTupla ntp = new NTupla(tareaEjecucion.getId());
                                ntp.setNombre(tareaEjecucion.getNombre());
                                
                                    String[] data = new String[5];
                                    data[0] = String.valueOf(ejec.getId());
                                        // Busco la Obra:
                                        PedidoObra obra = EjecucionUtils.getPedidoObraFromNumeroEjecucion(ejec.getId());
                                        if(obra!=null){
                                            data[1] = String.valueOf(obra.getId());
                                            data[2] = obra.getNombre();
                                        }
                                    data[3] = FechaUtil.getFecha(tareaEjecucion.getFechaInicio());
                                    data[4] = FechaUtil.getFecha(tareaEjecucion.getFechaFin());
                                        
                                    ntp.setData(data);
                                    
                                tareas.add(ntp);
                            }
                        }
                    }
                }
            
            HibernateUtil.commitTransaction();

        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            System.err.println("Error:"+e.getMessage());
        } 
               
        return tareas;
    }
    
}
