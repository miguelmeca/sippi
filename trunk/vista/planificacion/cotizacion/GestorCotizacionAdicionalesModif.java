/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion.cotizacion;

import controlador.cotizacion.GestorCotizacionAdicionales;
import controlador.cotizacion.GestorEditarCotizacion;
import modelo.SubObraXAdicional;
import modelo.SubObraXAdicionalModif;

/**
 *
 * @author Fran
 */
public class GestorCotizacionAdicionalesModif extends GestorCotizacionAdicionales{
    
    public GestorCotizacionAdicionalesModif(GestorEditarCotizacion gestorPadre)
    {
        super(gestorPadre);
    }
    
    @Override
    public SubObraXAdicional nuevaSubObraXAdicional()
    {
        SubObraXAdicional soxa = new SubObraXAdicionalModif();
        return soxa;
    }
}
