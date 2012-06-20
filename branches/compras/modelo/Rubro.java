package modelo;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class Rubro {

    private int id;
    private String nombre;
    private String nombreClase;

    public Rubro() {
    }

    public Rubro(int id, String nombre, String nombreClase) {
        this.id = id;
        this.nombre = nombre;
        this.nombreClase = nombreClase;
    }

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

    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreC) {
        this.nombreClase = nombreC;
    }

}
