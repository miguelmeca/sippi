/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.compras;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import modelo.HerramientaDeEmpresa;
import util.SwingPanel;
import vista.gen.PantallaConsultarGenerica;

/**
 * @author Iuga
 */
public class pantallaConsultarHerramientas extends PantallaConsultarGenerica{

    public pantallaConsultarHerramientas(Class entidad) {
        super(entidad);
    }

    public pantallaConsultarHerramientas() {
        super(HerramientaDeEmpresa.class);
    }
  
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"getNroSerie","Número de Serie"});
            columnas.add(new String[]{"getEstado","Estado"});
            columnas.add(new String[]{"getNombre","Nombre"});
        
        return columnas;
    }    
    
    @Override
    protected String[] getColumnasFiltro() {
        return new String[]{"Estado"};
    }    
    
    @Override
    protected void abrirEntidad(int id) {
        // Veo si hay que seleccionar una fila
        if(id!=-1)
        {
            ABMHerramientaDeEmpresa win = new ABMHerramientaDeEmpresa(id);
            SwingPanel.getInstance().addWindow(win);
            win.setVisible(true);
            
        }
        else
        {
            JOptionPane.showMessageDialog(new JInternalFrame(),"No se pudo encontrar el ID de la Fila","Error!",JOptionPane.ERROR_MESSAGE);
        }
    }
}
