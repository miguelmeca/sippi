package modelo;

/**
 * @author Iuga
 */
public class EjecucionXMaterial {
        
    private int id;
    private PlanificacionXMaterial materialPlanificado;

    public EjecucionXMaterial() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlanificacionXMaterial getMaterialPlanificado() {
        return materialPlanificado;
    }

    public void setMaterialPlanificado(PlanificacionXMaterial materialPlanificado) {
        this.materialPlanificado = materialPlanificado;
    }
}
