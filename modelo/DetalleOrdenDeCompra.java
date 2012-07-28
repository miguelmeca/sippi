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
    private double precioUnitario;
    private IDetallable item;
    
    private DetalleRecepcionOrdenDeCompra recepcion;

    public DetalleOrdenDeCompra() {
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

    public IDetallable getItem() {
        return item;
    }

    public void setItem(IDetallable item) {
        this.item = item;
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

}
