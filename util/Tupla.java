/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import javax.swing.JComboBox;

/**
 *
 * @author Emmanuel
 */
public class Tupla {
    private int id;
    private String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tupla() {
    }

    public Tupla(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * @author Iuga
     * Este metodo es para que la tupla se pueda mostrar directamente en un
     * JComboBox y que cuando se seleccione un valor devuelva la tupla.
     * Para ver como funciona ir a pantallaRegistrarNuevaPlanta.mostrarPaises()
     * @return String
     */
    @Override
    public String toString()
    {
        return nombre;
    }

    /**
     * Del combo que se le pasa, selecciona la Tupla que corresponda a ese ID
     * @param combo
     * @param id 
     */
    public static void seleccionarTuplaPorId(JComboBox combo, int id)
    {
        for (int i = 0; i < combo.getItemCount(); i++) {
            Tupla tp = (Tupla) combo.getItemAt(i);
            if(tp.getId()==id)
            {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

}
