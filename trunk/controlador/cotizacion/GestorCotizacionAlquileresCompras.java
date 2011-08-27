/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import modelo.Cotizacion;
import modelo.SubObra;
import vista.cotizacion.CotizacionAlquileresCompras;

/**
 * @author Iuga
 */
public class GestorCotizacionAlquileresCompras implements IGestorCotizacion {
    
    private GestorEditarCotizacion gestorPadre;
    private CotizacionAlquileresCompras pantalla;

    public GestorCotizacionAlquileresCompras(GestorEditarCotizacion gestorPadre) 
    {
        this.gestorPadre = gestorPadre;
    }

    public void setPantalla(CotizacionAlquileresCompras pantalla) {
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

    public void llenarTabla() 
    {
        
                
    }
    
}
