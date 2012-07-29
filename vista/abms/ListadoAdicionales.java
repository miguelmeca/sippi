/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.abms;

import java.util.ArrayList;
import modelo.TipoAdicional;
import modelo.TipoAlquilerCompra;
import vista.gen.PantallaConsultarGenerica;

/**
 *
 * @author Administrador
 */
public class ListadoAdicionales  extends PantallaConsultarGenerica {
    
    public ListadoAdicionales(Class entidad) {
        super(entidad);
    }

    public ListadoAdicionales() {
        super(TipoAdicional.class);
    }
  
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"getNombre","Nombre"});
        
        return columnas;
    }    
    
    @Override
    protected void abrirEntidad(int id) {
//        PantallaGestionarRecursos win = new PantallaGestionarRecursos(Herramienta.class,id);
//        SwingPanel.getInstance().addWindow(win);
//        win.setVisible(true);
    }
    
    
}
