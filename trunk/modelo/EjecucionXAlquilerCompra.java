package modelo;

/**
 * @author Iuga
 */
public class EjecucionXAlquilerCompra {
    
    private int id;
    private PlanificacionXAlquilerCompra alquilerCompraPlanificado;

    public EjecucionXAlquilerCompra() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlanificacionXAlquilerCompra getAlquilerCompraPlanificado() {
        return alquilerCompraPlanificado;
    }

    public void setAlquilerCompraPlanificado(PlanificacionXAlquilerCompra alquilerCompraPlanificado) {
        this.alquilerCompraPlanificado = alquilerCompraPlanificado;
    }
           
    
}
