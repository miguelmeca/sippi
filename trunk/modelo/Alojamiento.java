package modelo;
//
/**
 * Descripción:
 * @version 2.0
 * @author Fran
 **/

public class Alojamiento
{
    private int id;
    private Proveedor empresa;
    private int cantidad;
    private double precioUnitario;


    public Alojamiento()
    {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Proveedor getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Proveedor empresa) {
        this.empresa = empresa;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cant) {
        this.cantidad = cant;
    }
    public double getPrecio() {
        return this.precioUnitario;
    }

    public void setPrecio(double precio) {
        this.precioUnitario = precio;
    }

    
}
