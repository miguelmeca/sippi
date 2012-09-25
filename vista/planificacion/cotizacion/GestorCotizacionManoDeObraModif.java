/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion.cotizacion;

import controlador.cotizacion.GestorCotizacionManoDeObra;
import controlador.cotizacion.GestorEditarCotizacion;
import modelo.DetalleSubObraXTarea;
import modelo.DetalleSubObraXTareaModif;
import modelo.SubObraXTarea;
import modelo.SubObraXTareaModif;

/**
 *
 * @author Fran
 */
public class GestorCotizacionManoDeObraModif extends GestorCotizacionManoDeObra{
    
    public GestorCotizacionManoDeObraModif(GestorEditarCotizacion gestorPadre){
        super(gestorPadre);
    }
    
    @Override
    public SubObraXTarea nuevaSubObraXTarea()
    {
        SubObraXTarea soxt = new SubObraXTareaModif();
        return soxt;
    }
    
    @Override
    public DetalleSubObraXTarea crearDetalleVacio()
    {
        return new DetalleSubObraXTareaModif();
    }
}
