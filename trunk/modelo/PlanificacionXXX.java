package modelo;

//

import java.util.Date;
import java.util.List;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos
//  @ File Name : PlanificacionXXX.java
//  @ Date : 14/01/2012
//  @ Author : Emmanuel
//
//

public class PlanificacionXXX {
    private int id;
    private Date fechaInicio;
    private Date fechaFin;
    private int numeroPlanificacion;
    private CotizacionModificada cotizacion;
    private List<TareaPlanificacion> tareas;
    
    private String estado;
    
    public static final String ESTADO_ALTA = "ALTA"; // POR PONER ALGO, NO SON
    public static final String ESTADO_BAJA = "BAJA"; // POR PONER ALGO, NO SON

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PlanificacionXXX() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CotizacionModificada getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(CotizacionModificada cotizacion) {
        this.cotizacion = cotizacion;
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

    public int getNumeroPlanificacion() {
        return numeroPlanificacion;
    }

    public void setNumeroPlanificacion(int numeroPlanificacion) {
        this.numeroPlanificacion = numeroPlanificacion;
    }

    public List<TareaPlanificacion> getTareas() {
        return tareas;
    }

    public void setTareas(List<TareaPlanificacion> tareas) {
        this.tareas = tareas;
    }
    
    public String getNroCotizacionPlanificada()
    {
        if(this.getCotizacion()!=null)
        {
            if(this.getCotizacion().getCotizacionOriginal()!=null)
            {
                return this.getCotizacion().getCotizacionOriginal().getNroCotizacion();
            }
            return "";
        }
        return "";             
    }
    
    public TareaPlanificacion buscarTarea(int idTarea)
    {
        TareaPlanificacion tarea=null;
        for (int i = 0; i < tareas.size(); i++) {
            
            if(tareas.get(i).getId()==idTarea)
            {
                tarea=tareas.get(i);
                break;
            }
            else
            {
                tarea=tareas.get(i).buscarSubTarea(idTarea);
                if(tarea!=null)
                {break;}
            }
        }
        return tarea;
        
    }
    
    public TareaPlanificacion buscarTareaPorIdTareaCotizada(int idTarea)
    {
        TareaPlanificacion tarea=null;
        for (int i = 0; i < tareas.size(); i++) {
            
            if(tareas.get(i).getTareaCotizada().getId()==idTarea)
            {
                tarea=tareas.get(i);
                break;
            }
            else
            {
                tarea=tareas.get(i).buscarSubTareaPorIdTareaCotizada(idTarea);
                if(tarea!=null)
                {break;}
            }
        }
        return tarea;
    }
    
    public TareaPlanificacion buscarTareaPorIdTareaGantt(int idTareaGantt)
    {
        TareaPlanificacion tarea=null;
        for (int i = 0; i < tareas.size(); i++) {
            
            if(tareas.get(i).getIdTareaGantt()==idTareaGantt)
            {
                tarea=tareas.get(i);
                break;
            }
            else
            {
                tarea=tareas.get(i).buscarTareaPorIdTareaGantt(idTareaGantt);
                if(tarea!=null)
                {break;}
            }
        }
        return tarea;
    }
    
}
    