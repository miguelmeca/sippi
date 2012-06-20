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
    private int cantOperarios;
    private int cantDias;
    private double precioUnitario;

    public SubObraXAdicional() {
    }

    public int getCantDias() {
        return cantDias;
    }

    public void setCantDias(int cantDias) {
        this.cantDias = cantDias;
    }

    public int getCantOperarios() {
        return cantOperarios;
    }

    public void setCantOperarios(int cantOperarios) {
        this.cantOperarios = cantOperarios;
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
        return cantDias*cantOperarios*precioUnitario;
    }
}
