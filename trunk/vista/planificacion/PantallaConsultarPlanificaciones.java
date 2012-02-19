/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import java.util.HashMap;
import modelo.PlanificacionXXX;
import vista.PantallaConsultarGenerica;

/**
 *
 * @author Administrador
 */
public class PantallaConsultarPlanificaciones extends PantallaConsultarGenerica{

    public PantallaConsultarPlanificaciones(Class entidad) {
        super(entidad);
    }

    public PantallaConsultarPlanificaciones() {
        
        super(PlanificacionXXX.class);
    }
    
    @Override
    protected HashMap<String, String> getColumnas()
    {
        HashMap<String, String> columnas = new HashMap<String, String>();
        
            columnas.put("getNumeroPlanificacion","Número");
            columnas.put("getNroCotizacionPlanificada","Cotización");
            columnas.put("getFechaInicio","Fecha de Inicio");
            columnas.put("getFechaFin","Fecha de Fin");
        
        return columnas;
    }
   
}
