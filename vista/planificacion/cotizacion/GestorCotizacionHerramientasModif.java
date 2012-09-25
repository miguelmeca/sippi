/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion.cotizacion;

import controlador.cotizacion.GestorCotizacionHerramientas;
import controlador.cotizacion.GestorEditarCotizacion;
import modelo.SubObraXHerramienta;
import modelo.SubObraXHerramientaModif;

/**
 *
 * @author Fran
 */
public class GestorCotizacionHerramientasModif extends GestorCotizacionHerramientas{
        
    public GestorCotizacionHerramientasModif(GestorEditarCotizacion gestorPadre){
        super(gestorPadre);
    }
    
    @Override
    public SubObraXHerramienta nuevaSubObraXHerramienta()
    {
        SubObraXHerramienta soxh = new SubObraXHerramientaModif();
        return soxh;
    }
}
