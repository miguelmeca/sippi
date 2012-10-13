/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    
}
