/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion.cotizacion;

import controlador.cotizacion.GestorCotizacionAlquileresCompras;
import controlador.cotizacion.GestorEditarCotizacion;
import modelo.SubObraXAlquilerCompra;
import modelo.SubObraXAlquilerCompraModif;

/**
 *
 * @author Fran
 */
public class GestorCotizacionAlquileresComprasModif extends GestorCotizacionAlquileresCompras{
    
    public GestorCotizacionAlquileresComprasModif(GestorEditarCotizacion gestorPadre){
        super(gestorPadre);
    }
    
    @Override
    public SubObraXAlquilerCompra nuevaSubObraXAlquilerCompra()
    {
        SubObraXAlquilerCompra soxac = new SubObraXAlquilerCompraModif();
        return soxac;
    }
}
