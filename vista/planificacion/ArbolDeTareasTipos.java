/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import config.Iconos;

/**
 *
 * @author Administrador
 */
public class ArbolDeTareasTipos {
    
    public static final String TIPO_SUBOBRA = "Sub Obra";
    public static final String TIPO_TAREA = "Tarea";
    public static final String TIPO_OBRA = "Obra";
    public static final String TIPO_HERRAMIENTA = "Herramienta";
    public static final String TIPO_MATERIAL = "Material";
    public static final String TIPO_ALQUILERCOMPRA = "AlquilerCompra";
    
    public static final String TIPO_TAREAS = "Tareas";
    public static final String TIPO_HERRAMIENTAS = "Herramientas";
    public static final String TIPO_MATERIALES = "Materiales";
    public static final String TIPO_ALQUILERESCOMPRAS = "AlquileresCompras";
    
    public static final String TIPO_ASIGNACIONESEMPLEADOS = "AsignacionesEmpleados";
    public static final String TIPO_ASIGNACIONEMPLEADO = "AsignacionEmpleado";
    
    public static String getIcono(String tipo)
    {
       if(tipo.equals(TIPO_OBRA))
       {return Iconos.ICONO_OBRA;}
       if(tipo.equals(TIPO_SUBOBRA))
       {return Iconos.ICONO_SUBOBRA;}
       
       if(tipo.equals(TIPO_TAREAS))
       {return Iconos.ICONO_TAREAS;}
       if(tipo.equals(TIPO_TAREA))
       {return Iconos.ICONO_TAREA;}
       
       if(tipo.equals(TIPO_HERRAMIENTAS))
       {return Iconos.ICONO_HERRAMIENTAS;}
       if(tipo.equals(TIPO_HERRAMIENTA))
       {return Iconos.ICONO_HERRAMIENTA;}
       
       if(tipo.equals(TIPO_MATERIALES))
       {return Iconos.ICONO_MATERIALES;}
       if(tipo.equals(TIPO_MATERIAL))
       {return Iconos.ICONO_MATERIAL;}
       
       if(tipo.equals(TIPO_ALQUILERESCOMPRAS))
       {return Iconos.ICONO_ALQUILERESCOMPRAS;}
       if(tipo.equals(TIPO_ALQUILERCOMPRA))
       {return Iconos.ICONO_ALQUILERCOMPRA;}
       
       if(tipo.equals(TIPO_HERRAMIENTAS))
       {return Iconos.ICONO_HERRAMIENTAS;}
       if(tipo.equals(TIPO_HERRAMIENTA))
       {return Iconos.ICONO_HERRAMIENTA;}
       
       return null;
    }
    
    public static String getTipoColectivo(String tipoIndividual)
    {
       if(tipoIndividual.equals(TIPO_SUBOBRA))
       {return TIPO_OBRA;}
       
       if(tipoIndividual.equals(TIPO_TAREA))
       {return TIPO_TAREAS;}
       
       if(tipoIndividual.equals(TIPO_HERRAMIENTA))
       {return TIPO_HERRAMIENTAS;}
       
       if(tipoIndividual.equals(TIPO_MATERIAL))
       {return TIPO_MATERIALES;}
       
       if(tipoIndividual.equals(TIPO_ALQUILERCOMPRA))
       {return TIPO_ALQUILERESCOMPRAS;}
       
       if(tipoIndividual.equals(TIPO_HERRAMIENTA))
       {return TIPO_HERRAMIENTAS;}
       
       if(tipoIndividual.equals(TIPO_ASIGNACIONEMPLEADO))
       {return TIPO_ASIGNACIONESEMPLEADOS;}
       
       return null;
    }
    
    public static String getTipoIndividual(String tipoColectivo)
    {
       if(tipoColectivo.equals(TIPO_OBRA))
       {return TIPO_SUBOBRA;}
       
       if(tipoColectivo.equals(TIPO_TAREAS))
       {return TIPO_TAREA;}
       
       if(tipoColectivo.equals(TIPO_HERRAMIENTAS))
       {return TIPO_HERRAMIENTA;}
       
       if(tipoColectivo.equals(TIPO_MATERIALES))
       {return TIPO_MATERIAL;}
       
       if(tipoColectivo.equals(TIPO_ALQUILERESCOMPRAS))
       {return TIPO_ALQUILERCOMPRA;}
       
       if(tipoColectivo.equals(TIPO_HERRAMIENTAS))
       {return TIPO_HERRAMIENTA;}
       
       if(tipoColectivo.equals(TIPO_ASIGNACIONESEMPLEADOS))
       {return TIPO_ASIGNACIONEMPLEADO;}
       
       
       return null;
    }
       
    public static String[] getPadres()
    {
        String padres[] =new String[8];
        padres[0]=TIPO_SUBOBRA;
        padres[1]=TIPO_OBRA;
        padres[2]=TIPO_TAREA;
        padres[3]=TIPO_TAREAS;
        padres[4]=TIPO_HERRAMIENTAS;
        padres[5]=TIPO_MATERIALES;
        padres[6]=TIPO_ALQUILERESCOMPRAS;
        padres[7]=TIPO_ASIGNACIONESEMPLEADOS;
        return padres;
    }
    
    public static String[] getSinHijos()
    {
        String sinHijos[] =new String[3];
        sinHijos[0]=TIPO_MATERIAL;
        sinHijos[1]=TIPO_HERRAMIENTA;
        sinHijos[2]=TIPO_ALQUILERCOMPRA;
        
        return sinHijos;
    }
    
    public static String[] getGruposRecursos()
    {
        String padres[] =new String[4];
        padres[0]=TIPO_MATERIALES;
        padres[1]=TIPO_HERRAMIENTAS;
        padres[2]=TIPO_ALQUILERESCOMPRAS;
        padres[3]=TIPO_ASIGNACIONESEMPLEADOS;
        return padres;
    }
    
}
