/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cotizacion;

import modelo.Cotizacion;
import modelo.SubObra;

/**
 * @author Iuga
 */
public interface IGestorCotizacion {
 
    /**
     * Obtiene la cotizacion con la que se esta trabajando
     * @return 
     */
    public Cotizacion getCotizacion();
    
    
    /**
     * Obtiene la SubObra Actual que se está editando
     * @return 
     */
    public SubObra getSubObraActual();
    
    /**
     * Envia un pedido para que se refresquen el resto de las pantallas
     */
    public void refrescarPantallas();
    
}
