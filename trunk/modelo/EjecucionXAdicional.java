package modelo;

/**
 *
 * @author Iuga
 */
public class EjecucionXAdicional {
 
    private int id;
    private SubObraXAdicionalModif adicionalPlanificado;

    public EjecucionXAdicional() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SubObraXAdicionalModif getAdicionalPlanificado() {
        return adicionalPlanificado;
    }

    public void setAdicionalPlanificado(SubObraXAdicionalModif adicionalPlanificado) {
        this.adicionalPlanificado = adicionalPlanificado;
    }
    
}
