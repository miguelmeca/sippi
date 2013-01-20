package modelo;

import java.util.Date;

/**
 *
 * @author Iuga
 */
public class EjecucionXHerramientaXDia {
    
    private int id;
    private Date fecha;
    private int horasUtilizadas;

    public EjecucionXHerramientaXDia() {
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

    public int getHorasUtilizadas() {
        return horasUtilizadas;
    }

    public void setHorasUtilizadas(int horasUtilizadas) {
        this.horasUtilizadas = horasUtilizadas;
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
