package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.DetalleTareaEjecucion;
/**
 *
 * @author Iuga
 */
public class TareaEjecucion extends TareaPlanificacion{
    
   
    private TareaPlanificacion tareaPlanificada;
    
    
    // Overrided
    /*private int id;
    private List<TareaEjecucion> subtareas;    
    private List<EjecucionXHerramienta> herramientas;
    private List<EjecucionXMaterial> materiales;
    private List<EjecucionXAlquilerCompra> alquilerCompras;
    private List<DetalleTareaEjecucion> detalle;*/

    public TareaEjecucion() {
        super();
        
        
    }

    public TareaEjecucion(TareaPlanificacion aCopiar) {
        this.fechaInicio=aCopiar.fechaInicio;
        this.fechaFin=aCopiar.fechaFin;
        this.idTareaGantt=aCopiar.idTareaGantt;   
        this.subtareas = new ArrayList<TareaPlanificacion>();
        this.asignacionesEmpleados = new ArrayList<AsignacionEmpleadoPlanificacion>();
        this.herramientas = new ArrayList<PlanificacionXHerramienta>();
        this.materiales = new ArrayList<PlanificacionXMaterial>();
        this.alquilerCompras = new ArrayList<PlanificacionXAlquilerCompra>();
        this.detalles= new ArrayList<DetalleTareaPlanificacion>();   
        this.observaciones=aCopiar.observaciones;
        this.nombre=aCopiar.nombre;
        this.tipoTarea=aCopiar.tipoTarea;
    }
  
    
    public TareaPlanificacion getTareaPlanificada() {
        return tareaPlanificada;
    }

    public void setTareaPlanificada(TareaPlanificacion tareaPlanificada) {
        this.tareaPlanificada = tareaPlanificada;
    }


    public TareaEjecucion getSubtarea(int indice) {
        return (TareaEjecucion)(subtareas.get(indice));
    }

    public void addSubtarea(TareaEjecucion subtarea) {
        this.subtareas.add(subtarea);
    }
    
    public void addSubtarea(int indice, TareaEjecucion subtarea) {
        this.subtareas.add(indice, subtarea);
    }

    @Override
    public String toString() {
        if(tareaPlanificada.getNombre()!=null){
            return tareaPlanificada.getNombre();
        }
        return "";
    }

    public EjecucionXMaterial getMaterial(int indice) {
        return (EjecucionXMaterial)(materiales.get(indice));
    }

    public void addMaterial(EjecucionXMaterial material) {
        this.materiales.add(material);
    }
    
    public void addMaterial(int indice, EjecucionXMaterial material) {
        this.materiales.add(indice,material);
    }

    public EjecucionXAlquilerCompra getAlquilerCompra(int indice) {
        return (EjecucionXAlquilerCompra)(alquilerCompras.get(indice));
    }

    public void addAlquilerCompra(EjecucionXAlquilerCompra alquilerCompra) {
        this.alquilerCompras.add(alquilerCompra);
    }
    
    public void addAlquilerCompra(int indice, EjecucionXAlquilerCompra alquilerCompra) {
        this.alquilerCompras.add(indice, alquilerCompra);
    }

    public DetalleTareaEjecucion getDetalleTarea(int indice) {
        return (DetalleTareaEjecucion)(detalles.get(indice));
    }

    public void addDetalleTarea(DetalleTareaEjecucion detalleTarea) {
        this.detalles.add(detalleTarea);
    }
    
    public void addDetalleTarea(int indice, DetalleTareaEjecucion detalleTarea) {
        this.detalles.add(indice, detalleTarea);
    }

    @Deprecated
    @Override
    public SubObraXTareaModif getTareaCotizada() {
        return null;
    }
    
    @Deprecated
    @Override
    public void setTareaCotizada(SubObraXTareaModif tareaCotizada) {
        
    }

    /**
     * Este métdo retorna TRUE si la tarea ya Terminó, de lo contrario FALSE
     * TODO: Implementarla !
     * @return 
     */
    public boolean estaTerminada() {
        return false;
    }
}
