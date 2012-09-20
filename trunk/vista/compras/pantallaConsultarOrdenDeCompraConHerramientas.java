/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.compras;

import java.util.ArrayList;
import modelo.OrdenDeCompra;
import vista.gen.PantallaConsultarGenerica;

/**
 *
 * @author Emmanuel
 */
public class pantallaConsultarOrdenDeCompraConHerramientas extends PantallaConsultarGenerica {

    public pantallaConsultarOrdenDeCompraConHerramientas(Class entidad) {
        super(entidad);
    }

    public pantallaConsultarOrdenDeCompraConHerramientas() {
        super(OrdenDeCompra.class, new FiltroPasivoOrdenesDeCompraConHerramienta());
    }

    @Override
    protected ArrayList<String[]> getColumnas() {
        ArrayList<String[]> columnas = new ArrayList<String[]>();

        columnas.add(new String[]{"getId", "Número"});
        columnas.add(new String[]{"getNombreProveedor", "Proveedor"});
        columnas.add(new String[]{"getFechaDeGeneracionFormateada", "Fecha de Generación"});
        columnas.add(new String[]{"getHerramientasRecibidas", "Herramientas"});

        return columnas;
    }
}
