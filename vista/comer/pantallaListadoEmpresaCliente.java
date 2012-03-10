/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.comer;

import java.util.ArrayList;
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
    
}
