/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion.cotizacion;

import controlador.cotizacion.GestorCotizacionMateriales;
import controlador.cotizacion.GestorEditarCotizacion;
import modelo.SubObraXMaterial;
import modelo.SubObraXMaterialModif;

/**
 *
 * @author Fran
 */
public class GestorCotizacionMaterialesModif extends GestorCotizacionMateriales{
    
    
    public GestorCotizacionMaterialesModif(GestorEditarCotizacion gestorPadre){
        super(gestorPadre);
    }
    
    
    
    @Override
    public SubObraXMaterial nuevaSubObraXMaterial()
    {
        SubObraXMaterial soxm = new SubObraXMaterialModif();
        return soxm;
    }
}
