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

    public static DefaultTableModel vaciarDefaultTableModel(DefaultTableModel modelo)
    {
           int a = modelo.getRowCount();
           for(int i=0; i<a;i++)
           {
                modelo.removeRow(0);
           }
           return modelo;
    }
}
