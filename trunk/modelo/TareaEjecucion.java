package modelo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.DetalleTareaEjecucion;
import util.FechaUtil;
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
    public static final Color ESTADO_COLOR_CANCELADA   = new Color(0xFF6666);
    public static final Color ESTADO_COLOR_ENESPERA    = new Color(0x6666FF);
    public static final Color ESTADO_COLOR_IMPEDIMENTO = new Color(0x303030);

    public static final Color ESTADO_COLORFONDO_NUEVA       = new Color(0xFFFFFF);
    public static final Color ESTADO_COLORFONDO_ENPROGRESO  = new Color(0xFFD480);
    public static final Color ESTADO_COLORFONDO_COMPLETA    = new Color(0x99FF66);
    public static final Color ESTADO_COLORFONDO_CANCELADA   = new Color(0xFFB3B3);
    public static final Color ESTADO_COLORFONDO_ENESPERA    = new Color(0xB3B3FF);
    public static final Color ESTADO_COLORFONDO_IMPEDIMENTO = new Color(0x7A7A7A);    
    
    protected String estado;
    private TareaPlanificacion tareaPlanificada;

    public TareaEjecucion() {
        super();
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
        if(nombre==null){
            return ESTADO_ID_NUEVA;
        }
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

    @Override
    public double calcularSubtotalConSubtareas() {
        double subtotal = 0;
        
        // SubTareas
        for (TareaPlanificacion subtarea: subtareas) 
        {
            if(subtarea!=null){
                subtotal+=subtarea.calcularSubtotalConSubtareas();
            }
        }
        
        // Esta Tarea
        // Herramientas
        for (int i = 0; i < super.herramientas.size(); i++) {
            PlanificacionXHerramienta herr = super.herramientas.get(i);
            if(herr!=null){
                subtotal += herr.calcularSubtotal();
            }
        }
        
        // Materiales
        for (int i = 0; i < super.materiales.size(); i++) {
            PlanificacionXMaterial mate = super.materiales.get(i);
            if(mate!=null){
                subtotal += mate.calcularSubtotal();
            }
        }
        
        // Alquiler compras
        for (int i = 0; i < super.alquilerCompras.size(); i++) {
            PlanificacionXAlquilerCompra alqcomp = super.alquilerCompras.get(i);
            if(alqcomp!=null){
                subtotal += alqcomp.calcularSubtotal();
            }
        }
        
        // RRHH
        for (int i = 0; i < super.detalles.size(); i++) {
            DetalleTareaPlanificacion rrhh = super.detalles.get(i);
            if(rrhh!=null){
                subtotal += rrhh.calcularSubtotal();
            }
        }
        
        return subtotal;
    }
    
    ////////////////////////////////////////////////
    @Override
    public boolean setearFechaInicio(Date fechaInicio) throws Exception { 
        
        if(FechaUtil.fechaMayorQue(fechaInicio, this.getFechaFin())) {
            throw  new Exception("Error: Fecha inicio seteada es menor que la fecha de inicio");
        }
        if(FechaUtil.getFecha(fechaInicio).equals(
            FechaUtil.getFecha(this.getFechaInicio()))){
            return true;
        }
        
        
        // Si la fecha nueva, es mayor, tengo que crear
        if(FechaUtil.fechaMayorQue(this.getFechaInicio(), fechaInicio)){
            // Creo detalles y seteo la fecha de Inicio
            int nuevosDetallesCant = FechaUtil.diasDiferencia( fechaInicio, this.getFechaInicio());
            for (int i = 1; i <= nuevosDetallesCant; i++) {
                Date fechaDelNuevo = FechaUtil.fechaMenos(this.getFechaInicio(),i);
                crearHerramientaEjecucionXDiaParaFecha(fechaDelNuevo, false);
                crearDetallesTareaEjecucionXDiaParaFecha(fechaDelNuevo, false);
                
            }
            this.fechaInicio = fechaInicio;
            return true;
            
           
        }
        // Es menor, capaz que pueda borrar
        else {
            int cantABorrar = FechaUtil.diasDiferencia(this.getFechaInicio(), fechaInicio);
            
            //Veco q todos los detalles tengan esa fecha vacia en RRHH
            try{
                for (int i = 0; i < cantABorrar; i++) {
                    Date fechaDelDifunto = FechaUtil.fechaMas(this.getFechaInicio(),i);                
                    if(!validarDiaVacio(fechaDelDifunto, false)  ){                        
                        return false;
                    }                
                }
                for (int i = 0; i < cantABorrar; i++) {
                    Date fechaDelDifunto = FechaUtil.fechaMas(this.getFechaInicio(),i);                
                    borrarDia(fechaDelDifunto, false);               
                }
                this.fechaInicio = fechaInicio;
                return true;            

            
            }
            catch(Exception ex)
            {
               Logger.getLogger(DetalleTareaEjecucion.class.getName()).log(Level.SEVERE, null, ex);
               throw  new Exception("Error de inconsistencia en fechas mientras se validaba fecha para eliminacion en seteo fecha inicio");
            }           
        }
    }
    //////////////////////////////

    @Override
    public boolean setearFechaFin(Date fechaFin) throws Exception { 
        
        if(FechaUtil.fechaMayorQue(this.getFechaInicio(), fechaFin)) {
            throw  new Exception("Error: Fecha fin seteada es menor que la fecha de inicio");
        }
        if(FechaUtil.getFecha(fechaFin).equals(
            FechaUtil.getFecha(this.getFechaFin()))){
            return true;
        }
        
        
        // Si la fecha nueva, es mayor, tengo que crear
        if(FechaUtil.fechaMayorQue(fechaFin, this.getFechaFin())){
            // Creo detalles y seteo la fecha de Fin
            int nuevosDetallesCant = FechaUtil.diasDiferencia(this.getFechaFin(), fechaFin);
            for (int i = 1; i <= nuevosDetallesCant; i++) {
                Date fechaDelNuevo = FechaUtil.fechaMas(this.getFechaFin(),i);
                crearHerramientaEjecucionXDiaParaFecha(fechaDelNuevo, true);
                crearDetallesTareaEjecucionXDiaParaFecha(fechaDelNuevo, true);
                
            }
            this.fechaFin = fechaFin;
            return true;
            
           
        }
        // Es menor, capaz que pueda borrar
        else {
            int cantABorrar = FechaUtil.diasDiferencia(fechaFin, this.getFechaFin());
            
            //Veco q todos los detalles tengan esa fecha vacia en RRHH
            try{
                for (int i = 0; i < cantABorrar; i++) {
                    Date fechaDelDifunto = FechaUtil.fechaMenos(this.getFechaFin(),i);                
                    if(!validarDiaVacio(fechaDelDifunto, true)  ){                        
                        return false;
                    }                
                }
                for (int i = 0; i < cantABorrar; i++) {
                    Date fechaDelDifunto = FechaUtil.fechaMenos(this.getFechaFin(),i);                
                    borrarDia(fechaDelDifunto, true);               
                }
                this.fechaFin = fechaFin;
                return true;            

            
            }
            catch(Exception ex)
            {
               Logger.getLogger(DetalleTareaEjecucion.class.getName()).log(Level.SEVERE, null, ex);
               throw  new Exception("Error de inconsistencia en fechas mientras se validaba fecha para eliminacion en seteo fecha fin");
            }           
        }
    }
    
    
    //en este caso, la varialbe alFinal es solo para optimiza el recorrido de los for
    private void borrarDia(Date fechaDelDifunto, boolean alFinal) throws Exception{
        
      try{
        for (int i = 0; i < this.detalles.size(); i++){
            ((DetalleTareaEjecucion)(this.detalles.get(i))).borrarDia(fechaDelDifunto,alFinal);
        }
        for (int i = 0; i < this.herramientas.size(); i++){
            ((EjecucionXHerramienta)this.herramientas.get(i)).borrarDia(fechaDelDifunto,alFinal);
        }          
      }
      catch(Exception ex)
      {
               Logger.getLogger(DetalleTareaEjecucion.class.getName()).log(Level.SEVERE, null, ex);
               throw  new Exception("Error de inconsistencia en fechas mientras se validaba fecha para eliminacion en seteo fecha fin");
      }
        
    }
    
    
    //en este caso, la varialbe alFinal es solo para optimiza el recorrido de los for
    private boolean validarDiaVacio(Date fechaDelNuevo, boolean alFinal) throws Exception{
        
      try{
        for (int i = 0; i < this.detalles.size(); i++){
            if(!((DetalleTareaEjecucion)(this.detalles.get(i))).esDiaVacioEnFecha(fechaDelNuevo, alFinal)){
                return false;
            }
        }
        for (int i = 0; i < this.herramientas.size(); i++){
            if(((EjecucionXHerramienta)(this.herramientas.get(i))).esDiaVacioEnFecha(fechaDelNuevo, alFinal)){
                return false;
            }
        } 
        
        return true;
      }
      catch(Exception ex)
      {
               Logger.getLogger(DetalleTareaEjecucion.class.getName()).log(Level.SEVERE, null, ex);
               throw  new Exception("Error de inconsistencia en fechas mientras se validaba fecha para eliminacion en seteo fecha fin");
      }
        
    }
    
    
     private void crearDetallesTareaEjecucionXDiaParaFecha(Date fechaDelNuevo, boolean alFinal) {
        
         for (int i = 0; i < this.detalles.size(); i++) {
                DetalleTareaEjecucion dte = (DetalleTareaEjecucion)this.detalles.get(i);

                dte.crearDetalleTareaXDia(fechaDelNuevo, alFinal);
            }
        
    }
     
     
     
     private void crearHerramientaEjecucionXDiaParaFecha(Date fechaDelNuevo, boolean alFinal) {
         
        
            for (int i = 0; i < this.herramientas.size(); i++) {
                EjecucionXHerramienta hxe = (EjecucionXHerramienta)this.herramientas.get(i);

                hxe.crearHerramientaXDia(fechaDelNuevo, alFinal);
            }
        
        
    }
    
}
