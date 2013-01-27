package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Iuga
 */
public class EjecucionXHerramienta extends PlanificacionXHerramienta{
    
    private int id;
    private PlanificacionXHerramienta herramientaPlanificada;
    
    private List<EjecucionXHerramientaXDia> usoHerramientasXdia;

    public EjecucionXHerramienta() {
        usoHerramientasXdia = new ArrayList<EjecucionXHerramientaXDia>();
    }

    public PlanificacionXHerramienta getHerramientaPlanificada() {
        return herramientaPlanificada;
    }

    public void setHerramientaPlanificada(PlanificacionXHerramienta herramientaPlanificada) {
        this.herramientaPlanificada = herramientaPlanificada;
    }

    public List<EjecucionXHerramientaXDia> getUsoHerramientasXdia() {
        return usoHerramientasXdia;
    }

    public void setUsoHerramientasXdia(List<EjecucionXHerramientaXDia> usoHerramientasXdia) {
        this.usoHerramientasXdia = usoHerramientasXdia;
    }
    
    // NO SON HORAS ASIGNADAS, SON NO HORAS UTILIZADAS (Lean los metodos)
    public int getHorasUtilizadas() {
        int cantHoras=0;
        for (int i = 0; i < usoHerramientasXdia.size(); i++) {
            cantHoras+=usoHerramientasXdia.get(i).getHorasUtilizadas();
        }
        return cantHoras;
    }

    //@Deprecated (En serio? y como se le asignan las horas al objeto? )
    @Override
    public void setHorasAsignadas(int horasAsignadas) {
        this.horasAsignadas = horasAsignadas;
    }
    
    
    @Override
    public double calcularSubtotal(){
        return (getHorasUtilizadas()*getCostoXHora());
    }
    
    //en este caso, la varialbe alFinal es solo para optimiza el recorrido de los for
    public boolean esDiaVacioEnFecha(Date fechaDelNuevo, boolean alFinal) throws Exception{
        boolean encontroFecha=false;
        if(alFinal) {
            for (int i = usoHerramientasXdia.size()-1; i >=0 ; i--) {
            EjecucionXHerramientaXDia dia= usoHerramientasXdia.get(i);
            
            if(dia.esFecha(fechaDelNuevo)){
                if(dia.getHorasUtilizadas()!=0) {
                    return true;
                }
                encontroFecha=true;
                break;
            }
            }
        }
        else {
            for (int i = 0; i < usoHerramientasXdia.size(); i++) {
                EjecucionXHerramientaXDia dia= usoHerramientasXdia.get(i);

                if(dia.esFecha(fechaDelNuevo)){
                    if(dia.getHorasUtilizadas()!=0) {
                        return true;
                    }
                    encontroFecha=true;
                    break;
                }
            }
        }
        if(encontroFecha){
            return false;
        }
        else{
            throw new Exception("Fecha inexistente");
        }
    }
    
    //en este caso, la varialbe alFinal es solo para optimiza el recorrido de los for
    public boolean borrarDia(Date fecha, boolean alFinal) throws Exception{
        boolean encontroFecha=false;
        
        if(alFinal) {
           for (int i = usoHerramientasXdia.size()-1; i >=0; i--) {
                EjecucionXHerramientaXDia dia= usoHerramientasXdia.get(i);

                if(dia.esFecha(fecha)){
                    usoHerramientasXdia.remove(i);
                    encontroFecha=true;
                    break;
                }
            } 
        }
        else {
            for (int i = 0; i < usoHerramientasXdia.size(); i++) {
                EjecucionXHerramientaXDia dia= usoHerramientasXdia.get(i);

                if(dia.esFecha(fecha)){
                    usoHerramientasXdia.remove(i);
                    encontroFecha=true;
                    break;
                }
            }
        }
        
        if(encontroFecha){
            return true;
        }
        else{
            throw new Exception("Fecha inexistente");
        }
    }
    
    
    public void crearHerramientaXDia(Date fechaDelNuevo, boolean alFinal) {
        EjecucionXHerramientaXDia nuevo = new EjecucionXHerramientaXDia();
        nuevo.setFecha(fechaDelNuevo);
        nuevo.setHorasUtilizadas(0);

        if(alFinal) {
            usoHerramientasXdia.add(nuevo);
        } else {
            usoHerramientasXdia.add(0, nuevo);
        }
    }
    
    
}
