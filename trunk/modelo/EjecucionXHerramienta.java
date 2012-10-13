package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iuga
 */
public class EjecucionXHerramienta {
    
    private int id;
    private PlanificacionXHerramienta herramientaPlanificada;
    
    private List<EjecucionXHerramientaXDia> usoHerramientasXdia;

    public EjecucionXHerramienta() {
        usoHerramientasXdia = new ArrayList<EjecucionXHerramientaXDia>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
}
