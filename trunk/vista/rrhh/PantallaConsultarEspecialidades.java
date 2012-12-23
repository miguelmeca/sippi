/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.rrhh;

import java.util.ArrayList;
import modelo.Especialidad;
import modelo.OrdenDeCompra;
import util.SwingPanel;
import vista.gen.PantallaConsultarGenerica;

/**
 *
 * @author Administrador
 */
public class PantallaConsultarEspecialidades extends PantallaConsultarGenerica{
        
    public PantallaConsultarEspecialidades(Class entidad) {
        super(entidad);
    }

    public PantallaConsultarEspecialidades() {
        super(Especialidad.class);
    }

    public PantallaConsultarEspecialidades(String filtro) {
        super(Especialidad.class, filtro);
    }
    
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"mostrarTipoEspecialidad","Tipo de Especialidad"});
            columnas.add(new String[]{"mostrarRangoEspecialidad","Rango"});
            columnas.add(new String[]{"mostrarPrecioPorHora","Precio por Hora"});
        
        return columnas;
    }    

    @Override
    protected void abrirEntidad(int id) {
        pantallaGestionarEspecialidades win = new pantallaGestionarEspecialidades(id);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }
    
}
