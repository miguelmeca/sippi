package vista.ejecucion;

import controlador.ejecucion.EjecucionUtils;
import java.util.ArrayList;
import modelo.Ejecucion;
import modelo.PedidoObra;
import util.SwingPanel;
import vista.gen.PantallaConsultarGenerica;
import vista.gui.IFavorito;

/**
 *
 * @author Iuga
 */
public class ConsultarEjecuciones extends PantallaConsultarGenerica implements IFavorito{

    public ConsultarEjecuciones(Class entidad) {
        super(entidad);
    }

    public ConsultarEjecuciones() {
        
        super(Ejecucion.class);
    }
    
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"mostrarPedidoObra","Obra"});
            columnas.add(new String[]{"mostrarFechaInicio","Fecha de Inicio"});
            columnas.add(new String[]{"mostrarFechaFin","Fecha de Fin"});
            columnas.add(new String[]{"getEstado","Estado"});
        
        return columnas;
    }

    @Override
    protected String getNombreVentana() {
        return "Listado: Ejecuciones de Obra";
    }

    @Override
    protected ArrayList<String[]> getColumnColorCriteria() {
        ArrayList<String[]> colorCriteria = new ArrayList<String[]>();
        
            colorCriteria.add(new String[]{"Estado",Ejecucion.ESTADO_CREADA,"184","204","240"});
            colorCriteria.add(new String[]{"Estado",Ejecucion.ESTADO_CANCELADA,"229","184","183"});
            colorCriteria.add(new String[]{"Estado",Ejecucion.ESTADO_FINALIZADA,"204","255","153"});
            
        return colorCriteria;
    }

    @Override
    protected void abrirEntidad(int id) {
        PedidoObra pobra = EjecucionUtils.getPedidoObraFromNumeroEjecucion(id);
        if(pobra!=null){
            VentanaEjecucion win = new VentanaEjecucion(pobra.getId());
            SwingPanel.getInstance().addWindow(win);
            win.setVisible(true);
        }
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
