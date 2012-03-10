/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import java.util.ArrayList;
import java.util.HashMap;
import modelo.PlanificacionXXX;
import vista.PantallaConsultarGenerica;
import vista.interfaces.ICallBackGen;

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
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"getNumeroPlanificacion","Número"});
            columnas.add(new String[]{"getNroCotizacionPlanificada","Cotización"});
            columnas.add(new String[]{"getFechaInicio","Fecha de Inicio"});
            columnas.add(new String[]{"getFechaFin","Fecha de Fin"});
            columnas.add(new String[]{"getEstado","Estado"});
        
        return columnas;
    }

    @Override
    protected String getNombreVentana() {
        return "Listado de todas las Planificaciones Realizadas";
    }

    @Override
    protected ArrayList<String[]> getColumnColorCriteria() {
        ArrayList<String[]> colorCriteria = new ArrayList<String[]>();
        
            colorCriteria.add(new String[]{"Estado",PlanificacionXXX.ESTADO_ALTA,"102","102","88"});
            colorCriteria.add(new String[]{"Estado",PlanificacionXXX.ESTADO_BAJA,"15","200","75"});
        
        return colorCriteria;
    }
    
    
    
}
