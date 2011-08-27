/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import modelo.Cotizacion;
import modelo.SubObra;
import vista.cotizacion.CotizacionHerramientas;

/**
 *
 * @author Iuga
 */
public class GestorCotizacionHerramientas implements IGestorCotizacion{
    
    private GestorEditarCotizacion gestorPadre;
    private CotizacionHerramientas pantalla;

    public GestorCotizacionHerramientas(GestorEditarCotizacion gestorPadre) {
        this.gestorPadre = gestorPadre;
    }

    public void setPantalla(CotizacionHerramientas pantalla) {
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
    
    
    
}
