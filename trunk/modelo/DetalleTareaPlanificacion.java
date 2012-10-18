/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Fran
 */
public class DetalleTareaPlanificacion {
    private DetalleSubObraXTareaModif cotizado;
    protected DetalleTareaPlanificacion padre;
    protected List<Empleado> empleados;
    protected int id;
    protected int cantidadPersonas;
    protected double cantHorasNormales;
    protected double cantHorasAl50;
    protected double cantHorasAl100;
    protected double costoXHoraNormal;
    protected Especialidad especialidad;
    protected int cantidadHijos;
    
    private transient DetalleTareaPlanificacion detalleCopia;
    
    
    /**
     * Este constructor es para uso exclusivo de detalles q pertenecen a tareas
     *  que tienen referencia a una tarea de cotizacion
     * (TAREAS CREADAS DIRECTAMENTE ARRASTRANDO UNA TAREA DE COTIZACION)
     * 
     *
     * @param original 
     */
    public DetalleTareaPlanificacion(DetalleSubObraXTareaModif cotizado)
    {
        this.cotizado=cotizado;
        this.cantidadPersonas=cotizado.getCantidadPersonas();
        this.cantHorasNormales=cotizado.getCantHorasNormales();
        this.cantHorasAl50=cotizado.getCantHorasAl50();
        this.cantHorasAl100=cotizado.getCantHorasAl100();
        this.costoXHoraNormal=cotizado.getCostoXHoraNormal();
        this.especialidad=cotizado.getEspecialidad();
        empleados=new ArrayList<Empleado>();
        cantidadHijos=0;
        
    }
    
    public int getCantidadHijos()
    {
        return cantidadHijos;
    }
    
    @Deprecated
    public void setCantidadHijos(int cant)
    {
        this.cantidadHijos=cant;
    }
    
     /*public DetalleTareaPlanificacion(DetalleTareaPlanificacion padre)
    {
        this.padre=padre;    
        empleados=new ArrayList<Empleado>();
    }*/
    
    public DetalleTareaPlanificacion(DetalleTareaPlanificacion aCopiar)
    {        
        aCopiar.setDetalleCopia(this);
        this.cotizado=aCopiar.cotizado;
        if(aCopiar.padre!=null)
        {this.setearPadre(aCopiar.padre.getDetalleCopia());}
        this.cantidadPersonas=aCopiar.getCantidadPersonas();
        this.cantHorasNormales=aCopiar.getCantHorasNormales();
        this.cantHorasAl50=aCopiar.getCantHorasAl50();
        this.cantHorasAl100=aCopiar.getCantHorasAl100();
        this.costoXHoraNormal=aCopiar.getCostoXHoraNormal();
        this.especialidad=aCopiar.getEspecialidad();
        empleados=new ArrayList<Empleado>();
        for (int i = 0; i < aCopiar.getEmpleados().size(); i++) {
            empleados.add(aCopiar.getEmpleados().get(i));
        }
        
        cantidadHijos=0;
        
    }
     
     public DetalleTareaPlanificacion()
    {
        this.cotizado=null;
        this.padre=null;
        this.cantidadPersonas=0;
        this.cantHorasNormales=0.0;
        this.cantHorasAl50=0.0;
        this.cantHorasAl100=0.0;
        this.costoXHoraNormal=0.0;
        this.especialidad=null;
        empleados=new ArrayList<Empleado>();
        cantidadHijos=0;
    }

    /**
     * @return the original
     */
    public DetalleSubObraXTareaModif getCotizado() {
        return cotizado;
    }
    
    public DetalleSubObraXTareaModif buscarCotizado() 
    {
        DetalleSubObraXTareaModif detalleCotizado;
        if(this.cotizado!=null)
        {
            return this.cotizado;
        }
        else
        {
            if(this.padre!=null)
            {return this.padre.buscarCotizado();}
            else
            {return null;}
           
        }
        
    }

    /**
     * @param originalCotizado the original to set
     */
    public void setCotizado(DetalleSubObraXTareaModif cotizado) {
        this.cotizado = cotizado;
    }

    /**
     * @return the padre
     */
    public DetalleTareaPlanificacion getPadre() {
        return padre;
    }

    /**
     * @param padre the padre to set
     */
    public void setearPadre(DetalleTareaPlanificacion padre) {
        if(this.padre!=null)
        {
            this.padre.cantidadHijos--;
            /*if(this.padre.cantidadHijos<0)
            {
                throw new Exception("Cantidad de detalles hijos en detalle padre es negativa");
            }*/
                
        }
        
        this.padre=padre;
        if(this.padre!=null)
        {this.padre.cantidadHijos++;}
    }
    
    @Deprecated
    public void setPadre(DetalleTareaPlanificacion padre) {
        this.padre=padre;
    }
        

    /**
     * @return the empleado
     */
    public List<Empleado> getEmpleados() {
        return empleados;
    }

    /**
     * @param empleados the empleados to set
     * ESTE METODO NO DEBERIA USARSE. QUEDA PARA HIBERNATE
     */
    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
    
    /**
     * @param empleado el empleado a agregar
     */
    public void addEmpleados(Empleado empleado) throws RuntimeException
    {
        if(empleados.size()==cantidadPersonas)
        {
            throw new RuntimeException("ERROR: No se pueden asignar mas empleados que el numero de cantidad de personas");
        }
        this.empleados.add(empleado);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the cantidadPersonas
     */
    public int getCantidadPersonas() {
        return cantidadPersonas;
    }
    
    /**
     * @return cantidad de empleados asignados
     */
    public int getCantidadPersonasAsignadas() {
        return empleados.size();
    }

    /**
     * @param cantidadPersonas the cantidadPersonas to set
     */
    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    /**
     * @return the cantHorasNormales
     */
    public double getCantHorasNormales() {
        return cantHorasNormales;
    }

    /**
     * @param cantHorasNormales the cantHorasNormales to set
     */
    public void setCantHorasNormales(double cantHorasNormales) {
        this.cantHorasNormales = cantHorasNormales;
    }

    /**
     * @return the cantHorasAl50
     */
    public double getCantHorasAl50() {
        return cantHorasAl50;
    }

    /**
     * @param cantHorasAl50 the cantHorasAl50 to set
     */
    public void setCantHorasAl50(double cantHorasAl50) {
        this.cantHorasAl50 = cantHorasAl50;
    }

    /**
     * @return the cantHorasAl100
     */
    public double getCantHorasAl100() {
        return cantHorasAl100;
    }

    /**
     * @param cantHorasAl100 the cantHorasAl100 to set
     */
    public void setCantHorasAl100(double cantHorasAl100) {
        this.cantHorasAl100 = cantHorasAl100;
    }

    /**
     * @return the costoXHoraNormal
     */
    public double getCostoXHoraNormal() {
        return costoXHoraNormal;
    }

    /**
     * @param costoXHoraNormal the costoXHoraNormal to set
     */
    public void setCostoXHoraNormal(double costoXHoraNormal) {
        this.costoXHoraNormal = costoXHoraNormal;
    }

    /**
     * @return the tipoEspecialidad
     */
    public Especialidad getEspecialidad() {
        return especialidad;
    }

    /**
     * @param tipoEspecialidad the tipoEspecialidad to set
     */
    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
    
     public double calcularSubtotal() 
    {        
       double subT=cantidadPersonas*((costoXHoraNormal*cantHorasNormales)+(1.5*costoXHoraNormal*cantHorasAl50)+(2*costoXHoraNormal*cantHorasAl100));          
       return subT; 
    }
     
    public double calcularTotalHorasPorPersona() 
    {        
       double subT=(cantHorasNormales+cantHorasAl50+cantHorasAl100);          
       return subT; 
    }

    /**
     * @return the detalleCopia
     */
    public DetalleTareaPlanificacion getDetalleCopia() {
        return detalleCopia;
    }

    /**
     * @param detalleCopia the detalleCopia to set
     */
    public void setDetalleCopia(DetalleTareaPlanificacion detalleCopia) {
        this.detalleCopia = detalleCopia;
    }
    
    public void borrar(List<TareaPlanificacion> caminoTareas) throws Exception
    {
        if(!caminoTareas.get(caminoTareas.size()-1).getDetalles().contains(this))
        {
            throw new Exception("ERROR: DetalleTareaPlanificacion - borrar() Tarea pasada por parametro no contiene al detalle actual");        
        }
        
        if(this.cantidadHijos!=0)
        {
            throw new Exception("ERROR: DetalleTareaPlanificacion - borrar() El detalle seleccionado tiene detalles hijos");        
        }
        
        if(this.padre!=null)
        {this.setearPadre(null);}
        else if(this.padre.calcularTotalHorasPorPersona()==0.0 && this.padre.getCantidadHijos()==0)
        {
            ArrayList<TareaPlanificacion> caminoTareasReducido=new ArrayList<TareaPlanificacion>();
            for (int i = 0; i < caminoTareas.size()-1; i++) //Agrega hasta la penultima tarea
            {
                caminoTareasReducido.add(caminoTareas.get(i));
            }
            padre.borrar(caminoTareasReducido);
        }
        this.setCotizado(null);
        caminoTareas.get(caminoTareas.size()-1).getDetalles().remove(this);
        
    }
    
    
    
    
    
    
    
    
    
    
}
