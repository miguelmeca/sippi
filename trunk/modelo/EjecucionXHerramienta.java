package modelo;

/**
 *
 * @author Iuga
 */
public class EjecucionXHerramienta {
    
    private int id;
    private PlanificacionXHerramienta herramientaPlanificada;

    public EjecucionXHerramienta() {
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
    
}
