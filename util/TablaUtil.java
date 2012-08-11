/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import javax.swing.table.DefaultTableModel;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

public class TablaUtil {

    /**
     * Elimina todas las filas de una Tabla
     * @param modelo
     * @return 
     */
    public static DefaultTableModel vaciarDefaultTableModel(DefaultTableModel modelo)
    {
           int a = modelo.getRowCount();
           for(int i=0; i<a;i++)
           {
                modelo.removeRow(0);
           }
           return modelo;
    }
    
    /**
     * Para la columna dada por 'columna:int' de una tabla:
     * Si 
     *   seleccion = true: selecciona toda esa columna
     *   seleccion = false: deselecciona toda la columna 
     * @param columna
     * @param seleccion 
     */
    public static void cambiarSeleccionarTodos(DefaultTableModel modelo,int columna,boolean seleccion)
    {
           int a = modelo.getRowCount();
           for(int i=0; i<a;i++)
           {
               try
               {
                modelo.setValueAt(seleccion,i,columna);
               }
               catch(ArrayIndexOutOfBoundsException ex)
               {
                   System.err.println("No se pudo cambiar el valor de la columna en TablaUtil.cambiarSeleccionarTodos");
               }
           }     
    }
    
   /**
     * Para la columna dada por 'columna:int' de una tabla:
     * Si 
     *   seleccion = true: selecciona toda esa columna
     *   seleccion = false: deselecciona toda la columna 
     * @param columna
     * @param seleccion 
     */
    public static int getCantidadSeleccionados(DefaultTableModel modelo,int columna,boolean seleccion)
    {
           int count = 0;
           int a = modelo.getRowCount();
           for(int i=0; i<a;i++)
           {
               try
               {
                 boolean data = (Boolean) modelo.getValueAt(i,columna);
                 if(data==seleccion)
                 {
                     count++;
                 }
               }
               catch(ArrayIndexOutOfBoundsException ex)
               {
                   System.err.println("No se pudo cambiar el valor de la columna en TablaUtil.cambiarSeleccionarTodos");
               }
           }     
           return count;
    }    
}
