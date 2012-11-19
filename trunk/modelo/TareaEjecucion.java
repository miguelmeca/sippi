package modelo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import modelo.DetalleTareaEjecucion;
/**
 *
 * @author Iuga
 */
public class TareaEjecucion extends TareaPlanificacion{
    
    public static final int ESTADO_ID_NUEVA       = 0;
    public static final int ESTADO_ID_ENPROGRESO  = 1;
    public static final int ESTADO_ID_COMPLETA    = 2;
    public static final int ESTADO_ID_CANCELADA   = 3;
    public static final int ESTADO_ID_ENESPERA    = 4;
    public static final int ESTADO_ID_IMPEDIMENTO = 5;
    
    public static final String ESTADO_NUEVA       = "Nueva";
    public static final String ESTADO_ENPROGRESO  = "En Progreso";
    public static final String ESTADO_COMPLETA    = "Completa";
    public static final String ESTADO_CANCELADA   = "Cancelada";
    public static final String ESTADO_ENESPERA    = "En Espera";
    public static final String ESTADO_IMPEDIMENTO = "Con Impedimento";
    
    public static final Color ESTADO_COLOR_NUEVA       = new Color(0x000000);
    public static final Color ESTADO_COLOR_ENPROGRESO  = new Color(0xFFB31A);
    public static final Color ESTADO_COLOR_COMPLETA    = new Color(0x009900);
    public static final Color ESTADO_COLOR_CANCELADA   = new Color(0xFF0000);
    public static final Color ESTADO_COLOR_ENESPERA    = new Color(0x6666FF);
    public static final Color ESTADO_COLOR_IMPEDIMENTO = new Color(0xFF6666);

    public static final Color ESTADO_COLORFONDO_NUEVA       = new Color(0xCCCCCC);
    public static final Color ESTADO_COLORFONDO_ENPROGRESO  = new Color(0xFFD480);
    public static final Color ESTADO_COLORFONDO_COMPLETA    = new Color(0x99FF66);
    public static final Color ESTADO_COLORFONDO_CANCELADA   = new Color(0xFF0000);
    public static final Color ESTADO_COLORFONDO_ENESPERA    = new Color(0xB3B3FF);
    public static final Color ESTADO_COLORFONDO_IMPEDIMENTO = new Color(0xFFB3B3);    
    
    private String estado;
    private TareaPlanificacion tareaPlanificada;

    public TareaEjecucion() {
        super();
        this.estado = TareaEjecucion.ESTADO_NUEVA;
    }

    public TareaEjecucion(TareaPlanificacion aCopiar) {
        this.estado = TareaEjecucion.ESTADO_NUEVA;
        this.fechaInicio=aCopiar.fechaInicio;
        this.fechaFin=aCopiar.fechaFin;
        this.idTareaGantt=aCopiar.idTareaGantt;   
        this.subtareas = new ArrayList<TareaPlanificacion>();
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
        if(this.estado==null) {return false;}
        if(this.estado.equals(ESTADO_COMPLETA)){ return true; }
        if(this.estado.equals(ESTADO_CANCELADA)){ return true; }
        return false;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /**
     * Segun el nombre de la tarea retorna su ID 
     * (Asco Asco, pero es rápido y efectivo )
     * @param nombre
     * @return 
     */
    public static int getIdEstadoSegunNombre(String nombre){
        if(nombre.equals(ESTADO_CANCELADA)) { return ESTADO_ID_CANCELADA; }
        if(nombre.equals(ESTADO_COMPLETA)) { return ESTADO_ID_COMPLETA;}
        if(nombre.equals(ESTADO_ENESPERA)) { return ESTADO_ID_ENESPERA;}
        if(nombre.equals(ESTADO_ENPROGRESO)) { return ESTADO_ID_ENPROGRESO;}
        if(nombre.equals(ESTADO_IMPEDIMENTO)) { return ESTADO_ID_IMPEDIMENTO;}
        if(nombre.equals(ESTADO_NUEVA)) { return ESTADO_ID_NUEVA ; }
        return ESTADO_ID_NUEVA;
    }
    
    /**
     * Segun el nombre de la tarea retorna su ID 
     * (Asco Asco, pero es rápido y efectivo )
     * @param nombre
     * @return 
     */
    public static String getNombreEstadoSegunID(int id){
        switch(id){
            case ESTADO_ID_CANCELADA: return ESTADO_CANCELADA;
            case ESTADO_ID_COMPLETA: return ESTADO_COMPLETA;
            case ESTADO_ID_ENESPERA: return ESTADO_ENESPERA;
            case ESTADO_ID_ENPROGRESO: return ESTADO_ENPROGRESO;
            case ESTADO_ID_IMPEDIMENTO: return ESTADO_IMPEDIMENTO;
            case ESTADO_ID_NUEVA: return ESTADO_NUEVA;  
            default: return ESTADO_NUEVA;
        }
    }    
    
}
