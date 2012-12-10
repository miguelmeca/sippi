package modelo;

import java.util.ArrayList;
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
    
}
