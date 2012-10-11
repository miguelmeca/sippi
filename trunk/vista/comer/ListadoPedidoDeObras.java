/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.comer;

import java.util.ArrayList;
import modelo.Cotizacion;
import modelo.Herramienta;
import modelo.PedidoObra;
import util.SwingPanel;
import vista.abms.PantallaGestionarRecursos;
import vista.gen.PantallaConsultarGenerica;

/**
 * @author Iuga
 */
public class ListadoPedidoDeObras extends PantallaConsultarGenerica{

    public ListadoPedidoDeObras(Class entidad) {
        super(entidad);
    }

    public ListadoPedidoDeObras() {
        super(PedidoObra.class);
    }
 
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"getNumero","Nro."});
            columnas.add(new String[]{"getNombre","Nombre"});
            columnas.add(new String[]{"mostrarFechaInicio","Inicio"});
            columnas.add(new String[]{"mostrarFechaFin","Fin"});
            columnas.add(new String[]{"mostrarPlanta","Planta"});
            
            columnas.add(new String[]{"getEstado","Estado"});
        
        return columnas;
    }    
    
    @Override
    protected void abrirEntidad(int id) {
        ABMPedidoObra win = new ABMPedidoObra(id);
        //        pantallaConsultarObra win = new pantallaConsultarObra(id);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }   
    
   @Override
    protected ArrayList<String[]> getColumnColorCriteria() 
    {    
        ArrayList<String[]> colorCriteria = new ArrayList<String[]>();

            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_CONFIRMADO,"214","227","188"});
            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_PENDIENTE,"214","227","188"});
            
            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_CANCELADO,"229","184","183"});
            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_SUSPENDIDO,"229","184","183"});
            
            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_EN_EJECUCION,"196","188","150"});
            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_PLANIFICADO,"196","188","150"});
            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_PRESUPUESTADO,"196","188","150"});
            
            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_SOLICITADO,"184","204","240"});
            
            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_SOLICITADO,"132","230","147"});
            
        return colorCriteria;
    }    
 
    @Override
    protected String[] getColumnasFiltro() {
        return new String[]{"Estado"};
    }   
   
}
