/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.abms;

import java.util.ArrayList;
import modelo.Cotizacion;
import modelo.Material;
import modelo.RecursoEspecifico;
import util.SwingPanel;
import vista.cotizacion.ExplorarSubObras;
import vista.gen.FiltroPasivo;
import vista.gen.PantallaConsultarGenerica;

/**
 *
 * @author Administrador
 */
public class ListadoMateriales  extends PantallaConsultarGenerica {

    public ListadoMateriales(Class entidad) {
        super(entidad);
    }

    public ListadoMateriales() {
        super(Material.class);
    }
    
    public ListadoMateriales(FiltroPasivo filtro) {
        super(RecursoEspecifico.class,filtro);
    }    
  
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"getTipoRecursoespecifico","Tipo"});
            columnas.add(new String[]{"getNombreRecurso","Nombre"});
            columnas.add(new String[]{"getNombre","Especificación"});
            columnas.add(new String[]{"getDescipcion","Descripcion"});
        
        return columnas;
    }
    
    @Override
    protected String[] getColumnasFiltro() {
        return new String[]{"Tipo"};
    }
    
    @Override
    protected void abrirEntidad(int id) {
        PantallaGestionarRecursos win = new PantallaGestionarRecursos(Material.class,id);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }    
    
}
