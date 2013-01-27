package modelo;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Iuga
 */
public class DetalleTareaEjecucion extends DetalleTareaPlanificacion{
    
     private DetalleTareaPlanificacion detalleTareaPlanificado;
    
    private List<DetalleTareaEjecucionXDia> listaDetallePorDia;

    public DetalleTareaEjecucion() {
        super();
    }
   
    public DetalleTareaPlanificacion getDetalleTareaPlanificado() {
        return detalleTareaPlanificado;
    }
    
    public DetalleTareaEjecucion(DetalleTareaPlanificacion planificado)
    {
        super();
        this.detalleTareaPlanificado=planificado;
        this.cantidadPersonas=planificado.getCantidadPersonas();
        this.cantHorasNormales=planificado.getCantHorasNormales();
        this.cantHorasAl50=planificado.getCantHorasAl50();
        this.cantHorasAl100=planificado.getCantHorasAl100();
        this.costoXHoraNormal=planificado.getCostoXHoraNormal();
        this.especialidad=planificado.getEspecialidad();
        
        cantidadHijos=0;
        
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
    
    
    //en este caso, la varialbe alFinal es solo para optimiza el recorrido de los for
    public boolean esDiaVacioEnFecha(Date fecha, boolean alFinal) throws Exception{
        boolean encontroFecha=false;
        
        if(alFinal) {
           for (int i = listaDetallePorDia.size()-1; i >=0; i--) {
                DetalleTareaEjecucionXDia dia= listaDetallePorDia.get(i);

                if(dia.esFecha(fecha)){
                    if(dia.getCantidadDeHorasTotales()==0.0) {
                        return true;
                    }
                    encontroFecha=true;
                    break;
                }
            } 
        }
        else {
            for (int i = 0; i < listaDetallePorDia.size(); i++) {
                DetalleTareaEjecucionXDia dia= listaDetallePorDia.get(i);

                if(dia.esFecha(fecha)){
                    if(dia.getCantidadDeHorasTotales()!=0.0) {
                        return true;
                    }
                    encontroFecha=true;
                    break;
                }
            }
        }
        
        if(encontroFecha){
            return false;
        }
        else{
            throw new Exception("Fecha inexistente");
        }
    }
    
    
    //en este caso, la varialbe alFinal es solo para optimiza el recorrido de los for
    public boolean borrarDia(Date fecha, boolean alFinal) throws Exception{
        boolean encontroFecha=false;
        
        if(alFinal) {
           for (int i = listaDetallePorDia.size()-1; i >=0; i--) {
                DetalleTareaEjecucionXDia dia= listaDetallePorDia.get(i);

                if(dia.esFecha(fecha)){
                    encontroFecha=true;
                    listaDetallePorDia.remove(i);
                    break;
                }
            } 
        }
        else {
            for (int i = 0; i < listaDetallePorDia.size(); i++) {
                DetalleTareaEjecucionXDia dia= listaDetallePorDia.get(i);

                if(dia.esFecha(fecha)){
                    encontroFecha=true;
                    listaDetallePorDia.remove(i);
                    break;
                }
            }
        }
        
        if(encontroFecha){
            return true;
        }
        else{
            throw new Exception("Fecha inexistente");
        }
    }
    
    /*public boolean sonDiasVaciosEntreFechas(Date fechainicio, Date fechafin) throws Exception{
        boolean encontroFecha=false;
        for (int i = 0; i < listaDetallePorDia.size(); i++) {
            DetalleTareaEjecucionXDia dia= listaDetallePorDia.get(i);
            
            for()
            if(dia.esFecha(fechaDelNuevo)){
                if(dia.getCantidadDeHorasTotales()!=0.0) {
                    return true;
                }
                encontroFecha=true;
                break;
            }
        }
        if(encontroFecha){
            return false;
        }
        else{
            throw new Exception("Fecha inexistente");
        }
    }*/
    
    public void crearDetalleTareaXDia(Date fechaDelNuevo, boolean alFinal) {
        DetalleTareaEjecucionXDia nuevo = new DetalleTareaEjecucionXDia();
        nuevo.setFecha(fechaDelNuevo);
        nuevo.setCantHorasNormales(0.0);
        nuevo.setCantHorasAl50(0.0);
        nuevo.setCantHorasAl100(0.0);

        if(alFinal) {
            listaDetallePorDia.add(nuevo);
        } else {
            listaDetallePorDia.add(0, nuevo);
        }
    }
    
}
