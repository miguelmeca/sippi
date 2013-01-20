package modelo;

import java.util.Date;

/**
 *
 * @author Iuga
 */
public class DetalleTareaEjecucionXDia {
    
    private int id;
    private Date fecha;
    private double cantHorasNormales;
    private double cantHorasAl50;
    private double cantHorasAl100;

    public DetalleTareaEjecucionXDia() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getCantHorasNormales() {
        return cantHorasNormales;
    }

    public void setCantHorasNormales(double cantHorasNormales) {
        this.cantHorasNormales = cantHorasNormales;
    }

    public double getCantHorasAl50() {
        return cantHorasAl50;
    }

    public void setCantHorasAl50(double cantHorasAl50) {
        this.cantHorasAl50 = cantHorasAl50;
    }

    public double getCantHorasAl100() {
        return cantHorasAl100;
    }

    public void setCantHorasAl100(double cantHorasAl100) {
        this.cantHorasAl50 = cantHorasAl100;
    }
    
    public double getCantidadDeHorasTotales(){
        return this.cantHorasNormales+this.cantHorasAl50+this.cantHorasAl100;
    }
    
    public boolean esFecha(Date fechaX){
        Date thisFecha = (Date)this.fecha.clone();
        thisFecha.setHours(0);
        thisFecha.setMinutes(0);
        thisFecha.setSeconds(0);
        fechaX.setHours(0);
        fechaX.setMinutes(0);
        fechaX.setSeconds(0);
        
        if(thisFecha.compareTo(fechaX)==0){
            return true;
        }
        else{
            return false;
        }
    }
   
}
