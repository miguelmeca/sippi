/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class SubObraXTarea implements ISubtotal{
    private int id;
    private String nombre;
    private TipoTarea tipoTarea;
    private String observaciones;
    private List<DetalleSubObraXTarea> detalles;

    public SubObraXTarea() 
    {
        id=-1;
        detalles=new ArrayList<DetalleSubObraXTarea>();
    }
    public SubObraXTarea(SubObraXTarea aCopiar) 
    {
        id=-1;
        this.nombre=aCopiar.getNombre();;
        this.observaciones=aCopiar.getObservaciones();
        this.tipoTarea=aCopiar.getTipoTarea();
        List<DetalleSubObraXTarea> detallesAux=aCopiar.getDetalles();
        detalles=new ArrayList<DetalleSubObraXTarea>();
        for (DetalleSubObraXTarea detalle:detallesAux) 
        {
           DetalleSubObraXTarea nuevoDetalle=new DetalleSubObraXTarea(detalle);
           detalles.add(nuevoDetalle);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DetalleSubObraXTarea> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleSubObraXTarea> detalles) {
        this.detalles = detalles;
    }
     public void agreagarDetalle(DetalleSubObraXTarea detalle) {
        this.detalles.add(detalle);
    }
    
    public DetalleSubObraXTarea getDetalleParticular(int i) {
        return detalles.get(i);
    }    

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observacion) {
        this.observaciones = observacion;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public TipoTarea getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(TipoTarea tipoTarea) {
        this.tipoTarea = tipoTarea;
    }
    

    @Override
    public double calcularSubtotal() 
    {        
        double subT=0.0;
        for (int i = 0; i < detalles.size(); i++) 
        {
            subT+=detalles.get(i).calcularSubtotal();
            
        }               
       return subT; 
    }
    
    
    
    //Este metodo no tiene sentido en el mundo real:
    //Por ejemplo detalle A tiene 2 personas, detalle B tiene 1
    //El total de personas de la tarea podria ser 2 o podria ser 3
    @Deprecated
    public int obtenerTotalDePersonas()
    {
        int totalPersonas=0;
        for (DetalleSubObraXTarea detalle: detalles) 
        {
            totalPersonas+=detalle.getCantidadPersonas();
        }
        return totalPersonas;
    }
    
    public double obtenerTotalDeHorasNormales()
    {
        double totalHorasNormales=0;
        for (DetalleSubObraXTarea detalle: detalles) 
        {
            totalHorasNormales+=(detalle.getCantHorasNormales()*detalle.getCantidadPersonas());
        }
        return totalHorasNormales;
    }
    public double obtenerTotalDeHorasAl50()
    {
        double totalHoras50=0;
        for (DetalleSubObraXTarea detalle: detalles) 
        {
            totalHoras50+=(detalle.getCantHorasAl50()*detalle.getCantidadPersonas());
        }
        return totalHoras50;
    }
    public double obtenerTotalDeHorasAl100()
    {
        double totalHoras100=0;
        for (DetalleSubObraXTarea detalle: detalles) 
        {
            totalHoras100+=(detalle.getCantHorasAl100()*detalle.getCantidadPersonas());
        }
        return totalHoras100;
    }
    

}
