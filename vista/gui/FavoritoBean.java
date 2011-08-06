/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.gui;

import javax.swing.Icon;

/**
 *
 * @author Iuga
 */
public class FavoritoBean {
    
    private String _instance;
    private String nombre;
    private long counts;
    private String icono;

    public FavoritoBean(String _instance, String nombre, long counts, String icono) {
        this._instance = _instance;
        this.nombre = nombre;
        this.counts = counts;
        this.icono = icono;
    }
    
    public String getInstance() {
        return _instance;
    }

    public long getCounts() {
        return counts;
    }

    public String getIcono() {
        return icono;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void addCount()
    {
        this.counts++;
    }
    
    
    
}
