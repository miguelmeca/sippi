/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

/**
 * Procesa los mensajes del sistema, es un proxy para el System.out.println()
 * @author iuga
 */
public class LogUtil {

    /**
     * Envía un mensaje de error al Sistema de Logeo
     * @param error String
     */
    public static void addError(String error)
    {
        mostrarMensaje(error);
    }

    /**
     * Envía un mensaje al Sistema de Logeo
     * @param mensaje
     */
    public static void addMensaje(String mensaje)
    {
        mostrarMensaje(mensaje);
    }

    /**
     * Envía un mensaje de debug al systema de logeo
     * @param mensaje
     */
    public static void addDebug(String mensaje)
    {
        mostrarMensaje(mensaje);
    }

    /**
     * Por ahora solo muestra por pantalla
     * Más adelante puede registrar en un archivo o en la BD, lo veremos.
     * @param error
     */
    private static void mostrarMensaje(String error)
    {
        System.out.println("["+FechaUtil.getFechaYHoraActual()+"] "+error);
    }


}
