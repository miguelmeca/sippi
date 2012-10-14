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
        this.cantHorasAl100 = cantHorasAl100;
    }
   
}
