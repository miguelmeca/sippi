/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Fran
 */
public class DetalleTareaPlanificacion {
    private DetalleSubObraXTareaModif originalCotizado;
    private DetalleTareaPlanificacion padre;
    private ArrayList<Empleado> empleados;
    private int id;
    private int cantidadPersonas;
    private double cantHorasNormales;
    private double cantHorasAl50;
    private double cantHorasAl100;
    private RangoEmpleado rangoEmpleado;
    private double costoXHoraNormal;
    private TipoEspecialidad tipoEspecialidad;
    
    
    /**
     * Este constructor es para uso exclusivo de detalles q pertenecen a tareas
     *  que tienen referencia a una tarea de cotizacion
     * 
     * @param padre
     * @param empleado
     * @param original 
     */
    public DetalleTareaPlanificacion(DetalleSubObraXTareaModif originalCotizado)
    {
        this.originalCotizado=originalCotizado;
        this.cantidadPersonas=originalCotizado.getCantidadPersonas();
        this.cantHorasNormales=originalCotizado.getCantHorasNormales();
        this.cantHorasAl50=originalCotizado.getCantHorasAl50();
        this.cantHorasAl100=originalCotizado.getCantHorasAl100();
        this.rangoEmpleado=originalCotizado.getRangoEmpleado();
        this.costoXHoraNormal=originalCotizado.getCostoXHoraNormal();
        this.tipoEspecialidad=originalCotizado.getTipoEspecialidad();
        empleados=new ArrayList<Empleado>();
        
    }
    
     public DetalleTareaPlanificacion(DetalleTareaPlanificacion padre)
    {
        this.padre=padre;    
        empleados=new ArrayList<Empleado>();
    }

    /**
     * @return the original
     */
    public DetalleSubObraXTarea getOriginalCotizado() {
        return originalCotizado;
    }

    /**
     * @param originalCotizado the original to set
     */
    public void setOriginalCotizado(DetalleSubObraXTareaModif originalCotizado) {
        this.originalCotizado = originalCotizado;
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
        this.padre = padre;
    }

    /**
     * @return the empleado
     */
    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    /**
     * ESTE METODO NO DEBERIA USARSE. QUEDA PARA HIBERNATE
     */
    public void setEmpleados(ArrayList<Empleado> empleados) {
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
     * @return the rangoEmpleado
     */
    public RangoEmpleado getRangoEmpleado() {
        return rangoEmpleado;
    }

    /**
     * @param rangoEmpleado the rangoEmpleado to set
     */
    public void setRangoEmpleado(RangoEmpleado rangoEmpleado) {
        this.rangoEmpleado = rangoEmpleado;
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
    public TipoEspecialidad getTipoEspecialidad() {
        return tipoEspecialidad;
    }

    /**
     * @param tipoEspecialidad the tipoEspecialidad to set
     */
    public void setTipoEspecialidad(TipoEspecialidad tipoEspecialidad) {
        this.tipoEspecialidad = tipoEspecialidad;
    }
}
