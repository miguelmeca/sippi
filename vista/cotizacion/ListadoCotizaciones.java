/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.cotizacion;

import java.util.ArrayList;
import modelo.Cotizacion;
import util.SwingPanel;
import vista.gen.PantallaConsultarGenerica;
import vista.gui.IFavorito;

/**
 *
 * @author Administrador
 */
public class ListadoCotizaciones extends PantallaConsultarGenerica implements IFavorito{

    public ListadoCotizaciones(Class entidad) {
        super(entidad);
    }

    public ListadoCotizaciones() {
        super(Cotizacion.class);
    }

    public ListadoCotizaciones(String filtro) {
        super(Cotizacion.class,filtro);
    }    
    
    @Override
    protected String getNombreVentana() {
        return "Listado: Cotizaciones de Obra";
    }
    
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"getNroCotizacion","Número"});
            columnas.add(new String[]{"getNroRevision","Revisión"});
            columnas.add(new String[]{"getFechaCreacion","Fecha de Creación"});
            columnas.add(new String[]{"getCalcularTotal","Monto Cotizado"});
            columnas.add(new String[]{"getEstado","Estado"});
        
        return columnas;
    }

    @Override
    protected ArrayList<String[]> getColumnColorCriteria() 
    {    
        ArrayList<String[]> colorCriteria = new ArrayList<String[]>();

            colorCriteria.add(new String[]{"Estado",Cotizacion.ESTADO_ACEPTADO,"214","227","188"});
            colorCriteria.add(new String[]{"Estado",Cotizacion.ESTADO_CANCELADO,"229","184","183"});
            colorCriteria.add(new String[]{"Estado",Cotizacion.ESTADO_EN_CREACION,"196","188","150"});
            colorCriteria.add(new String[]{"Estado",Cotizacion.ESTADO_PENDIENTE_ACEPTACION,"184","204","240"});
            colorCriteria.add(new String[]{"Estado",Cotizacion.ESTADO_RECHAZADO,"224","184","183"});
            
        return colorCriteria;
    }

    @Override
    protected void abrirEntidad(int id) {
        ExplorarSubObras win = new ExplorarSubObras(id);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }

    @Override
    protected String[] getColumnasFiltro() {
        return new String[]{"Estado"};
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
