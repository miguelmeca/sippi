/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import modelo.Cotizacion;
import vista.cotizacion.CotizacionAdicionales;

/**
 *
 * @author Iuga
 */
public class GestorCotizacionAdicionales implements IGestorCotizacion {

    private GestorEditarCotizacion gestorPadre;
    private CotizacionAdicionales pantalla;

    public GestorCotizacionAdicionales(GestorEditarCotizacion gestorPadre) 
    {
        this.gestorPadre = gestorPadre;
    }

    public void setPantalla(CotizacionAdicionales pantalla) {
        this.pantalla = pantalla;
    }

    @Override
    public Cotizacion getCotizacion() 
    {
        return this.gestorPadre.getCotizacion();
    }
    
    
    
    
}
