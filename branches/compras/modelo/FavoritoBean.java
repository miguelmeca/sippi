/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Iuga
 */
public class FavoritoBean {
    
    private int id;
    private String instance;
    private String nombre;
    private long counts;
    private String icono;

    public FavoritoBean(String _instance, String nombre, long counts, String icono) {
        this.instance = _instance;
        this.nombre = nombre;
        this.counts = counts;
        this.icono = icono;
    }

    public FavoritoBean() {
    }
    
    public String getInstance() {
        return instance;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInstance(String _instance) {
        this.instance = _instance;
    }

    public void setCounts(long counts) {
        this.counts = counts;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    
}
