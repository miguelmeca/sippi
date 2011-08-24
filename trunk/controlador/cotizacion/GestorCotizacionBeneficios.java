/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import modelo.Cotizacion;
import vista.cotizacion.CotizacionBeneficios;

/**
 *
 * @author Iuga
 */
public class GestorCotizacionBeneficios implements IGestorCotizacion {
    
    private GestorEditarCotizacion gestorPadre;
    private CotizacionBeneficios pantalla;

    public GestorCotizacionBeneficios(GestorEditarCotizacion gestorPadre) {
        this.gestorPadre = gestorPadre;
    }

    public void setPantalla(CotizacionBeneficios pantalla) {
        this.pantalla = pantalla;
    }
    
    @Override
    public Cotizacion getCotizacion() 
    {
        return this.gestorPadre.getCotizacion();
    }
    
    
}
