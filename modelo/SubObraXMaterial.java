/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author Emmanuel
 */
public class SubObraXMaterial implements ISubtotal{
    private int id;
    private int cantidad;
    private String descripcion;
    //private PrecioSegunCantidad precio;
    private RecursoXProveedor material;

    public SubObraXMaterial() {
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RecursoXProveedor getMaterial() {
        return material;
    }

    public void setMaterial(RecursoXProveedor material) {
        this.material = material;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

//    public PrecioSegunCantidad getPrecio() {
//        return precio;
//    }
//
//    public void setPrecio(PrecioSegunCantidad precio) {
//        this.precio = precio;
//    }

    public double calcularSubtotal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
