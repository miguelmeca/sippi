/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.compras;

import java.util.ArrayList;
import java.util.HashMap;
import modelo.HerramientaDeEmpresa;
import vista.gen.PantallaConsultarGenerica;

/**
 *
 * @author Administrador
 */
public class pantallaConsultarHerramientas extends PantallaConsultarGenerica{

    public pantallaConsultarHerramientas(Class entidad) {
        super(entidad);
    }

    public pantallaConsultarHerramientas() {
        super(HerramientaDeEmpresa.class);
    }
  
    @Override
    protected ArrayList<String[]> getColumnas()
    {
        ArrayList<String[]> columnas = new ArrayList<String[]>();
        
            columnas.add(new String[]{"getNroSerie","Número de Serie"});
            columnas.add(new String[]{"getEstado","Estado"});
            columnas.add(new String[]{"getNombre","Nombre"});
        
        return columnas;
    }    
    
    @Override
    protected String[] getColumnasFiltro() {
        return new String[]{"Estado"};
    }    
    
}
