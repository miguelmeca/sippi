package modelo;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Iuga
 */
public class Ejecucion {
    
    private int id;
    private List<TareaEjecucion> listaTareas;
    private Date fechaInicio;
    private Date fechaFin;
    private String observaciones;

    public Ejecucion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TareaEjecucion> getListaTareas() {
        return listaTareas;
    }

    public void setListaTareas(List<TareaEjecucion> listaTareas) {
        this.listaTareas = listaTareas;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
}
