/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.comer;

import java.util.ArrayList;
import java.util.HashMap;
import modelo.EmpresaCliente;
import modelo.Proveedor;
import vista.PantallaConsultarGenerica;

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
    
}
