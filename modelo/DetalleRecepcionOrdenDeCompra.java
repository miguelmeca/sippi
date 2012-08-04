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
    private Date fechaDeRecepcion;
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaDeRecepcion() {
        return fechaDeRecepcion;
    }

    public void setFechaDeRecepcion(Date fechaDeRecepcion) {
        this.fechaDeRecepcion = fechaDeRecepcion;
    }

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


}
