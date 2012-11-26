package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.FechaUtil;
import util.HibernateUtil;

public class Planificacion {
    protected int id;
    protected Date fechaInicio;
    protected Date fechaFin;
    private CotizacionModificada cotizacion;
    protected List<TareaPlanificacion> tareas;
    protected String descripcion;
    
    protected String estado;
    
    public static final String ESTADO_CREADA = "Creada";
    public static final String ESTADO_CANCELADA = "Canceada";
    public static final String ESTADO_FINALIZADA= "Finalizada";

    public Planificacion() {
        this.estado = Planificacion.ESTADO_CREADA;
        this.tareas = new ArrayList<TareaPlanificacion>();
        this.descripcion = new String();
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
    
    public String mostrarFechaFin(){
        if(this.fechaFin!=null){
            return FechaUtil.getFecha(fechaFin);
        }
        return "";
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }
    
    public String mostrarFechaInicio(){
        if(this.fechaInicio!=null){
            return FechaUtil.getFecha(fechaInicio);
        }
        return "";
    }    

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public List<TareaPlanificacion> getTareas() {
        return tareas;
    }

    public void setTareas(List<TareaPlanificacion> tareas) {
        this.tareas = tareas;
    }
    
    public String mostrarPedidoObra(){
        try
        {
            HibernateUtil.beginTransaction();
            PedidoObra po = (PedidoObra)HibernateUtil.getSession().createQuery("FROM PedidoObra po WHERE :cID = po.planificacion").setParameter("cID", this).uniqueResult();
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
    public TareaPlanificacion buscarTareaPorHash(int hashTarea)
    {
        TareaPlanificacion tarea=null;
        for (int i = 0; i < tareas.size(); i++) {
            
            if(tareas.get(i).hashCode()==hashTarea)
            {
                tarea=tareas.get(i);
                break;
            }
            else
            {
                tarea=tareas.get(i).buscarSubTareaPorHash(hashTarea);
                if(tarea!=null)
                {break;}
            }
        }
        return tarea;
        
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
    public TareaPlanificacion buscarTareaPorHashTareaCotizada(int hashTarea)
    {
        TareaPlanificacion tarea=null;
        for (int i = 0; i < tareas.size(); i++) {
            
            if(tareas.get(i).getTareaCotizada().hashCode()==hashTarea)
            {
                tarea=tareas.get(i);
                break;
            }
            else
            {
                tarea=tareas.get(i).buscarSubTareaPorHashTareaCotizada(hashTarea);
                if(tarea!=null)
                {break;}
            }
        }
        return tarea;
    }
    
    public boolean eliminarTareaPorHash(int hashTarea, boolean busquedaProfunda)
    {
        boolean eliminada=false;
        for (int i = 0; i < tareas.size(); i++) {
            
            if(tareas.get(i).hashCode()==hashTarea)
            {
                tareas.remove(i);
                eliminada=true;
                break;
            }
            else
            {
                if(busquedaProfunda)
                {
                    eliminada=tareas.get(i).eliminarSubTareaPorHash(hashTarea, busquedaProfunda);
                    if(eliminada)
                    {break;}
                }
            }
        }  
        return eliminada;
    }
    public boolean eliminarTarea(int idTarea, boolean busquedaProfunda)
    {
        boolean eliminada=false;
        for (int i = 0; i < tareas.size(); i++) {
            
            if(tareas.get(i).getId()==idTarea)
            {
                tareas.remove(i);
                eliminada=true;
                break;
            }
            else
            {
                if(busquedaProfunda)
                {tareas.get(i).eliminarSubTarea(idTarea, busquedaProfunda);}
            }
        }  
        return eliminada;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void addTarea(TareaPlanificacion soxt){
        this.tareas.add(soxt);
    }
    
    public TareaPlanificacion getTareaDeDetalle(DetalleTareaPlanificacion detalle)
    {
        for (int i = 0; i < this.tareas.size(); i++) {
            if(this.tareas.get(i).tieneDetalle(detalle))
            {return this.tareas.get(i);}
            else
            {
                TareaPlanificacion tareaX=this.tareas.get(i).buscarDetalleEnSubtareas(detalle);
                if(tareaX!=null)
                {return tareaX;}
            }
            
        }
        return null;
    }
    
    public double calcularMontoTotal() {
        double total=0.0;
        for (int i = 0; i < this.tareas.size(); i++) {
            total+=tareas.get(i).calcularSubtotalConSubtareas();
        }
        
        for (int i = 0; i < this.getCotizacion().getSubObras().size(); i++) {
            SubObra subO=this.getCotizacion().getSubObras().get(i);
            for (int j = 0; j < subO.getAdicionales().size(); j++) {
                total+=subO.getAdicionales().get(j).calcularSubtotal();
            }
          
        }
        return total;
    }
    
    
     
}
    
