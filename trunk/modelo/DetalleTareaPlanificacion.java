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
        this.padre = padre;
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
    
    /*
    public void impactarDatos( int cantidadPersonas,double cantHorasNormales, double cantHorasAl50, double cantHorasAl100, double costoXHoraNormal, Especialidad especialidad, TareaPlanificacion tarea) throws RuntimeException
    {
        if(tarea.getDetalles().indexOf(this)==-1)
        {
            throw new RuntimeException("El detalle no pertenece a la tarea pasada por parametro");
        }
        if(this.padre.cantidadPersonas<cantidadPersonas)
        {
            throw new RuntimeException("La cantidad de personas no puede ser mayor a la actual");
        }
        if(especialidad==null)
        {
            throw new RuntimeException("La la especialidad no puede ser null");
        }
        
        boolean altoImpactoMasHoras=false;//=true  cambios en detalle en cotizacion modificada (agregar horas) - restar las horas al padre.
        boolean altoImpactoMenosPersonas=false;//=true  dividir detalle en tarea padre. usar horas de uno de ellos.
        boolean altoImpactoCosto=false; //=true  crear nuevo detalle en cotizacion modificada
        
        //Si todos son false, simplemente restar las horas al padre.
        
        if( !especialidad.equals(this.padre.especialidad) || this.padre.costoXHoraNormal !=costoXHoraNormal)
        {
            altoImpactoCosto=true;
        }
        
        if(this.padre.cantidadPersonas!=cantidadPersonas)
        {
            altoImpactoMenosPersonas=true;
        }       
        
        
        if(altoImpactoMenosPersonas)
        {
            //divido al padre en 3:  padre original (puede estar siendo padre de otro detalle)
            //                       padre nuevo (el q sera padre de este) - tiene detalle cotizado propio (nuevo)
            //                       tio (division del padre con el sobrante de personas)  - tiene detalle cotizado propio (nuevo)
            
            //Resto a lo cotizado las horas del padre
            padre.cotizado.setCantHorasNormales(padre.cotizado.getCantHorasNormales() - padre.getCantHorasNormales());
            padre.cotizado.setCantHorasAl50(padre.cotizado.getCantHorasAl50() - padre.getCantHorasAl50());
            padre.cotizado.setCantHorasAl100(padre.cotizado.getCantHorasAl100() - padre.getCantHorasAl100());
            
            //Creo las 2 divisiones nuevas del padre (padre nuevo y tio)
            DetalleTareaPlanificacion tio= new DetalleTareaPlanificacion(padre);
            DetalleTareaPlanificacion padreNuevo= new DetalleTareaPlanificacion(padre);
            tio.cantidadPersonas = tio.cantidadPersonas - cantidadPersonas; //tio.cantidadPersonas  -= cantidadPersonas; 
            padreNuevo.cantidadPersonas =cantidadPersonas;
            
            
            //Creo las cotizaciones de tio y padre nuevo
            DetalleSubObraXTareaModif tioCotizado= new DetalleSubObraXTareaModif(padre.cotizado);
            DetalleSubObraXTareaModif padreNuevoCotizado= new DetalleSubObraXTareaModif(padre.cotizado);
            tioCotizado.setCantidadPersonas(padre.cotizado.getCantidadPersonas() - cantidadPersonas); 
            padreNuevoCotizado.setCantidadPersonas(cantidadPersonas);
            tarea.getTareaCotizada().agreagarDetalle(tioCotizado);
            tarea.getTareaCotizada().agreagarDetalle(padreNuevoCotizado);
            
            //Resto las horas sobrantes al padre
            padre.setCantHorasNormales(padre.getCantHorasNormales() - padreNuevo.getCantHorasNormales());
            padre.setCantHorasAl50(padre.getCantHorasAl50() - padreNuevo.getCantHorasAl50());
            padre.setCantHorasAl100(padre.getCantHorasAl100() - padreNuevo.getCantHorasAl100());
            
            tio.setCotizado(tioCotizado);
            padreNuevo.setCotizado(padreNuevoCotizado);
            padre=padreNuevo;
        }
        
        if(altoImpactoCosto)
        {
            //divido al padre en 2:  padre original (puede estar siendo padre de otro detalle)
            //                       padre nuevo (el q sera padre de este) - tiene detalle cotizado propio (nuevo)
            
            //Resto a lo cotizado las horas que necesito
            padre.cotizado.setCantHorasNormales(padre.cotizado.getCantHorasNormales() - cantHorasNormales);
            padre.cotizado.setCantHorasAl50(padre.cotizado.getCantHorasAl50() - cantHorasAl50);
            padre.cotizado.setCantHorasAl100(padre.cotizado.getCantHorasAl100() - cantHorasAl100);
            
            //Creo la division nueva del padre (padre nuevo)
            DetalleTareaPlanificacion padreNuevo= new DetalleTareaPlanificacion(padre);
            padreNuevo.setCostoXHoraNormal(costoXHoraNormal);
            padreNuevo.setEspecialidad(especialidad);
            padreNuevo.setCantHorasNormales(cantHorasNormales);
            padreNuevo.setCantHorasAl50(cantHorasAl50);
            padreNuevo.setCantHorasAl100(cantHorasAl100);
            
            //Creo la cotizacion de  padre nuevo
            DetalleSubObraXTareaModif padreNuevoCotizado= new DetalleSubObraXTareaModif(padre.cotizado);
            tarea.getTareaCotizada().agreagarDetalle(padreNuevoCotizado);
            padreNuevoCotizado.setCostoXHoraNormal(costoXHoraNormal);
            padreNuevoCotizado.setEspecialidad(especialidad);
            padreNuevoCotizado.setCantHorasNormales(cantHorasNormales);
            padreNuevoCotizado.setCantHorasAl50(cantHorasAl50);
            padreNuevoCotizado.setCantHorasAl100(cantHorasAl100);
            
            padreNuevo.setCotizado(padreNuevoCotizado);
            
            //Resto las horas sobrantes al padre
            padre.setCantHorasNormales(padre.getCantHorasNormales() - padreNuevo.getCantHorasNormales());
            padre.setCantHorasAl50(padre.getCantHorasAl50() - padreNuevo.getCantHorasAl50());
            padre.setCantHorasAl100(padre.getCantHorasAl100() - padreNuevo.getCantHorasAl100());
                        
            
            padre=padreNuevo;
        }
        
        
        
        if( padre.getCantHorasNormales() <cantHorasNormales)
        {
            padre.cotizado.setCantHorasNormales(padre.cotizado.getCantHorasNormales()+(cantHorasNormales-padre.getCantHorasNormales()));
            padre.setCantHorasNormales(0.0);
        }
        else
        {
           padre.setCantHorasNormales(padre.getCantHorasNormales() - cantHorasNormales); 
        }
        
        if( padre.getCantHorasAl50() <cantHorasAl50) 
        {
            padre.cotizado.setCantHorasAl50(padre.cotizado.getCantHorasAl50()+(cantHorasAl50-padre.getCantHorasAl50()));
            padre.setCantHorasAl50(0.0);
        }
        else
        {
            padre.setCantHorasAl50(padre.getCantHorasAl50() -cantHorasAl50);
        }
        
        if( padre.getCantHorasAl100() <cantHorasAl100)
        {
            padre.cotizado.setCantHorasAl100(padre.cotizado.getCantHorasAl100()+(cantHorasAl100-padre.getCantHorasAl100()));
            padre.setCantHorasAl100(0.0);
        }
        else
        {
            padre.setCantHorasAl100(padre.getCantHorasAl100() - cantHorasAl100);
        }
        ///////////////////////////////////////////////////
       
        
        //Devuelvo el sobrante al padre:        
       if( this.cantHorasNormales >cantHorasNormales)
        {
            padre.setCantHorasNormales(padre.getCantHorasNormales()+(this.cantHorasNormales - cantHorasNormales));
            //padre.setCantHorasNormales(0.0);
        }
        
        if( this.cantHorasAl50 >cantHorasAl50) 
        {
            padre.setCantHorasAl50(padre.getCantHorasAl50()+(this.cantHorasAl50- cantHorasAl50));
            ///padre.setCantHorasAl50(0.0);
        }
        
        if( this.cantHorasAl100 >cantHorasAl100)
        {
            padre.setCantHorasAl100(padre.getCantHorasAl100()+(this.cantHorasAl100 - cantHorasAl100));
            //padre.setCantHorasAl100(0.0);
        }
        
       
    }*/
    
    
    
    
    
    
    
    
    
    
    
}
