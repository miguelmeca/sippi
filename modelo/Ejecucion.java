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
public class Ejecucion extends Planificacion{
    
    // Overrided
    /*private int id;
    private List<TareaEjecucion> tareas;
    private Date fechaInicio;
    private Date fechaFin;    
    private String estado;*/
    
//    private String observaciones;
    private Planificacion planificacionOriginal;
    
    
    private List<EjecucionXAdicional> adicionales;

    public Ejecucion() {
        super();
        //this.estado = Ejecucion.ESTADO_ALTA;
        //tareas = new ArrayList<TareaEjecucion>();
        adicionales = new ArrayList<EjecucionXAdicional>();
    }
    
    public Ejecucion(Planificacion aCopiar) {
        super();
        //this.estado = Ejecucion.ESTADO_ALTA;
        //tareas = new ArrayList<TareaEjecucion>();
        adicionales = new ArrayList<EjecucionXAdicional>();
        this.fechaInicio=aCopiar.fechaInicio;
        this.fechaFin=aCopiar.fechaFin;
        this.descripcion=aCopiar.descripcion;
    }
    

   

    public TareaEjecucion getTarea(int indice) {
        return (TareaEjecucion) tareas.get(indice);
    }

    public void addTarea(TareaEjecucion tarea) {
        this.tareas.add(tarea);
    }
    
    public void addTarea(int indice, TareaEjecucion tarea ) {
        this.tareas.add(indice, tarea);
    }
//
//
//    public String getObservaciones() {
//        return observaciones;
//    }
//
//    public void setObservaciones(String observaciones) {
//        this.observaciones = observaciones;
//    }

    @Override
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

    public List<EjecucionXAdicional> getAdicionales() {
        return adicionales;
    }

    public void setAdicionales(List<EjecucionXAdicional> adicionales) {
        this.adicionales = adicionales;
    }

    /**
     * @return the planificacionOriginal
     */
    public Planificacion getPlanificacionOriginal() {
        return planificacionOriginal;
    }

    /**
     * @param planificacionOriginal the planificacionOriginal to set
     */
    public void setPlanificacionOriginal(Planificacion planificacionOriginal) {
        this.planificacionOriginal = planificacionOriginal;
    }
    
}
