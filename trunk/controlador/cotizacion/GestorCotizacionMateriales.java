/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import modelo.Cotizacion;
import vista.cotizacion.CotizacionMateriales;

/**
 *
 * @author Iuga
 */
public class GestorCotizacionMateriales implements IGestorCotizacion{
    
    private GestorEditarCotizacion gestorPadre;
    private CotizacionMateriales pantalla;

    public GestorCotizacionMateriales(GestorEditarCotizacion gestorPadre) {
        this.gestorPadre = gestorPadre;
    }

    public void setPantalla(CotizacionMateriales pantalla) {
        this.pantalla = pantalla;
    }

    @Override
    public Cotizacion getCotizacion() 
    {
        return this.gestorPadre.getCotizacion();
    }
  
}
