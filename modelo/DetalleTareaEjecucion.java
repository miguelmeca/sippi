package modelo;

import java.util.List;

/**
 *
 * @author Iuga
 */
public class DetalleTareaEjecucion extends DetalleTareaPlanificacion{
    
     private DetalleTareaPlanificacion detalleTareaPlanificado;
    
    private List<DetalleTareaEjecucionXDia> listaDetallePorDia;

    public DetalleTareaEjecucion() {
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
    
    
    
    @Override
    public double getCantHorasNormales() {
        double cantHoras=0.0;
        for (int i = 0; i < listaDetallePorDia.size(); i++) {
            cantHoras+=listaDetallePorDia.get(i).getCantHorasNormales();
        }
        return cantHoras;
    }

    @Deprecated
    @Override
    public void setCantHorasNormales(double cantHorasNormales) {
        this.cantHorasNormales = cantHorasNormales;
    }

    @Override
    public double getCantHorasAl50() {
        double cantHoras=0.0;
        for (int i = 0; i < listaDetallePorDia.size(); i++) {
            cantHoras+=listaDetallePorDia.get(i).getCantHorasAl50();
        }
        return cantHoras;
    }

    @Deprecated
    @Override
    public void setCantHorasAl50(double cantHorasAl50) {
        this.cantHorasAl50 = cantHorasAl50;
    }

    @Override
    public double getCantHorasAl100() {
        double cantHoras=0.0;
        for (int i = 0; i < listaDetallePorDia.size(); i++) {
            cantHoras+=listaDetallePorDia.get(i).getCantHorasAl100();
        }
        return cantHoras;
    }

    @Deprecated
    @Override
    public void setCantHorasAl100(double cantHorasAl100) {
        this.cantHorasAl100 = cantHorasAl100;
    }
    
    @Override
     public double calcularSubtotal() 
    {        
       double subT=cantidadPersonas*((costoXHoraNormal*getCantHorasNormales())+(1.5*costoXHoraNormal*getCantHorasAl50())+(2*costoXHoraNormal*getCantHorasAl100()));          
       return subT; 
    }
    
    @Override
    public double calcularTotalHorasPorPersona() 
    {        
       double subT=(getCantHorasNormales()+getCantHorasAl50()+getCantHorasAl100());          
       return subT; 
    }
}
