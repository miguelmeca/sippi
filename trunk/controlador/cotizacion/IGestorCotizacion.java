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
 
    public Cotizacion getCotizacion();
    
    public SubObra getSubObraActual();
    
}
