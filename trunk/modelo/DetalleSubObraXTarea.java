/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.Date;

/**
 *
 * @author Emmanuel
 */
public class DetalleSubObraXTarea implements ISubtotal{
    private int id;
    private int cantidadPersonas;
    private double cantHorasNormales;
    private double cantHorasAl50;
    private double cantHorasAl100;
    private Especialidad especialidad;
    //private RangoEspecialidad rangoEmpleado;
    private double costoXHoraNormal;
    //private TipoEspecialidad tipoEspecialidad;

    public DetalleSubObraXTarea() 
    {
        id=-1;
    }
    
    public DetalleSubObraXTarea(DetalleSubObraXTarea aCopiar) 
    {
        id=-1;
        this.cantidadPersonas=aCopiar.cantidadPersonas;
        this.cantHorasNormales=aCopiar.cantHorasNormales;
        this.cantHorasAl50=aCopiar.cantHorasAl50;
        this.cantHorasAl100=aCopiar.cantHorasAl100;
        this.especialidad=aCopiar.especialidad;
        this.costoXHoraNormal = aCopiar.costoXHoraNormal;
        /*this.rangoEmpleado=aCopiar.rangoEmpleado;
        this.costoXHoraNormal=aCopiar.costoXHoraNormal;
        this.tipoEspecialidad=aCopiar.tipoEspecialidad;*/
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantPersonas) {
        this.cantidadPersonas = cantPersonas;
    }

    
    public double getCantHorasNormales() {
        return cantHorasNormales;
    }

    public void setCantHorasNormales(double cantHoras) {
        this.cantHorasNormales = cantHoras;
    }
    public double getCantHorasAl50() {
        return cantHorasAl50;
    }

    public void setCantHorasAl50(double cantHoras) {
        this.cantHorasAl50 = cantHoras;
    }
    public double getCantHorasAl100() {
        return cantHorasAl100;
    }

    public void setCantHorasAl100(double cantHoras) {
        this.cantHorasAl100 = cantHoras;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
    
/*
    public RangoEspecialidad getRangoEmpleado() {
        return rangoEmpleado;
    }
    
    public void setRangoEmpleado(RangoEspecialidad rangoEmpleado) {
        this.rangoEmpleado = rangoEmpleado;
    }
    
    public TipoEspecialidad getTipoEspecialidad() {
        return tipoEspecialidad;
    }

    public void setTipoEspecialidad(TipoEspecialidad tipoEspecialidad) {
        this.tipoEspecialidad = tipoEspecialidad;
    }
       */
    public double getCostoXHoraNormal() {
        return costoXHoraNormal;
    }

    
    public void setCostoXHoraNormal(double costoXHoraNormal) {
        this.costoXHoraNormal = costoXHoraNormal;
    }

    @Override
    public double calcularSubtotal() 
    {        
       double subT=cantidadPersonas*((costoXHoraNormal*cantHorasNormales)+(1.5*costoXHoraNormal*cantHorasAl50)+(2*costoXHoraNormal*cantHorasAl100));          
       return subT; 
    }

    

}
