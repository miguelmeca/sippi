package modelo;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class DetalleOrdenDeCompra {

    private int id;
    private int cantidad;
    private double precio;
    private RecursoEspecifico recurso;

    public DetalleOrdenDeCompra() {
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
