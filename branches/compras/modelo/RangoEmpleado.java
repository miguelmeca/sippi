/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author Emmanuel
 */
public class RangoEmpleado {
    private int id;
    private String nombre;
    private  double costoXHora;

    public RangoEmpleado() {
    }

    public double getCostoXHora() {
        return costoXHora;
    }

    public void setCostoXHora(double costoXHora) {
        this.costoXHora = costoXHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
