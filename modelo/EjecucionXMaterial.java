package modelo;

/**
 * @author Iuga
 */
public class EjecucionXMaterial extends PlanificacionXMaterial{
        
    private int id;
    private PlanificacionXMaterial materialPlanificado;

    public EjecucionXMaterial() {
    }

    public PlanificacionXMaterial getMaterialPlanificado() {
        return materialPlanificado;
    }

    public void setMaterialPlanificado(PlanificacionXMaterial materialPlanificado) {
        this.materialPlanificado = materialPlanificado;
    }
}
