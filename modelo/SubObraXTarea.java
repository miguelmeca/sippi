/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.Date;

/**
 *
 * @author Emmanuel
 */
public class SubObraXTarea implements ISubtotal{
    private int id;
    private TipoTarea tipoTarea;
    private String observaciones;
    private int cantOperarios;
    private double cantHoras;
    private Date fechaInicio;
    private Date fechaFin;
    private RangoEmpleado rangoEmpleado;

    public SubObraXTarea() 
    {
        id=-1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantOperarios() {
        return cantOperarios;
    }

    public void setCantOperarios(int cantOperarios) {
        this.cantOperarios = cantOperarios;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public double getCantHoras() {
        return cantHoras;
    }

    public void setCantHoras(double cantHoras) {
        this.cantHoras = cantHoras;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observacion) {
        this.observaciones = observacion;
    }

    public RangoEmpleado getRangoEmpleado() {
        return rangoEmpleado;
    }

    public void setRangoEmpleado(RangoEmpleado rangoEmpleado) {
        this.rangoEmpleado = rangoEmpleado;
    }

    public TipoTarea getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(TipoTarea tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    @Override
    public double calcularSubtotal() {
        return cantHoras*cantOperarios; // FAKE
    }

}
