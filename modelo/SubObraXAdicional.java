/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author Emmanuel
 */
public class SubObraXAdicional implements ISubtotal{
    private int id;
    private TipoAdicional tipoAdicional;
    private String descripcion;
    private int cantidad;
    private int cantDias;
    private double precioUnitario;

    public SubObraXAdicional() {
    }

    @Deprecated
    public int getCantDias() {
        return 1;
    }

    @Deprecated
    public void setCantDias(int cantDias) {
        this.cantDias = 1;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantOperarios) {
        this.cantidad = cantOperarios;
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

    public TipoAdicional getTipoAdicional() {
        return tipoAdicional;
    }

    public void setTipoAdicional(TipoAdicional tipoAdicional) {
        this.tipoAdicional = tipoAdicional;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public double calcularSubtotal() 
    {
        return cantidad*precioUnitario;
    }
    
    public String getTipoYDescripcion() {
        return tipoAdicional.getNombre()+"-"+descripcion;
    }
}
