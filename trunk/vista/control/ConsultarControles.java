package vista.control;

import java.util.ArrayList;
import modelo.PedidoObra;
import util.SwingPanel;
import vista.gen.PantallaConsultarGenerica;
import vista.gui.IFavorito;

/**
 *
 * @author Iuga
 */
public class ConsultarControles extends PantallaConsultarGenerica implements IFavorito{

    public ConsultarControles(Class entidad) {
        super(entidad);
    }

    public ConsultarControles() {
        
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
    protected String getNombreVentana() {
        return "Listado: Controles de Obra";
    }

    @Override
    protected void abrirEntidad(int id) {
        VentanaControl win = new VentanaControl(id);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }
    
    @Override
    protected ArrayList<String[]> getColumnColorCriteria() 
    {    
        ArrayList<String[]> colorCriteria = new ArrayList<String[]>();

            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_CANCELADO,"229","184","183"});
            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_EN_EJECUCION,"196","188","150"});
            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_PLANIFICADO,"196","188","150"});
            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_COTIZADO,"196","188","150"});
            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_SOLICITADO,"132","230","147"});
            colorCriteria.add(new String[]{"Estado",PedidoObra.ESTADO_FINALIZADO,"204","255","153"});
            
        return colorCriteria;
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
