/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author Emmanuel
 */
public class TipoTarea {
    private int id;
    private String nombre;
    private int cantOperariosPredeterminada;
    private int cantHorasPredeterminada;
    private RangoEmpleado rangoEmpleadoPredeterminado;

    public TipoTarea() {
    }

    public int getCantOperariosPredeterminada() {
        return cantOperariosPredeterminada;
    }

    public void setCantOperariosPredeterminada(int cantOperariosPredeterminada) {
        this.cantOperariosPredeterminada = cantOperariosPredeterminada;
    }

    public int getCantHorasPredeterminada() {
        return cantHorasPredeterminada;
    }

    public void setCantHorasPredeterminada(int horasPredeterminadas) {
        this.cantHorasPredeterminada = horasPredeterminadas;
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

    public RangoEmpleado getRangoEmpleadoPredeterminado() {
        return rangoEmpleadoPredeterminado;
    }

    public void setRangoEmpleadoPredeterminado(RangoEmpleado rangoEmpleadoPredeterminado) {
        this.rangoEmpleadoPredeterminado = rangoEmpleadoPredeterminado;
    }
}
