/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.compras;

import java.util.ArrayList;
import vista.planificacion.*;
import java.util.HashMap;
import modelo.OrdenDeCompra;
import modelo.PlanificacionXXX;
import vista.gen.PantallaConsultarGenerica;

/**
 *
 * @author Administrador
 */
public class PantallaConsultarOrdenesDeCompra extends PantallaConsultarGenerica{

    public PantallaConsultarOrdenesDeCompra(Class entidad) {
        super(entidad);
    }

    public PantallaConsultarOrdenesDeCompra() {
        
        super(OrdenDeCompra.class);
    }
    
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"getId","Número"});
            columnas.add(new String[]{"getNombreProveedor","Proveedor"});
            columnas.add(new String[]{"getFechaDeGeneracion","Fecha de Generación"});
            columnas.add(new String[]{"getNombreEstado","Estado"});
            columnas.add(new String[]{"getCalcularTotal","Total"});
        
        return columnas;
    }
   
}
