package modelo;

/**
 * @author Iuga
 */
public class EjecucionXAlquilerCompra extends PlanificacionXAlquilerCompra{
    
    private int id;
    private PlanificacionXAlquilerCompra alquilerCompraPlanificado;

    public EjecucionXAlquilerCompra() {
    }

    public PlanificacionXAlquilerCompra getAlquilerCompraPlanificado() {
        return alquilerCompraPlanificado;
    }

    public void setAlquilerCompraPlanificado(PlanificacionXAlquilerCompra alquilerCompraPlanificado) {
        this.alquilerCompraPlanificado = alquilerCompraPlanificado;
    }
           
    
}
