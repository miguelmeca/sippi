/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

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
    
       
    public static final String[] getPadres()
    {
        String padres[] =new String[5];
        padres[0]=TIPO_SUBOBRA;
        padres[1]=TIPO_OBRA;
        padres[2]=TIPO_TAREA;
        padres[3]=TIPO_TAREAS;
        padres[4]=TIPO_HERRAMIENTAS;
        padres[5]=TIPO_MATERIALES;
        padres[6]=TIPO_ALQUILERESCOMPRAS;
        return padres;
    }
    
    public static final String[] getSinHijos()
    {
        String sinHijos[] =new String[5];
        sinHijos[0]=TIPO_MATERIAL;
        sinHijos[1]=TIPO_HERRAMIENTA;
        sinHijos[2]=TIPO_ALQUILERCOMPRA;
        return sinHijos;
    }
    
}
