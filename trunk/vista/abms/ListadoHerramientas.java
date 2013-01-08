/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.abms;

import java.util.ArrayList;
import java.util.List;
import modelo.Herramienta;
import modelo.RecursoEspecifico;
import util.SwingPanel;
import vista.gen.FiltroPasivo;
import vista.gen.PantallaConsultarGenerica;

/**
 *
 * @author Administrador
 */
public class ListadoHerramientas  extends PantallaConsultarGenerica {
    
    public ListadoHerramientas(Class entidad) {
        super(entidad);
    }

    public ListadoHerramientas() {
        super(Herramienta.class);
    }
    
    public ListadoHerramientas(FiltroPasivo filtro) {
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
        PantallaGestionarRecursos win = new PantallaGestionarRecursos(Herramienta.class,id);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }
    
    @Override
    protected String getNombreVentana() {
        return "Listado: Herramientas";
    }
        
    
}
