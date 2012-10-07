package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.FechaUtil;
import util.HibernateUtil;

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
    private String estado;
    
    public static final String ESTADO_ALTA = "Alta";
    public static final String ESTADO_ENEJECUCION = "En Ejecución";
    public static final String ESTADO_FINALIZADA = "Finalizada";
    public static final String ESTADO_BAJA = "Baja";

    public Ejecucion() {
        this.estado = Ejecucion.ESTADO_ALTA;
        listaTareas = new ArrayList<TareaEjecucion>();
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }    
    
    public String mostrarPedidoObra(){
        try
        {
            HibernateUtil.beginTransaction();
            PedidoObra po = (PedidoObra)HibernateUtil.getSession().createQuery("FROM PedidoObra po WHERE :cID = po.ejecucion").setParameter("cID", this).uniqueResult();
            HibernateUtil.commitTransaction();
            if(po!=null){
                return po.getNombre();
            }
        }catch(Exception e)
        {
            HibernateUtil.rollbackTransaction();
            return "";
        } 
        return "";
    }    
    
    public String mostrarFechaFin(){
        if(this.fechaFin!=null){
            return FechaUtil.getFecha(fechaFin);
        }
        return "";
    }
    
    public String mostrarFechaInicio(){
        if(this.fechaInicio!=null){
            return FechaUtil.getFecha(fechaInicio);
        }
        return "";
    }        
    
}
