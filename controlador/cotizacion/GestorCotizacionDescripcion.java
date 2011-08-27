/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import modelo.Cotizacion;
import modelo.SubObra;
import vista.cotizacion.CotizacionDescripcion;

/**
 *
 * @author Iuga
 */
public class GestorCotizacionDescripcion implements IGestorCotizacion{
  
    private GestorEditarCotizacion gestorPadre;
    private CotizacionDescripcion pantalla;

    public GestorCotizacionDescripcion(GestorEditarCotizacion gestorPadre) {
        this.gestorPadre = gestorPadre;
    }

    public void setPantalla(CotizacionDescripcion pantalla) {
        this.pantalla = pantalla;
    }

    @Override
    public Cotizacion getCotizacion() 
    {
        return this.gestorPadre.getCotizacion();
    }

    @Override
    public SubObra getSubObraActual()
    {
        return this.gestorPadre.getSubObraActual();
    }

    @Override
    public void refrescarPantallas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
}
