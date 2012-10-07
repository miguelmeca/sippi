/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.comer;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import modelo.EmpresaCliente;
import util.SwingPanel;
import vista.compras.ABMHerramientaDeEmpresa;
import vista.gen.PantallaConsultarGenerica;

/**
 *
 * @author Administrador
 */
public class pantallaListadoEmpresaCliente extends PantallaConsultarGenerica{

    public pantallaListadoEmpresaCliente(Class entidad) {
        super(entidad);
    }

    public pantallaListadoEmpresaCliente() {
        super(EmpresaCliente.class);
    }
    
    @Override
    protected ArrayList<String[]> getColumnas()
    {
         ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"mostrarEstado","Estado"});
            columnas.add(new String[]{"getRazonSocial","Razon Social"});
            columnas.add(new String[]{"mostrarDomicilio","Domicilio"});
            columnas.add(new String[]{"getEmail","Email"});
            columnas.add(new String[]{"mostrarTelefonos","Teléfonos"});
        
        return columnas;
    }    
    
    @Override
    protected void abrirEntidad(int id) {
        // Veo si hay que seleccionar una fila
        if(id!=-1)
        {
            Object opciones[] = {"Consultar Empresa Cliente","Modificar Empresa Cliente","Cancelar"};
            int n = JOptionPane.showInternalOptionDialog(this, "Qué desea hacer con la empresa seleccionada?", "Seleccione una opción", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            switch(n)
            {
                case 0: 
                {
                    pantallaConsultarEmpresaCliente win = new pantallaConsultarEmpresaCliente(id);
                    SwingPanel.getInstance().addWindow(win);
                    win.setVisible(true);
                    break;
                }
                case 1:
                {
                    pantallaModificarEmpresaCliente win = new pantallaModificarEmpresaCliente(id);
                    SwingPanel.getInstance().addWindow(win);
                    win.setVisible(true);
                    break;
                }
                case 2:
                {
                    // nada... 
                    break;
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(new JInternalFrame(),"No se pudo encontrar el ID de la Fila","Error!",JOptionPane.ERROR_MESSAGE);
        }
    }
}
