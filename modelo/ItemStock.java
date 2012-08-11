package modelo;

import java.util.Date;

/**
 * @author Iuga
 */
public class ItemStock {
    
    private int id = 0;
    private ItemComprable item;
    private double cantidad;
    private Date fechaModificacion;

    public ItemStock() {
    }
    
    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemComprable getItem() {
        return item;
    }

    public void setItem(ItemComprable item) {
        this.item = item;
    }
}
