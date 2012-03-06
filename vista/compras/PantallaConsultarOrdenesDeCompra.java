/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.compras;

import vista.planificacion.*;
import java.util.HashMap;
import modelo.OrdenDeCompra;
import modelo.PlanificacionXXX;
import vista.PantallaConsultarGenerica;

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
    protected HashMap<String, String> getColumnas()
    {
        HashMap<String, String> columnas = new HashMap<String, String>();
        
            columnas.put("getId","Número");
            columnas.put("getNombreProveedor","Proveedor");
            columnas.put("getFechaDeGeneracion","Fecha de Generación");
            columnas.put("getNombreEstado","Estado");
            columnas.put("getCalcularTotal","Total");
        
        return columnas;
    }
   
}
