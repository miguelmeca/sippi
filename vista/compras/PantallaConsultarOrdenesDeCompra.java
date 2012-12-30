/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.compras;

import java.util.ArrayList;
import vista.planificacion.*;
import java.util.HashMap;
import modelo.Cotizacion;
import modelo.OrdenDeCompra;
import modelo.Planificacion;
import util.SwingPanel;
import vista.cotizacion.ExplorarSubObras;
import vista.gen.PantallaConsultarGenerica;
import vista.gui.IFavorito;

/**
 * @author Iuga
 */
public class PantallaConsultarOrdenesDeCompra extends PantallaConsultarGenerica implements IFavorito{

    public PantallaConsultarOrdenesDeCompra(Class entidad) {
        super(entidad);
    }

    public PantallaConsultarOrdenesDeCompra() {
        super(OrdenDeCompra.class);
    }

    public PantallaConsultarOrdenesDeCompra(String filtro) {
        super(OrdenDeCompra.class, filtro);
    }
    
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"getId","Número"});
            columnas.add(new String[]{"getNombreProveedor","Proveedor"});
            columnas.add(new String[]{"getFechaDeGeneracionFormateada","Fecha de Generación"});
            columnas.add(new String[]{"getEstado","Estado"});
            columnas.add(new String[]{"mostrarCalcularTotal","Total"});
        
        return columnas;
    }
    
    @Override
    protected ArrayList<String[]> getColumnColorCriteria() 
    {    
        ArrayList<String[]> colorCriteria = new ArrayList<String[]>();

            colorCriteria.add(new String[]{"Estado",OrdenDeCompra.ESTADO_PENDIENTE,"214","227","188"});
            colorCriteria.add(new String[]{"Estado",OrdenDeCompra.ESTADO_ANULADA,"229","184","183"});
            colorCriteria.add(new String[]{"Estado",OrdenDeCompra.ESTADO_EMITIDA,"184","204","240"});
            
        return colorCriteria;
    }

    @Override
    protected String[] getColumnasFiltro() {
        return new String[]{"Estado","Proveedor","Fecha de Generación"};
    }       
    
    @Override
    protected void abrirEntidad(int id) {
        GenerarNuevaOrdenDeCompra win = new GenerarNuevaOrdenDeCompra(id);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }    
    
    @Override
    public boolean isFavorito() {
        return true;
    }

    @Override
    public String getIconoFavorito() {
        return "/res/iconos/var/16x16/List.png";
    }       
   
}
