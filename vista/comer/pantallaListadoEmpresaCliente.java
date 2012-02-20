/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.comer;

import java.util.HashMap;
import modelo.EmpresaCliente;
import vista.PantallaConsultarGenerica;

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
    protected HashMap<String, String> getColumnas()
    {
        HashMap<String, String> columnas = new HashMap<String, String>();
        
            columnas.put("mostrarEstado","Estado");
            columnas.put("getRazonSocial","Razon Social");
            columnas.put("mostrarDomicilio","Domicilio");
            columnas.put("getEmail","Email");
            columnas.put("mostrarTelefonos","Teléfonos");
        
        return columnas;
    }    
    
}
