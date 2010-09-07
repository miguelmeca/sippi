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
    private double cantidad;
    private double precio;
    private String descripcion;
    private RecursoEspecifico recurso;

    public DetalleOrdenDeCompra() {
    }
    public DetalleOrdenDeCompra(double cant, double preci, String descripcio, RecursoEspecifico rec)
    {
     cantidad=cant;
     precio=preci;
     descripcion=descripcio;
     recurso=rec;
    }
    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcio) {
        this.descripcion = descripcio;
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

    public double getPrecioParcial()
    {
        return (precio*cantidad);
    }

}
