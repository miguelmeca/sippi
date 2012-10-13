/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import java.util.ArrayList;
import java.util.HashMap;
import modelo.PlanificacionXXX;
import util.SwingPanel;
import vista.gen.PantallaConsultarGenerica;
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
        
            columnas.add(new String[]{"mostrarPedidoObra","Obra"});
            columnas.add(new String[]{"getNroCotizacionPlanificada","Cotización"});
            columnas.add(new String[]{"mostrarFechaInicio","Fecha de Inicio"});
            columnas.add(new String[]{"mostrarFechaFin","Fecha de Fin"});
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
        
            colorCriteria.add(new String[]{"Estado",PlanificacionXXX.ESTADO_ALTA,"214","227","188"});
            colorCriteria.add(new String[]{"Estado",PlanificacionXXX.ESTADO_BAJA,"229","184","183"});
            colorCriteria.add(new String[]{"Estado",PlanificacionXXX.ESTADO_FINALIZADA,"184","204","240"});
        
        return colorCriteria;
    }

    @Override
    protected void abrirEntidad(int id) {
        EditarPlanificacion win = new EditarPlanificacion(id);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }
    
}
