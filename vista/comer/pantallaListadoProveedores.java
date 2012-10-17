/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.comer;

import java.util.ArrayList;
import java.util.HashMap;
import modelo.EmpresaCliente;
import modelo.Proveedor;
import util.SwingPanel;
import vista.compras.ABMProveedor;
import vista.gen.PantallaConsultarGenerica;

/**
 *
 * @author Administrador
 */
public class pantallaListadoProveedores extends PantallaConsultarGenerica{

    public pantallaListadoProveedores(Class entidad) {
        super(entidad);
    }

    public pantallaListadoProveedores() {
        super(Proveedor.class);
    }
    
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"getRazonSocial","Razon Social"});
            columnas.add(new String[]{"mostrarDomicilio","Domicilio"});
            columnas.add(new String[]{"mostrarTelefonos","Teléfonos"});
            columnas.add(new String[]{"getEmail","Email"});
            columnas.add(new String[]{"mostrarRubros","Rubros"});
            columnas.add(new String[]{"mostrarEstado","Estado"});
            
        return columnas;
    }

    @Override
    protected String[] getColumnasFiltro() {
        return new String[]{"Rubros","Estado"};
    }

    @Override
    protected void abrirEntidad(int id) {       
        ABMProveedor ventana = new ABMProveedor(id);
        SwingPanel.getInstance().addWindow(ventana);
        ventana.setVisible(true);
    }
    
    
    
}
