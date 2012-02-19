/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.compras;

import java.util.HashMap;
import modelo.HerramientaDeEmpresa;
import vista.PantallaConsultarGenerica;

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
    protected HashMap<String, String> getColumnas()
    {
        HashMap<String, String> columnas = new HashMap<String, String>();
        
            columnas.put("getNroSerie","Número de Serie");
            columnas.put("getEstado","Estado");
            columnas.put("getNombre","Nombre");
        
        return columnas;
    }    
    
    
}
