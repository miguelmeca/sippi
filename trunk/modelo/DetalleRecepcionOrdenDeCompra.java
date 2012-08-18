/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;

/**
 *
 * @author Iuga
 */
public class DetalleRecepcionOrdenDeCompra {
    
    private int id = 0;
    private double cantidad;
    private DetalleOrdenDeCompra detalleOrdenDeCompra;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public DetalleOrdenDeCompra getDetalleOrdenDeCompra() {
        return detalleOrdenDeCompra;
    }

    public void setDetalleOrdenDeCompra(DetalleOrdenDeCompra detalleOrdenDeCompra) {
        this.detalleOrdenDeCompra = detalleOrdenDeCompra;
    }

    
    
}
