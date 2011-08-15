/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author Emmanuel
 */
public class SubObraXAlquilerCompra implements ISubtotal{
    private int id;
    private TipoAlquilerCompra tipoAlquilerCompra;
    private String descripcion;
    private int cantidad;
    private double precioUnitario;

    public SubObraXAlquilerCompra() {
    }

    public TipoAlquilerCompra getTipoAlquilerCompra() {
        return tipoAlquilerCompra;
    }

    public void setTipoAlquilerCompra(TipoAlquilerCompra tipoAlquierCompra) {
        this.tipoAlquilerCompra = tipoAlquierCompra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public double calcularSubtotal() 
    {
        return cantidad*precioUnitario;
    }

}
