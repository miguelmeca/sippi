package modelo;

import java.util.List;

/**
 *
 * @author Iuga
 */
public class DetalleTareaEjecucion {
    
    private int id;
    private DetalleTareaPlanificacion detalleTareaPlanificado;
    
    private List<DetalleTareaEjecucionXDia> listaDetallePorDia;

    public DetalleTareaEjecucion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DetalleTareaPlanificacion getDetalleTareaPlanificado() {
        return detalleTareaPlanificado;
    }

    public void setDetalleTareaPlanificado(DetalleTareaPlanificacion detalleTareaPlanificado) {
        this.detalleTareaPlanificado = detalleTareaPlanificado;
    }

    public List<DetalleTareaEjecucionXDia> getListaDetallePorDia() {
        return listaDetallePorDia;
    }

    public void setListaDetallePorDia(List<DetalleTareaEjecucionXDia> listaDetallePorDia) {
        this.listaDetallePorDia = listaDetallePorDia;
    }    
    
}
