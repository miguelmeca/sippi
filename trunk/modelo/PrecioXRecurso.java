package modelo;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class PrecioXRecurso {

    private int id;
    private double precio;
    private RecursoEspecifico recurso;

    public PrecioXRecurso() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public RecursoEspecifico getRecurso() {
        return recurso;
    }

    public void setRecurso(RecursoEspecifico recurso) {
        this.recurso = recurso;
    }

    


}
