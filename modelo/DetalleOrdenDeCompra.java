package modelo;

import java.util.List;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class DetalleOrdenDeCompra {

    private int id = 0;
    private double cantidad;
    private double precioUnitario;
    private String descripcion;
    private ItemComprable item;

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

    public ItemComprable getItem() {
        return item;
    }

    public void setItem(ItemComprable item) {
        this.item = item;
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

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
}
