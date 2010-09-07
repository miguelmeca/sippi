package util;

/**
 * Descripción: Utilidades varias para tratamiento de cadenas de texto
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */
public class StringUtil {


    /**
     * Cuenta la candidad de veces que una cadena se repite dentro de otra.
     * @param cadena La cadena más larga, en la que buscamos las ocurrencias
     * @param patron Lo que buscamos dentro de la otra cadena
     * @return Cantidad de ocurrencias
     */
    public static int cantidadOcurrencias(String cadena, String patron)
    {
        int count = 0; int start = 0; int len = patron.length();
        while((start = cadena.indexOf(patron, start)) > -1)
        {
            count++;
            start += len;
        }
        return count;

//        int cant = 0;
//        while (cadena.indexOf(patron) > -1)
//        {
//              cadena = cadena.substring(cadena.indexOf(patron)+patron.length(),cadena.length());
//              cant++;
//        }
        // return cant;

    }

}
