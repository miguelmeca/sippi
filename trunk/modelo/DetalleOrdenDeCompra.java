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
    private float cantidad;
    private double precioUnitario;
    private String descripcion;
    private ItemComprable item;
    
    private DetalleRecepcionOrdenDeCompra recepcion;

    public DetalleOrdenDeCompra() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public DetalleRecepcionOrdenDeCompra getRecepcion() {
        return recepcion;
    }

    public void setRecepcion(DetalleRecepcionOrdenDeCompra recepcion) {
        this.recepcion = recepcion;
    }

    public ItemComprable getItem() {
        return item;
    }

    public void setItem(ItemComprable item) {
        this.item = item;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getCantidad() {
        return cantidad;
    }
    
    public double calcularSubTotal()
    {
        return cantidad * precioUnitario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
