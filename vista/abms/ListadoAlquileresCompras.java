/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.abms;

import java.util.ArrayList;
import modelo.TipoAlquilerCompra;
import vista.gen.PantallaConsultarGenerica;

/**
 *
 * @author Administrador
 */
public class ListadoAlquileresCompras  extends PantallaConsultarGenerica {
    
    public ListadoAlquileresCompras(Class entidad) {
        super(entidad);
    }

    public ListadoAlquileresCompras() {
        super(TipoAlquilerCompra.class);
    }
  
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"getNombre","Nombre"});
        
        return columnas;
    }     
}
