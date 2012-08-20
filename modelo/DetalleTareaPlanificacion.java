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
    private DetalleTareaPlanificacion padre;
    private List<Empleado> empleados;
    private int id;
    private int cantidadPersonas;
    private double cantHorasNormales;
    private double cantHorasAl50;
    private double cantHorasAl100;
    private double costoXHoraNormal;
    private Especialidad especialidad;
    private int contHijos;
    
    
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
        contHijos=0;
        
    }
    
     /*public DetalleTareaPlanificacion(DetalleTareaPlanificacion padre)
    {
        this.padre=padre;    
        empleados=new ArrayList<Empleado>();
    }*/
    
    public DetalleTareaPlanificacion(DetalleTareaPlanificacion aCopiar)
    {
        this.cotizado=aCopiar.cotizado;
        this.padre= aCopiar.padre;
        this.cantidadPersonas=aCopiar.getCantidadPersonas();
        this.cantHorasNormales=aCopiar.getCantHorasNormales();
        this.cantHorasAl50=aCopiar.getCantHorasAl50();
        this.cantHorasAl100=aCopiar.getCantHorasAl100();
        this.costoXHoraNormal=aCopiar.getCostoXHoraNormal();
        this.especialidad=aCopiar.getEspecialidad();
        empleados=new ArrayList<Empleado>();
        contHijos=0;
        
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
    }

    /**
     * @return the original
     */
    public DetalleSubObraXTareaModif getCotizado() {
        return cotizado;
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
    
    
    
    
    
    
    
    
    
    
    
    
}
