/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.comer;

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
    protected HashMap<String, String> getColumnas()
    {
        HashMap<String, String> columnas = new HashMap<String, String>();
        
            columnas.put("mostrarEstado","Estado");
            columnas.put("getRazonSocial","Razon Social");
            columnas.put("mostrarDomicilio","Domicilio");
            columnas.put("getEmail","Email");
            columnas.put("mostrarTelefonos","Teléfonos");
            columnas.put("mostrarRubros","Rubros");
            
        return columnas;
    }    
    
}
