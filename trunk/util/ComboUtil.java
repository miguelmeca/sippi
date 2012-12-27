/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Emmanuel
 */
public class ComboUtil {
    
    /**
     * Método que permite seleccionar un elemento en un combo compuesto por
     * Tuplas
     * @param cb el combobox a ser seleccionado
     * @param id el id de la entidad a seleccionar
     */
    public static void seleccionarEnCombo(JComboBox cb, int id) {
        DefaultComboBoxModel dcbm = (DefaultComboBoxModel)cb.getModel();
        for(int i = 0 ; i < dcbm.getSize() ; i++)
        {
            if(dcbm.getElementAt(i) instanceof Tupla)
            {
                Tupla tupla = (Tupla) dcbm.getElementAt(i);
                if(tupla.getId() == id)
                {
                    cb.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
}
