/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.users;

import java.util.ArrayList;
import modelo.User;
import util.SwingPanel;
import vista.gen.PantallaConsultarGenerica;

/**
 *
 * @author Iuga
 */
public class ListadoUsuarios  extends PantallaConsultarGenerica {

    public ListadoUsuarios(Class entidad) {
        super(entidad);
    }

    public ListadoUsuarios() {
        super(User.class);
    }
    
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"getId","#"});
            columnas.add(new String[]{"getUsuario","Usuario"});
            columnas.add(new String[]{"isAdmin","¿Administrador?"});
            columnas.add(new String[]{"getPermisos","Permisos"});
            columnas.add(new String[]{"getEstado","Estado"});
        
        return columnas;
    }   
    
    @Override
    protected ArrayList<String[]> getColumnColorCriteria() 
    {    
        ArrayList<String[]> colorCriteria = new ArrayList<String[]>();

            colorCriteria.add(new String[]{"Estado",User.ESTADO_ALTA,"214","227","188"});
            colorCriteria.add(new String[]{"Estado",User.ESTADO_BAJA,"224","184","183"});
            
        return colorCriteria;
    }    
    
    @Override
    protected void abrirEntidad(int id) {
        ABMUsers win = new ABMUsers(id);
        SwingPanel.getInstance().addWindow(win);
        win.setVisible(true);
    }    
    
}
